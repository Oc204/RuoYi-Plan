<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="支出名称" prop="payName">
        <el-input
          v-model="queryParams.payName"
          placeholder="请输入支出名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支出日期">
        <el-date-picker clearable size="small"
                        v-model="dateRange"
                        type="daterange"
                        value-format="yyyy-MM-dd"
                        range-separator="-"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="支出明细">
        <el-input
          v-model="queryParams.payMin"
          placeholder="最小值"
          clearable
          size="small"
          type="number"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>_
      <el-form-item>
        <el-input
          v-model="queryParams.payMax"
          placeholder="最大值"
          clearable
          size="small"
          type="number"
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
          v-hasPermi="['mypay:pay:add']"
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
          v-hasPermi="['mypay:pay:edit']"
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
          v-hasPermi="['mypay:pay:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mypay:pay:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="payList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center"  width="50"> <!-- prop="id"-->
        <template scope="scope">
          <span>{{(queryParams.pageNum-1)*queryParams.pageSize+(scope.$index + 1)}} </span>
        </template>
      </el-table-column>
      <el-table-column label="支出名称" align="center" prop="payName" />
<!--      <el-table-column label="用户id" align="center" prop="userId" />-->
      <el-table-column label="备注" align="center" prop="remark" />
<!--      <el-table-column label="排序号" align="center" prop="weight" />-->
      <el-table-column label="支出日期" align="center" prop="payDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支出明细" align="center" prop="payDetail" />
      <el-table-column label="关联图片" align="center" prop="payPic" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.payPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mypay:pay:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mypay:pay:remove']"
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
        <el-form-item label="支出名称" prop="payName">
          <el-input v-model="form.payName" placeholder="请输入支出名称" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="支出日期" prop="payDate">
          <el-date-picker clearable size="small"
            v-model="form.payDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择支出日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="支出明细" prop="payDetail">
          <el-input v-model="form.payDetail" placeholder="请输入支出明细" />
        </el-form-item>
        <el-form-item label="关联图片">
          <image-upload v-model="form.payPic"/>
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
import { listPay, getPay, delPay, addPay, updatePay } from "@/api/mypay/pay";

export default {
  name: "Pay",
  data() {
    //  <!--金额数字-->
    let checkMoney = (rule, value, callback) => {
      var reg = /^(([1-9]{1}\d*)|(0{1}))(\.\d{1,2})?$/ ;
      if (!reg.test(value)) {
        callback(new Error('请输入金额数字，最多两位小数'))
      } else {
        callback()
      }
    }

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
      payList: [],
      // 弹出层标题
      title: "",
      // 日期区间
      dateRange: [],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        payName: null,
        weight: null,
        payDate: null,
        payDetail: null,
        payMin: null,
        payMax: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        payDetail: [
          { validator: checkMoney, trigger: 'change' }
        ],
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
      this.queryParams = this.addDateRange(this.queryParams, this.dateRange) ;
      listPay(this.addPayRange(this.queryParams)).then(response => {
        this.payList = response.rows;
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
        payName: null,
        userId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        weight: null,
        payDate: null,
        payDetail: null,
        payPic: null
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
      this.dateRange = [];
      this.queryParams.payMax = null;
      this.queryParams.payMin = null;
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
      getPay(id).then(response => {
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
            updatePay(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPay(this.form).then(response => {
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
        return delPay(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/pay/export', {
        ...this.queryParams
      }, `pay_${new Date().getTime()}.xlsx`)
    },
    // 添加日期范围
    addPayRange(params) {
      let search = params;
      search.params = typeof (search.params) === 'object' && search.params !== null && !Array.isArray(search.params) ? search.params : {};
      search.params['payMin'] = search.payMin;
      search.params['payMax'] = search.payMax;

      return search;
    }
  }
};
</script>
