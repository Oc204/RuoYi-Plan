<template>
  <div class="upload-file">
<!--    <el-upload-->
<!--      multiple-->
<!--      :action="uploadFileUrl"-->
<!--      :before-upload="handleBeforeUpload"-->
<!--      :file-list="fileList"-->
<!--      :limit="limit"-->
<!--      :on-error="handleUploadError"-->
<!--      :on-exceed="handleExceed"-->
<!--      :on-success="handleUploadSuccess"-->
<!--      :show-file-list="false"-->
<!--      :headers="headers"-->
<!--      class="upload-file-uploader"-->
<!--      ref="upload"-->
<!--    >-->
    <el-upload
      multiple
      :options="options"
      :autoStart=false
      :file-status-text="fileStatusText"
      :on-error="handleUploadError"
      @file-added="onFileAdded"
      @file-success="onFileSuccess"
      @file-progress="onFileProgress"
      :headers="headers"
      class="upload-file-uploader"
      ref="upload"
    >
      <!-- 上传按钮 -->
      <el-button size="mini" type="primary">选取文件</el-button>
      <!-- 上传提示 -->
      <div class="el-upload__tip" slot="tip" v-if="showTip">
        请上传
        <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}GB</b> </template>
        <!--        <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>-->
        的文件
      </div>
    </el-upload>

    <!-- 文件列表 -->
    <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul">
      <li :key="file.url" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in fileList">
        <el-link :href="`${baseUrl}${file.url}`" :underline="false" target="_blank">
          <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
        </el-link>
        <div class="ele-upload-list__item-content-action">
          <el-link :underline="false" @click="handleDelete(index)" type="danger">删除</el-link>
        </div>
      </li>
    </transition-group>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import * as SparkMD5 from "core-js";
import { mergeFile } from "@/api/system/file";

export default {
  name: "BigFileUpload",
  props: {
    // 值
    value: [String, Object, Array],
    // 数量限制
    limit: {
      type: Number,
      default: 5,
    },
    // 大小限制(GB)
    fileSize: {
      type: Number,
      default: 2,
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["doc", "xls", "ppt", "txt", "pdf", "png", "jpg", "jpeg"],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      number: 0,
      uploadList: [],
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/chunk", // 分片上传后台地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: [],
      options: {
        target: process.env.VUE_APP_BASE_API + "/common/chunk", // 分片上传后台地址
        chunkSize: '20480000',
        fileParameterName: 'upfile',
        //失败后最多自动重试上传次数
        maxChunkRetries: 3,
        //是否开启服务器分片校验，对应GET类型同名的target URL
        testChunks: true,
        /*
        服务器分片校验函数，判断秒传及断点续传,传入的参数是Uploader.Chunk实例以及请求响应信息
        reponse码是successStatuses码时，才会进入该方法
        reponse码如果返回的是permanentErrors 中的状态码，不会进入该方法，直接进入onFileError函数 ，并显示上传失败
        reponse码是其他状态码，不会进入该方法，正常走标准上传
        checkChunkUploadedByResponse函数直接return true的话，不再调用上传接口
        */
        checkChunkUploadedByResponse: function (chunk, response) {
          let objMessage = JSON.parse(response) ;
          if (objMessage.skipUpload) {
            return true ;
          }
          return (objMessage.uploadedChunks || []).indexOf(chunk.offset + 1) >= 0 ;
        }
      },
      fileStatusText: {
        success: '上传成功',
        error: '上传失败',
        uploading: '上传中',
        paused: '暂停',
        waiting: '等待上传'
      },
    };
  },
  watch: {
    value: {
      handler(val) {
        if (val) {
          let temp = 1;
          // 首先将值转为数组
          const list = Array.isArray(val) ? val : this.value.split(',');
          // 然后将数组转为对象数组
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              item = { name: item, url: item };
            }
            item.uid = item.uid || new Date().getTime() + temp++;
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // 是否显示提示
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
  },
  methods: {
    onFileAdded(file){
      this.computeMD5(file);
    },
    computeMD5(file) {
      file.pause() ;

      let fileSizeLimit = 2 * 1024 * 1024 * 1024;
      if(file.size() > fileSizeLimit){
        this.$message({
          showClose: true,
          message: '文件大小不能超过2G'
        });
        file.cancel();
      }

      let fileReader = new FileReader() ;
      let time = new Date().getTime() ;
      let blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice ;
      let currentChunk = 0 ;
      const chunkSize = 10 * 1024 * 1000 ;
      let chunks = Math.ceil(file.size() / chunkSize) ;
      let spark = new SparkMD5.ArrayBuffer();
      //由于计算整个文件的Md5太慢，因此采用只计算第1块文件的md5的方式
      let chunkNumberMD5 = 1;

      loadNext() ;

      fileReader.onload = (e => {
        spark.append(e.target.result) ;

        if(currentChunk < chunkNumberMD5hun) {
          loadNext() ;
        } else {
          let md5 = spark.end() ;
          file.uniqueIdentifier = md5 ;
          file.resume() ;
          console.log(`MD5计算完毕：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${file.size} 用时：${new Date().getTime() - time} ms`);
        }
      });

      fileReader.onerror = (e =>{
        this.error(`文件${file.name}读取出错，请检查该文件`) ;
        file.cancel() ;
      });

      function loadNext() {
        let start = currentChunk * chunkSize ;
        let end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize ;

        fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end)) ;
        currentChunk++;
        console.log("计算第"+currentChunk+"块");
      }
    },
    onFileSuccess(rootFile, file, response, chunk){

      mergeFile(file).then(response =>{
        if(responseData.data.code === 415){
          console.log("合并操作未成功，结果码："+responseData.data.code);
        }
      }).catch(function (error){
        console.log("合并后捕获的未知异常："+error);
      });;

    },

    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      // 校检文件类型
      // if (this.fileType) {
      //   let fileExtension = "";
      //   if (file.name.lastIndexOf(".") > -1) {
      //     fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
      //   }
      //   const isTypeOk = this.fileType.some((type) => {
      //     if (file.type.indexOf(type) > -1) return true;
      //     if (fileExtension && fileExtension.indexOf(type) > -1) return true;
      //     return false;
      //   });
      //   if (!isTypeOk) {
      //     this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join("/")}格式文件!`);
      //     return false;
      //   }
      // }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`上传文件大小不能超过 ${this.fileSize} GB!`);
          return false;
        }
      }
      this.$modal.loading("正在上传文件，请稍候...");
      this.number++;
      return true;
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
    },
    // 上传失败
    handleUploadError(err) {
      this.$modal.msgError("上传文件失败，请重试");
      this.$modal.closeLoading()
    },
    // 上传成功回调
    handleUploadSuccess(res) {
      this.uploadList.push({ name: res.fileName, url: res.fileName });
      if (this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileList));
        this.$modal.closeLoading();
      }
    },
    // 删除文件
    handleDelete(index) {
      this.fileList.splice(index, 1);
      this.$emit("input", this.listToString(this.fileList));
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url + separator;
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    }
  }
};
</script>

<style scoped lang="scss">
.upload-file-uploader {
  margin-bottom: 5px;
}
.upload-file-list .el-upload-list__item {
  border: 1px solid #e4e7ed;
  line-height: 2;
  margin-bottom: 10px;
  position: relative;
}
.upload-file-list .ele-upload-list__item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: inherit;
}
.ele-upload-list__item-content-action .el-link {
  margin-right: 10px;
}
</style>
