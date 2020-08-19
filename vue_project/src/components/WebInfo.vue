<template>
  <div>
  <a-form-model :model="webinfo"  :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">



    <a-collapse  v-model="activeKey" >
      <a-collapse-panel key="1" header="文章信息" >
    <a-form-model-item label="文章地址"  prop="blogUrl">
      <a-input v-model="webinfo.blogUrl" placeholder="请输入文章地址"/>
    </a-form-model-item>
        <a-form-model-item label="来源站点">
          <a-select v-model="webinfo.website" placeholder="选择文章来源网站">
            <a-select-option value="csdn">
              CSDN
            </a-select-option>
            <a-select-option value="vx">
              微信公众号
            </a-select-option>
            <a-select-option value="cnblogs">
              博客园cnblogs
            </a-select-option>
            <a-select-option value="" disabled>
              其它渠道陆续开发中...
            </a-select-option>
          </a-select>
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
      data:'无',
      activeKey: [1, 2],
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      webinfo:{
        website:undefined,
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
      console.log('submit!', this.webinfo);
      this.$axios.post('http://localhost:9999/resolve/mark', this.webinfo).then(r =>{
        this.data = r.data
      });
    },
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
