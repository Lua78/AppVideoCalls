<template>
  <div v-if="!apiDataLoaded">
    <q-item-label>Insert room code</q-item-label>
    <q-input v-model="roomName" />
    <q-btn @click="join">Join</q-btn>
  </div>
  <tracks-page v-if="apiDataLoaded" :token="token" :roomName="roomName" :appId="appId" :uId="userId" />
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { onBeforeMount, ref } from 'vue';
import axios from 'axios';
import tracksPage from './tracksPage.vue';

const router = useRouter();
const token = ref('');
const appId = ref('');
const roomName = ref('');
const apiDataLoaded = ref(false);

const userId = ref(0);

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

function generateRandomId() {
  const random = Math.random();
  return Math.floor(random * (10000 - 100 + 1)) + 100;
}

onBeforeMount(() => {
  userId.value = generateRandomId();
});

const join = async () => {
  try {
    const response = await api.post('/join', null, {
      params: {
        id: userId.value,
        channel: roomName.value
      }
    });
    token.value = response.data.token;
    appId.value = response.data.appId;
    apiDataLoaded.value = true;
  } catch (error) {
    console.error(error);
  }
};
</script>
