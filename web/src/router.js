import Vue from "vue";
import VueRouter from "vue-router";

// Start using vue router
Vue.use(VueRouter);

export default new VueRouter({
  mode: 'hash',
  linkActiveClass: 'active open',
  routes: [
    {
      path: '/',
      component: require('./dashboard/dashboard.vue')
    },
    {
      path: '/settings',
      redirect: '/settings/notifications',
      component: require('./setting/setting.vue'),
      children: [
        {
          path: 'notifications',
          component: require('./setting/notification.vue')
        }
      ]
    }
  ]
});

