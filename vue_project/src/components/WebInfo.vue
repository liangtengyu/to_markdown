<template>
  <div>

    <a-spin :spinning="spinning">
  <a-form-model :model="webinfo"  :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">


    <a-collapse  v-model="activeKey" >
      <a-collapse-panel key="1" header="文章信息" >
    <a-form-model-item label="文章地址"  prop="blogUrl">
      <a-input v-model="webinfo.blogUrl" placeholder="请输入文章地址"/>
    </a-form-model-item>

      </a-collapse-panel>
      <a-collapse-panel key="2" header="图片保存信息"  >

        <a-form-model-item label="图片名称"   >
          <a-input v-model="webinfo.imageName"  placeholder="请输入图片名称"/>
        </a-form-model-item>
        <a-form-model-item label="存图片到:"  prop="blogUrl">
          <a-input v-model="webinfo.imagePath" placeholder="请输入存图片到哪里--[服务器存图片的目录]"/>
        </a-form-model-item>
        <a-form-model-item label="文中图片链接:"  prop="blogUrl">
          <a-input v-model="webinfo.imageUrl" placeholder="请输入解析后文章中图片的链接--[外网,本地服务器的ip或域名]"/>
        </a-form-model-item>
      </a-collapse-panel>
    </a-collapse>

    <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">
        解析
      </a-button>
      <a-button style="margin-left: 10px;">
        取消
      </a-button>
    </a-form-model-item>
  </a-form-model>
    </a-spin>

      <mavon-editor     v-model="data.markdown"  style="min-height: 500px" />


  </div>
</template>

<script>
  import {mavonEditor} from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  export default {
  name: 'WebInfo',
  components:{
    mavonEditor
  },
  data(){
    return{
      data:'',
      spinning: false,
      activeKey: [1, 2],
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      webinfo:{
        blogUrl:undefined,
        imagePath:undefined,
        imageUrl:undefined,
        imageName:undefined
      },
      rules: {
        blogUrl: [
          { required: true, message: 'Please input blogUrl ', trigger: 'blur' },
          { min: 10,  message: 'Length should be > 10', trigger: 'blur' },
        ]
      },
    }
  },methods: {
    onSubmit() {
      this.spinning = true;
      this.$axios.post('/resolve/mark', this.webinfo).then(r =>{
        this.data = r.data
        this.spinning = false;

      }).catch(reason => {
        console.log(reason);
        console.log('报错啦');
        this.spinning = false;
      });
    },

  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
