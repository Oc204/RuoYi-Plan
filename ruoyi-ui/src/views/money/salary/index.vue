<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工资名称" prop="salaryName">
        <el-input
          v-model="queryParams.salaryName"
          placeholder="请输入工资名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发薪日期" prop="salaryDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.salaryDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发薪日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="发薪明细" prop="salaryDetail">
        <el-input
          v-model="queryParams.salaryDetail"
          placeholder="请输入发薪明细"
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
          @click="handleAdd"
          v-hasPermi="['mytodo:salary:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mytodo:salary:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mytodo:salary:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mytodo:salary:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salaryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center"  width="50"> <!-- prop="id"-->
        <template scope="scope">
          <span>{{(queryParams.pageNum-1)*queryParams.pageSize+(scope.$index + 1)}} </span>
        </template>
      </el-table-column>
<!--      <el-table-column label="${comment}" align="center" prop="id" />-->
      <el-table-column label="工资名称" align="center" prop="salaryName" />
<!--      <el-table-column label="用户id" align="center" prop="userId" />-->
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="排序号" align="center" prop="weight" />
      <el-table-column label="发薪日期" align="center" prop="salaryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.salaryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发薪明细" align="center" prop="salaryDetail" />
      <el-table-column label="关联图片" align="center" prop="salaryPic">
        <template slot-scope="scope">
<!--          　　<img v-for="item in scope.row.salaryPic" :src="item" width="40" height="40" class="head_pic"/>-->
          <ImagePreview v-for="item in scope.row.salaryPic" :src="item" width="50%" height="50%" class="head_pic" />
        </template>
      </el-table-column>

<!--      <el-table-column label="关联图片" align="center" prop="salaryPic" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mytodo:salary:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mytodo:salary:remove']"
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

    <!-- 添加或修改计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工资名称" prop="salaryName">
          <el-input v-model="form.salaryName" placeholder="请输入工资名称" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
<!--        <el-form-item label="排序号" prop="weight">-->
<!--          <el-input-number v-model="form.weight" placeholder="请输入排序号" />-->
<!--        </el-form-item>-->
        <el-form-item label="发薪日期" prop="salaryDate">
          <el-date-picker clearable size="small"
            v-model="form.salaryDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发薪日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发薪明细" prop="salaryDetail">
          <el-input v-model="form.salaryDetail" placeholder="请输入发薪明细" />
        </el-form-item>
        <el-form-item label="关联图片" prop="salaryPic">
          <ImageUpload ref="salaryPic" v-model="form.salaryPic"/>

<!--          <el-input v-model="form.salaryPic" placeholder="请输入关联图片" />-->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import MinioImageUpload from "@/components/MinioImageUpload"
import { listSalary, getSalary, delSalary, addSalary, updateSalary, picTest} from "@/api/mysalary/salary";

export default {
  name: "Salary",
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
      // 计划表格数据
      salaryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salaryName: null,
        weight: null,
        salaryDate: null,
        salaryDetail: null,
        salaryPic: null
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
    /** 查询计划列表 */
    getList() {
      this.loading = true;
      listSalary(this.queryParams).then(response => {
        this.salaryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        salaryName: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        weight: null,
        salaryDate: null,
        salaryDetail: null,
        salaryPic: null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSalary(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSalary(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSalary(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除计划编号为"' + ids + '"的数据项？').then(function() {
        return delSalary(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mysalary/salary/export', {
        ...this.queryParams
      }, `salary_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
