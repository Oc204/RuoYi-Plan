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
      <el-form-item label="点击次数">
        <el-input
          v-model="queryParams.clickTimesMin"
          placeholder="最小值"
          clearable
          size="small"
          type="number"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>_
      <el-form-item>
        <el-input
          v-model="queryParams.clickTimesMax"
          placeholder="最大值"
          clearable
          size="small"
          type="number"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除" prop="hasDelete">
        <el-select
          v-model="queryParams.hasDelete"
          placeholder="是否删除"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option label="是" value="0"></el-option>
          <el-option label="否" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否审核" prop="approve">
        <el-select
          v-model="queryParams.approve"
          placeholder="是否审核"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核情况" prop="isPass" >
        <el-select
          v-model="queryParams.isPass"
          placeholder="是否通过"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option label="已通过" value="1"></el-option>
          <el-option label="不通过" value="0"></el-option>
        </el-select>
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
          @click="handleAdd"
          v-hasPermi="['system:pic:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleUpload"
        >上传图片</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:pic:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="multiple"
          @click="handleApprovePass"
          v-hasPermi="['system:pic:approve']"
        >一键审核通过</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="multiple"
          @click="handleApproveNoPass"
          v-hasPermi="['system:pic:approve']"
        >一键审核不通过</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:pic:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="picList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="" align="center" prop="id" />-->
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="权重" align="center" prop="weight" />
      <el-table-column label="图片地址" align="center" prop="picPath">
        <template slot-scope="scope">
          <ImagePreview :src="scope.row.picPath" width="50%" height="50%" />
        </template>
      </el-table-column>
      <el-table-column label="是否已经删除" align="center" prop="hasDelete" >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.hasDelete === 1 "
            type="success"
            size="mini"
          >否</el-button>
          <el-button
            v-else-if="scope.row.hasDelete === 0"
            type="danger"
            size="mini"
          >是</el-button>
        </template>
      </el-table-column>
      <el-table-column label="是否已审核" align="center" prop="approve" >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.approve === 0 "
            type="danger"
            size="mini"
          >否</el-button>
          <el-button
            v-else-if="scope.row.approve === 1"
            type="success"
            size="mini"
          >是</el-button>
        </template>
      </el-table-column>
      <el-table-column label="是否通过审核" align="center" prop="isPass" >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.isPass === 0 "
            type="danger"
            size="mini"
          >否</el-button>
          <el-button
            v-else-if="scope.row.isPass === 1"
            type="success"
            size="mini"
          >是</el-button>
        </template>
      </el-table-column>
      <el-table-column label="点击次数" align="center" prop="clickTimes" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:pic:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:pic:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改图片对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="权重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入权重" />
        </el-form-item>
        <el-form-item label="图片地址" prop="picPath">
          <ImageUpload ref="picPath" v-model="form.picPath"/>
        </el-form-item>
        <el-form-item label="是否删除" prop="hasDelete">
          <el-radio-group v-model="form.hasDelete">
            <el-radio :label=0>是</el-radio>
            <el-radio :label=1>否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见" prop="isPass">
          <el-radio-group v-model="form.isPass">
            <el-radio :label=0>不通过</el-radio>
            <el-radio :label=1>通过</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否审批" prop="approve">
          <el-radio-group v-model="form.approve">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
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
import { listPic, getPic, delPic, addPic, updatePic, uploadPic, approvePass, approveNoPass } from "@/api/pic/pic";

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
        clickTimes: null,
        isPass: null,
        clickTimesMax:null,
        clickTimesMin: null

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
      listPic(this.addClickTimesRange(this.queryParams)).then(response => {
        this.picList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 一键审核通过
    handleApprovePass(row){
      const ids = row.id || this.ids;
      this.$modal.confirm('是否审核通过编号为"' + ids + '"的数据项？').then(function() {
        return approvePass(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("审核成功");
      }).catch(() => {});
    },
    // 一键审核不通过
    handleApproveNoPass(row){
      const ids = row.id || this.ids;
      this.$modal.confirm('是否审核不通过编号为"' + ids + '"的数据项？').then(function() {
        return approveNoPass(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("审核成功");
      }).catch(() => {});
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
        downloadTimes: null,
        clickTimesMax:null,
        clickTimesMin: null
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
      this.queryParams.clickTimesMax = null;
      this.queryParams.clickTimesMin = null
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图片";
    },
    /** 上传图片按钮操作 */
    handleUpload() {
      this.reset();
      this.upload_open = true;
      this.title = "上传图片";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPic(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图片";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePic(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPic(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除图片编号为"' + ids + '"的数据项？').then(function() {
        return delPic(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 添加点击范围
    addClickTimesRange(params) {
      let search = params;
      search.params = typeof (search.params) === 'object' && search.params !== null && !Array.isArray(search.params) ? search.params : {};
      search.params['clickTimesMin'] = search.clickTimesMin;
      search.params['clickTimesMax'] = search.clickTimesMax;

      return search;
    }
  }
};
</script>
