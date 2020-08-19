  <template id="app">
    <a-layout id="components-layout-demo-custom-trigger">
      <a-layout-sider v-model="collapsed" :trigger="null" collapsible>
        <div class="logo" >
          <span style="color:#1890ff;font-size: 22px; font-weight: bold">| ToMarkDown |</span>
        </div>
        <a-menu theme="dark" mode="inline" :default-selected-keys="['1']">
          <a-menu-item key="Home"   @click="toNav">
            <a-icon type="clock-circle" />
            <span>解析</span>
          </a-menu-item>
          <a-menu-item key="upload"   @click="toNav">
            <a-icon type="upload" />
            <span>上传</span>
          </a-menu-item>
          <a-menu-item key="about"    @click="toNav">
            <a-icon type="bulb" />
            <span>关于</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header style="background: #fff; padding: 0">
          <a-icon
                  class="trigger"
                  :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                  @click="() => (collapsed = !collapsed)"
          />
        </a-layout-header>
        <a-layout-content
                :style="{ margin: '24px 16px', padding: '24px', background: '#fff', minHeight: '280px' }"
        >

          <router-view/>

        </a-layout-content>
      </a-layout>
    </a-layout>
  </template>

<script>
    export default {
      data() {
        return {
          collapsed: false,
        };
      },methods:{
        toNav(e){
          this.$router.push({
            path:e.key
          })
        }
      }
    };
  </script>
  <style>
    #components-layout-demo-custom-trigger .trigger {
      font-size: 18px;
      line-height: 64px;
      padding: 0 24px;
      cursor: pointer;
      transition: color 0.3s;
    }

    #components-layout-demo-custom-trigger .trigger:hover {
      color: #1890ff;
    }

    #components-layout-demo-custom-trigger .logo {
      height: 32px;
      background: rgba(255, 255, 255, 0.2);
      margin: 16px;
    }
  </style>

