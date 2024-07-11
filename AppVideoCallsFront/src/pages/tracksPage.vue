<template>
  <q-page class="q-pa-md">
    <q-card class="q-pa-lg my-card">
      <q-card-section>
        <q-label>Join chame - {{ props.roomName }}</q-label>
        <div class="text-h6">Video Meet</div>
      </q-card-section>
      <div class="video-streams">
        <div ref="remoteVideo" class="video-container"></div>
        <div ref="localVideo" class="video-container-local"></div>
      </div>
    </q-card>
    <q-card-section>
      <q-item>
        <q-item-section class="q-mb-md content-center">
          <q-btn v-if="mic" @click="toggleMicrophone">
            {{ localTracks[0].muted ? 'Unmute Microphone' : 'Mute Microphone' }}
          </q-btn>
          <q-btn v-if="cam" @click="toggleCamera">
            {{ localTracks[1].muted ? 'Turn On Camera' : 'Turn Off Camera' }}
          </q-btn>
        </q-item-section>
      </q-item>
    </q-card-section>
  </q-page>
</template>

<script setup>
import AgoraRTC from 'agora-rtc-sdk-ng';
import { defineProps, ref } from 'vue';
import { useRouter } from 'vue-router';
import { onBeforeMount, markRaw, computed } from 'vue';

const router = useRouter();
const client = ref(null);
const localVideo = ref(null);
const remoteVideo = ref(null);
const localTracks = ref([]);
const mic = computed(() => {
  return localTracks.value.length > 0 && localTracks.value[0]
})
const cam = computed(() => {
  return localTracks.value.length > 0 && localTracks.value[1]
})
const props = defineProps({
  token: {
    type: String,
    required: true
  },
  roomName: {
    type: String,
    required: true
  },
  appId: {
    type: String,
    required: true
  },
  uId: {
    type: String,
    required: true
  }
});

onBeforeMount(async () => {
  if (!props.token || !props.roomName || !props.appId) {
    console.error('Missing required props');
    router.push('/');
    return;
  }

  try {
    client.value = markRaw(AgoraRTC.createClient({ mode: 'rtc', codec: 'vp8' }));

    localTracks.value = await AgoraRTC.createMicrophoneAndCameraTracks();

    await client.value.join(props.appId, props.roomName, props.token, props.uId);
    await client.value.publish(localTracks.value);

    localTracks.value[1].play(localVideo.value);

    client.value.on('user-published', async (user, mediaType) => {
      await client.value.subscribe(user, mediaType);
      if (mediaType === 'video') {
        const remoteTrack = user.videoTrack;
        remoteTrack.play(remoteVideo.value);
      }
    });


  } catch (error) {
    console.error('Error joining channel:', error);
  }
});

async function toggleMicrophone() {
  console.log(localTracks.value)
  if (localTracks.value.length > 0) {
    if (localTracks.value[0].muted) {
      await localTracks.value[0].setMuted(false)
    } else {
      await localTracks.value[0].setMuted(true)
    }
  }
}

async function toggleCamera() {
  if (localTracks.value.length > 1) {
    if (localTracks.value[1].muted) {
      await localTracks.value[1].setMuted(false)
    } else {
      await localTracks.value[1].setMuted(true)
    }
  }
}
</script>

<style scoped>
.my-card {
  max-width: 1200px;
  margin: auto;
}

.video-container {
  width: 100%;
  height: 480px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #000;
  display: flex;
  align-items: center;
  justify-content: center;
}

.video-container-local {
  width: 300px;
  height: 240px;
  border: 1px solid #cccccc;
  border-radius: 4px;
  background-color: #000;
  display: flex;
  align-items: center;
  justify-content: center;
}

#video-streams {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
  height: 90vh;
  width: 100%;
  margin: 0 auto;
}
</style>
