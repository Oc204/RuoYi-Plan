<template>
  <div class="app-container">
    <!-- 添加或修改番茄对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="番茄时长" prop="tomatoTime">
<!--          <el-input v-model="form.tomatoTime" placeholder="请输入番茄时长(最大为60分钟)" />-->
          <el-select v-model="form.tomatoTime" placeholder="请选择番茄时长">
            <el-option
              v-for="item in tomatoOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listSetting, getSetting, addSetting, updateSetting,settingTomato } from "@/api/mytodo/tomatoSetting";

export default {
  name: "tomatoSetting",
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
      // 番茄表格数据
      settingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 番茄时间下拉框选项
      tomatoOption: [],
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.initTomatoOption();
  },
  methods: {
    /** 查询番茄列表 */
    getList() {
      this.loading = true;
      listSetting(this.queryParams).then(response => {
        this.settingList = response.rows;
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
        tomatoTime: null,
        userId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        active: null,
        flag: null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加番茄";
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.reset();
      settingTomato().then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "设置番茄";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSetting(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSetting(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    initTomatoOption() {
      for(let i=5;i<=60; i++){
        let json = {
            value: i,
            label: i+'分钟'
        };
        this.tomatoOption.push(json);
      }
    }
  }
};
</script>
