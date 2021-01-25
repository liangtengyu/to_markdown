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
          <a-input v-model="webinfo.imageName" required  placeholder="请输入图片名称"/>
        </a-form-model-item>

      </a-collapse-panel>




      <a-collapse-panel key ="3" header="自定义配置(不需要修改)">
        <a-form-model-item label="存图片到:"  prop="blogUrl">
          <a-input v-model="webinfo.imagePath" placeholder="请输入存图片到哪里--[服务器存图片的目录]-可不填"/>
        </a-form-model-item>
        <a-form-model-item label="图片映射地址:"  prop="blogUrl">

            <a-select default-value="http://localhost:9999/images" v-model="webinfo.imageUrl"  @change="handleChange">
              <a-select-option value=" http://localhost:9999/images">
                本地运行
              </a-select-option>
              <a-select-option value="http://markdown.liangtengyu.com:9998/images">
                线上试用
              </a-select-option>
            </a-select>



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
        blogUrl:"",
        imagePath:"./pics",
        imageUrl:"http://localhost:9999/images",
        imageName:"default_name"
      },
      rules: {
        blogUrl: [
          { required: true, message: '根据情况选择运行环境', trigger: 'blur' },
          { min: 10,  message: 'Length should be > 10', trigger: 'blur' },
        ]
      },
    }
  },methods: {
    onSubmit() {
      if (this.webinfo.blogUrl.length>10) {
        this.spinning = true;
        this.$axios.post('/resolve/mark', this.webinfo).then(r => {
          this.data = r.data
          this.spinning = false;


        }).catch(reason => {
          alert(reason);
          this.spinning = false;
        });
      } else {
        alert("需要输入文章地址")
      }

    },handleChange(value) {
        this.webinfo.imageUrl = value
    }

  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
