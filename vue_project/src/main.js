import Vue from 'vue'
import App from './App.vue'
import router from './router'

import axios from 'axios'
Vue.prototype.$axios = axios

import { Button, message,Layout,Input,Menu,Icon,Collapse,FormModel,Spin,Select,Pagination,Card,Modal} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
Vue.use(Button);
Vue.use(Layout);
Vue.use(Input);
Vue.use(Menu);
Vue.use(Collapse);
Vue.use(Icon);
Vue.use(FormModel);
Vue.use(Spin);
Vue.use(Select);
Vue.use(Pagination);
Vue.use(Card);
Vue.use(Modal);
Vue.prototype.$message = message;


Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
