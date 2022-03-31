<template>
  <div class="app-container">
    <el-header>
      <span style="font-size: large;font-family: Bahnschrift;font-weight: bold">丁真の博物馆</span>
    <el-image :src="require('@/assets/images/head.png')" style="height: 100%"></el-image>
    </el-header>
    <el-main>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleUpload"
        >上传图片</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <div class="demo-image__lazy">
      <PublicImagePreview v-for="url in picList"  :src="url.picPath" :id="url.id" lazy  style="width: 18%;height: 18%;margin-right: 10px" />
    </div>

    <!-- 上传图片对话框 -->
    <el-dialog :title="title" :visible.sync="upload_open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图片" prop="picPath">
          <PublicImageUpload ref="picPath" v-model="form.picPath"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPic">确 定</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
      </div>
    </el-dialog>
    </el-main>
  </div>

</template>

<script>
import {uploadPic, publicPic } from "@/api/pic/pic";
import PublicImageUpload from "@/components/PublicImageUpload"
import PublicImagePreview from "@/components/PublicImagePreview"

export default {
  name: "Wall",
  components:{
    PublicImageUpload,
    PublicImagePreview
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 图片表格数据
      picList: [],
      // 图片列表
      imageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      upload_open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        weight: null,
        picPath: null,
        hasDelete: null,
        approve: null,
        downloadTimes: null,
        isPass: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        picPath: [
          { required: true, message: "上传图片不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询图片列表 */
    getList() {
      this.loading = true;
      publicPic(this.queryParams).then(response => {
        this.picList = response.rows;
        this.total = response.total;
        this.loading = false;

        this.setImageList(this.picList);
      });
    },
    // 设置图片列表数据
    setImageList(picList){
      this.imageList = [];
      picList.forEach(item => {
        this.imageList.push(process.env.VUE_APP_BASE_API + item.picPath);
      });
    },
    // 取消按钮
    cancelUpload() {
      this.upload_open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        weight: null,
        picPath: null,
        hasDelete: null,
        approve: null,
        downloadTimes: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 上传图片按钮操作 */
    handleUpload() {
      this.reset();
      this.upload_open = true;
      this.title = "上传图片";
    },
    /** 提交上传图片按钮 */
    submitPic() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          uploadPic(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.upload_open = false;
            // this.getList();
          });
        }
      });
    },

  }
};
</script>
