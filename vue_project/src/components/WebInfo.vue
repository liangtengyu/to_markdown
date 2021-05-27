<template>
  <div class="div">

    <a-spin :spinning="spinning">
      <a-form-model :model="configInfo" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">


        <a-form-model-item label="抓取文章地址" prop="blogUrl">
          <a-input v-model="configInfo.blogUrl" placeholder="请输入文章地址"/>
        </a-form-model-item>

        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
          <a-button type="primary" @click="onSubmit" icon="copy">
            解析
          </a-button>
          <a-button style="margin-left: 10px;" icon="close-circle">
            取消
          </a-button>

        </a-form-model-item>
      </a-form-model>
    </a-spin>

    <a-button type="dashed" v-if="saveButtonIsShow" style="margin-left: 10px;" icon="save" @click="update()">
      保存更改
    </a-button>
    <a-input v-model="title" v-if="saveButtonIsShow" placeholder="自定义文章标题"/>
    <mavon-editor class="mde" v-model="data.markdown" style="min-height: 500px"/>


  </div>
</template>

<script>
import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  name: 'WebInfo',
  components: {
    mavonEditor
  },
  data() {
    return {
      title:'',
      saveButtonIsShow: false,
      value: '',
      data: {
        id: '',
        code: '',
        markdown: ''
      },
      loadfile: '',
      spinning: false,
      labelCol: {span: 3},
      wrapperCol: {span: 18},
      configInfo: {
        blogUrl: ""
      },
      rules: {
        blogUrl: [
          {required: true, message: '根据情况选择运行环境', trigger: 'blur'},
          {min: 10, message: 'Length should be > 10', trigger: 'blur'},
        ]
      },
    }
  }, methods: {
    onSubmit() {
      if (this.configInfo.blogUrl.length > 10) {
        this.spinning = true;
        this.$axios.post('/resolve/mark', this.configInfo).then(r => {
          this.data = r.data
          this.spinning = false;


        }).catch(reason => {
          alert(reason);
          this.spinning = false;
        });
      } else {
        alert("需要输入文章地址")
      }

    }, update() {
      this.spinning = true;
      this.$axios.post('/update',{'id':this.data.id,'context':this.data.markdown,'blogUrl':this.configInfo.blogUrl,'title':this.title}).then(r => {
        if (r.data.code === 0) {
          alert(" 保存到数据库成功!   本地md文件重写完成!")
        }
      }).catch(reason => {
        alert(reason);
      });
      this.spinning = false;

    }, initArticle(id) {
      this.saveButtonIsShow = true
      this.$axios.post('/select/' + id).then(r => {
        this.data.markdown = r.data.context
        this.data.id = r.data.id
        this.configInfo.blogUrl = r.data.blogUrl
      }).catch(reason => {
        alert(reason);
      });
      this.spinning = false;
    }

  }, mounted() {
    this.loadfile = this.$route.params.id
    if (this.loadfile !== undefined) {
      this.initArticle(this.loadfile)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.mde {
  height: 850px;
}
</style>
