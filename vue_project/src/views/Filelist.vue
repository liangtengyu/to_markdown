<template>

  <div class="Files" style="height: 1500px; overflow: hidden" >
    <div class="content-box" v-for="md in mdlist"   >
      <div class="opt_button">
        <a-button type="dashed" icon="edit" :id="md.id " @click="edit(md.id)" >
          编辑
        </a-button>
        <a-button type="dashed" icon="delete" :id="md.id" ref="id" @click="showModal(md.id)" >
          删除
        </a-button>

      </div>
        <a-card class="tr" :title="md.title"  >

        <section>
          <td v-for="pics in md.pics">
            <img :src=pics  alt="" class="img-container">
          </td>
        </section>
        </a-card>
    </div>
    <a-pagination v-model="current" :total="count" :defaultPageSize=5 show-less-items  @change="getData(current-1)" />

    <a-modal v-model="visible" title="确认删除吗?"  @ok="handleOk($event)">
      <p>注意:删除时数据库记录和本地文件会一并删除!</p>
    </a-modal>
  </div>

</template>

<script>
export default {
  components: {},
  data() {
    return {
      mdlist: '',
      current: 1,
      count:5,
      visible: false,
      delete:-1,
    }
  }, methods: {
    edit(id){
      console.log("发送"+id)
      this.$router.push({
        path: '/Home',
        name: 'Home',
        params: {
          id: id
        }
      })
    },
    showModal(id) {
      this.visible = true;
      this.delete = id;
    },
    handleOk(e) {
      console.log(this.delete);
      this.$axios.post('/delete/'+this.delete).then(r => {
        console.log("删除返回:",r.data)
        this.getData(0)
      }).catch(reason => {
        alert("请求数据出错:" + reason);
      });
      this.visible = false;
    },
    getData(id){
      this.spinning = true;
      this.$axios.post('/filelist', {"id": id}).then(r => {
        this.mdlist = r.data.data;
      }).catch(reason => {
        alert("请求数据出错:" + reason);
      });
      this.$axios.post('/count').then(r => {
        this.count = r.data;
      }).catch(reason => {
        alert("请求分页数据出错:" + reason);
      });
      this.spinning = false;
    }
  }
  , mounted() {
    this.getData(0)
  }

}
</script>


<style scoped>
.img-container {
  width: 210px;
  height: 140px;
}


.tr{
  border-radius: 6px;
  margin-bottom: 11px;
  overflow: hidden;
}
.opt_button{
}
</style>
