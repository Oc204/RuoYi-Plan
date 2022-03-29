<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="权重" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入权重"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下载次数" prop="downloadTimes">
        <el-input
          v-model="queryParams.downloadTimes"
          placeholder="请输入下载次数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

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
    <el-image v-for="url in imageList" :key="url" :src="url" lazy style="width: 200px;height: 100px"></el-image>
    </div>
    <el-table v-loading="loading" :data="picList" @selection-change="handleSelectionChange" v-show="false">
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="权重" align="center" prop="weight" />
      <el-table-column label="图片地址" align="center" prop="picPath">
        <template slot-scope="scope">
          <ImagePreview :src="scope.row.picPath" width="50%" height="50%" />
        </template>
      </el-table-column>
      <el-table-column label="下载次数" align="center" prop="downloadTimes" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 上传图片对话框 -->
    <el-dialog :title="title" :visible.sync="upload_open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图片" prop="picPath">
          <ImageUpload ref="picPath" v-model="form.picPath"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPic">确 定</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPic, getPic, delPic, addPic, updatePic } from "@/api/pic/pic";
import {uploadPic} from "../../../api/pic/pic";

export default {
  name: "Pic",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
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
      open: false,
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
      listPic(this.queryParams).then(response => {
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
        return this.imageList.push(process.env.VUE_APP_BASE_API + item.picPath);
      });
      return this.imageList;
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
            this.getList();
          });
        }
      });
    },

  }
};
</script>
