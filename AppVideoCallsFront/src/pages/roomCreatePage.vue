<template>
  <q-page class="q-pa-md">
    <q-card class="q-pa-lg my-card">
      <q-card-section v-if="!apiDataLoaded">
        <div class="text-h6">Start Meet</div>
      </q-card-section>

      <q-card-section class="q-pt-none" v-if="!apiDataLoaded">
        <q-item>
          <q-item-section>
            <q-btn color="primary" @click="create">Start</q-btn>
          </q-item-section>
        </q-item>
      </q-card-section>

      <q-card-section v-if="apiDataLoaded" class="q-pt-none">
        <tracks-page :token="token" :roomName="roomName" :appId="appId" :uId="userId" />
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup>
import { ref, onBeforeMount } from 'vue';
import axios from 'axios';
import tracksPage from './tracksPage.vue';

const token = ref('');
const appId = ref('');
const roomName = ref('');
const userId = ref(0)
const apiDataLoaded = ref(false);

const api = axios.create({
  baseURL: 'http://localhost:8080',
});
onBeforeMount(() => {
  userId.value = generateRandomId();
});
async function create() {
  try {
    const response = await api.post('/create', null, {
      params: {
        id: userId.value
      }
    });
    console.log(response.data);

    // Asignar valores de token, appId y roomName después de crear la sala
    token.value = response.data.token;
    appId.value = response.data.appId;
    roomName.value = response.data.channelName;

    // Marcar que los datos del API han sido cargados
    apiDataLoaded.value = true;

  } catch (error) {
    console.error(error);
  }
}

function generateRandomId() {
  const random = Math.random();
  return Math.floor(random * (10000 - 100 + 1)) + 100;
}
</script>
