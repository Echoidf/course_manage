<template>
  <div class="input-wrap">
    <span class="atr">{{ atr }}</span>
    <input @input="onInput" v-model="showValue">
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const props = defineProps(['modelValue', 'atr'])
const emit = defineEmits(["update:modelValue"]);

function onInput(e) {
  emit('update:modelValue', e.target.value)
}

const showValue = ref(props.modelValue);

onMounted(() => {
  console.log(showValue.value);
})
</script>

<style  lang="less" scoped>
.input-wrap {
  display: inline-block;
}

.atr {
  font-size: calc(@baseSize * 1.2);
  font-weight: bold;
}

input {
  display: inline-block;
  position: relative;
  margin: 3px 12px 3px 12px;
  padding: 1.2rem;
  width: 156px;
  padding: 6px;
  border-radius: 5px;
  border: 1px solid #d8d8d8;
  outline: none;
  font-size: medium;
  font-weight: 300;
}

/* 其他样式 */
</style>