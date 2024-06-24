import { createApp } from 'vue'

// 导入router和store
import router from './router/index'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//导入pinia
import {createPinia} from 'pinia'
import presist from 'pinia-plugin-persistedstate'
import VCalendar from 'v-calendar';
import 'v-calendar/style.css';

const pinia = createPinia();
const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.use(VCalendar, {})
pinia.use(presist)
app.use(pinia)
app.mount('#app')
