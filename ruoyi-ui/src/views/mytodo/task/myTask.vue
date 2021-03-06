<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="当前列表所属清单：">{{plistName}}

      </el-form-item>
      <el-form-item label="任务名称" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          placeholder="请输入任务名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
        <el-input
          v-model="queryParams.plistId"
          @keyup.enter.native="handleQuery"
          v-show="false"
        />
      </el-form-item>
      <el-form-item label="优先级" prop="weight">
        <el-select
          v-model="queryParams.weight"
          placeholder="任务优先级"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option label="低" value="0"></el-option>
          <el-option label="中" value="1"></el-option>
          <el-option label="高" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否完成" prop="finish">
        <el-select
          v-model="queryParams.finish"
          placeholder="完成状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
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
          v-hasPermi="['system:menu:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="taskList"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="任务名称" align="center" prop="taskName" />
      <el-table-column label="番茄数" align="center" prop="tomatoNumber" />
      <el-table-column label="到期日" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="level" >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.level === '2' "
            type="danger"
            size="mini"
          >高</el-button>
          <el-button
            v-else-if="scope.row.level === '1'"
            type="warning"
            size="mini"
          >中</el-button>
          <el-button
            v-else-if="scope.row.level === '0'"
            type="success"
            size="mini"
          >低</el-button>
        </template>
      </el-table-column>
      <el-table-column label="是否完成" align="center" prop="finish" >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.finish === '0' "
            type="danger"
            size="mini"
          >否</el-button>
          <el-button
            v-else-if="scope.row.finish === '1'"
            type="success"
            size="mini"
          >是</el-button>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:menu:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:menu:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级任务">
              <treeselect
                v-model="form.parentId"
                :options="taskListOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级任务"
              />
            </el-form-item>
          </el-col>

          <el-col :span="24">
          <el-form-item label="任务名称" prop="taskName">
            <el-input v-model="form.taskName" placeholder="请输入任务名称" />
          </el-form-item>
          </el-col>
          <el-col :span="24">
          <el-form-item label="番茄数" prop="tomatoNumber">
            <el-input type="number" v-model="form.tomatoNumber" placeholder="请输入番茄数" />
          </el-form-item>
          </el-col>
          <el-col :span="24">
          <el-form-item label="到期日" prop="dueDate">
            <el-date-picker clearable size="small"
                            v-model="form.dueDate"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择到期日">
            </el-date-picker>
          </el-form-item>
          </el-col>
          <el-col :span="24">
          <el-form-item label="优先级" prop="level">
            <el-radio-group v-model="form.level">
              <el-radio label="0">低</el-radio>
              <el-radio label="1">中</el-radio>
              <el-radio label="2">高</el-radio>
            </el-radio-group>
          </el-form-item>
          </el-col>
            <el-col :span="24">
          <el-form-item label="是否完成" prop="finish">
            <el-radio-group v-model="form.finish">
              <el-radio label="0">否</el-radio>
              <el-radio label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
            </el-col>
          <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
          </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="weight">
              <el-input-number v-model="form.weight" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { listTask, getTask, delTask, addTask, updateTask ,currentTaskList} from "@/api/mytodo/task";

import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import IconSelect from "@/components/IconSelect";

export default {
  name: "Menu",
  dicts: ['sys_show_hide', 'sys_normal_disable'],
  components: { Treeselect, IconSelect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 任务表格树数据
      taskList: [],
      // 关联的清单id
      plistId: undefined,
      // 清单树选项
      taskListOptions: [],
      // 当前任务列表所属清单
      plistName: undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部折叠
      isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        taskName: undefined,
        weight: undefined,
        finish: undefined,
        visible: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        weight: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ],
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        level: [
          { required: true, message: "请选择优先级", trigger: "blur" }
        ],
        finish: [
          { required: true, message: "请选择完成状态", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.initData();
    this.getList();
  },
  methods: {
    /** 初始化操作*/
    initData() {
      this.plistId = this.$route.query && this.$route.query.plistId ;
      this.form.plistId = this.plistId ;
      this.plistName = this.$route.query && this.$route.query.plistName ;
      // console.log("get plistId" + this.plistId + "  this.plistName: " + this.plistName) ;
    },
    // 选择图标
    selected(name) {
      this.form.icon = name;
    },
    /** 查询任务列表 */
    getList() {
      this.loading = true;
      this.queryParams.plistId = this.plistId ;
      currentTaskList(this.queryParams).then(response => {
        // console.log("格式化查询数据"+JSON.stringify(this.queryParams)) ;
        this.taskList = this.handleTree(response.data, "id");
        this.loading = false;
      });
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.taskName,
        children: node.children
      };
    },
    /** 查询菜单下拉树结构 */
    getTreeselect() {
      currentTaskList().then(response => {
        this.taskListOptions = [];
        const task = { id: 0, taskName: '主任务', children: [] };
        task.children = this.handleTree(response.data, "id");
        this.taskListOptions.push(task);
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
        id: undefined,
        parentId: 0,
        taskName: undefined,
        weight: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.parentId = row.id;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加清单";
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      getTask(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改任务";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTask(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除名称为"' + row.listName + '"的数据项？').then(function() {
        return delTask(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
