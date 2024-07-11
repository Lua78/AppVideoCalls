<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn flat dense round icon="menu" aria-label="Menu" @click="toggleLeftDrawer" />

        <q-toolbar-title>
          Video Calls Application
        </q-toolbar-title>

        <div>Quasar v{{ $q.version }}</div>
      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <div class="q-px-xl">
        <q-item-label header>
          Options
        </q-item-label>
        <q-item to="/create" v-ripple clickable>
          <q-item-section avatar>
            <q-icon color="primary" name="mdi-video" />
          </q-item-section>
          <q-item-section>
            Create VideoCall
          </q-item-section>
        </q-item>
        <q-item to="/join" v-ripple clickable>
          <q-item-section avatar>
            <q-icon color="primary" name="mdi-camera" />
          </q-item-section>
          <q-item-section>
            Join VideoCall
          </q-item-section>
        </q-item>
      </div>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref } from 'vue'
import EssentialLink from 'components/EssentialLink.vue'

defineOptions({
  name: 'MainLayout'
})


const leftDrawerOpen = ref(false)

function toggleLeftDrawer() {
  leftDrawerOpen.value = !leftDrawerOpen.value
}
</script>
