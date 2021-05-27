<template>


  <div class="Files" style="height: 1500px; overflow: hidden" >
    <div class="content-box" v-for="md in mdlist">
      <div class="opt_button">
        <a-button type="dashed" icon="edit" :id="md.id">
          编辑
        </a-button>
        <a-button type="dashed" icon="delete" :id="md.id">
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
    <a-pagination v-model="current" :total="30" show-less-items  @change="getData(current-1)" />
  </div>

</template>

<script>
export default {
  components: {},
  data() {
    return {
      mdlist: '',
      current: 1,
    }
  }, methods: {
    getData(id){
      this.spinning = true;
      this.$axios.post('http://127.0.0.1:9999/filelist', {"id": id}).then(r => {
        this.mdlist = r.data.data;
      }).catch(reason => {
        alert("请求数据出错:" + reason);
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
