<template>
  <div class="Files" style=" height: 850px;">


      <div class="content-box">
        <tr v-for="md in mdlist">
          <td>
            <div class="files">
             {{md.title}}
            </div>
          </td>

            <td v-for="pics in md.pics">
              <img :src=pics  alt="" class="img-container">
            </td>


        </tr>






      </div>



  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      mdlist: '',
    }
  }, methods: {

  }, beforeCreate() {
    this.spinning = true;
    this.$axios.post('http://127.0.0.1:9999/filelist', {"id": 0}).then(r => {
      this.mdlist = r.data.data;
      console.log(this.mdlist)
    }).catch(reason => {
      alert("请求数据出错:" + reason);
    });
    this.spinning = false;
  }

}
</script>


<style scoped>
.img-container {
  width: 270px;
  height: 150px;
}

.content-box {
  /*border: 1px solid #1890ff ;*/
  padding: 10px;
  margin-bottom: 10px;
}

</style>
