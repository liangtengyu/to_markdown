<template>
  <div style=" height: 850px;">

    <a-spin :spinning="spinning">
      <a-form-model :model="configInfo" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">


        <a-collapse  v-model="activeKey" >


          <a-collapse-panel key="1" header="抓取文章前-先进行配置"  >

            <a-form-model-item label="图片名称"   >
              <a-input v-model="configInfo.Image_DEFAULT_NAME" required placeholder="请输入图片名称"/>
            </a-form-model-item>
            <a-form-model-item label="保存图片到:"  prop="blogUrl">
              <a-input v-model="configInfo.Image_Save_Path" placeholder="请输入存图片到哪里"/>
            </a-form-model-item>
            <a-form-model-item label="保存MD文件到:" >
              <a-input v-model="configInfo.MD_Save_Path" required placeholder="请输入保存MD文件到哪里"/>
            </a-form-model-item>
            <a-form-model-item label="图片映射地址:"  prop="blogUrl">
              <a-input v-model="configInfo.Image_Proxy_Path" placeholder="请输入图片映射的地址"/>
            </a-form-model-item>


            <a-form-model-item class="save_button" :wrapper-col="{ span: 14, offset: 4 }">
              线上试用版本,为防止被恶意篡改配置,不提供此功能!
<!--              <a-button type="primary"  @click="onSubmit">-->
<!--                保存配置-->
<!--              </a-button>-->

              <p style="color: red" class="lb" >{{msg}}</p>
            </a-form-model-item>
          </a-collapse-panel>
        </a-collapse>
      </a-form-model>
    </a-spin>




  </div>
</template>

<script>
  export default {
    components:{

    },
    data(){
      return{
        msg:'',
        data:'',
        spinning: false,
        activeKey: [1],
        labelCol: { span: 4 },
        wrapperCol: { span: 18 },
        configInfo:{
          Image_DEFAULT_NAME:"default_name",
          Image_Save_Path:"./pics",
          MD_Save_Path:"./mds",
          Image_Proxy_Path:"http://localhost:9999/images"
        },rules: {
          blogUrl: [
            { min: 10,  message: 'Length should be > 10', trigger: 'blur' },
          ]
        },

      }
    },methods: {
      onSubmit() {
        this.msg = '更改配置后请重启服务器,否则不生效!';
          this.spinning = true;
          this.$axios.post('/setting/set', this.configInfo).then(r => {
            this.data = r.data
            this.spinning = false;
            console.log(this.data)

          }).catch(reason => {
            alert(reason);
            this.spinning = false;
          });


      }

    },mounted() {

        this.spinning = true;
        this.$axios.post('/setting/get').then(r => {
          this.data = r.data
          this.configInfo=this.data
          this.spinning = false;
        }).catch(reason => {
          alert(reason);
          this.spinning = false;
        });

      }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.save_button {

}
</style>
