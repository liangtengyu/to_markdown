<template>
<div style="height: 850px">
    <p>点击connect进行数据库管理</p>
    <iframe id="template-iframe" ref="iframe" src="/db" @load="loaded" class="if">

    </iframe>
</div>
</template>

<script>
    export default {
        name: "manage"
    }
</script>

<style scoped>
.if {
    height: 768px;
    width: 1024px;
}
</style>
