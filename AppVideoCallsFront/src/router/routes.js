const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/IndexPage.vue") }],
  },
  {
    path: "/join/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("src/pages/roomJoinPage.vue") },
    ],
  },
  {
    path: "/create",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("src/pages/roomCreatePage.vue") },
    ],
  },
  {
    path: "/tracksPage",
    name: 'tracksPage',
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("src/pages/tracksPage.vue") },
    ],
    props: true,
  },
];

export default routes;
