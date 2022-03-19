<template>
  <div class="app-container">
    <div class="block">
      <el-date-picker
        v-model="year"
        type="year"
        value-format="yyyy"
        placeholder="选择年"
        @change="getAnalysisByYear"
      >
      </el-date-picker>
    </div>
    <div id="echarts_box" style="width: 100%;height: 400px;margin-top: 20px"></div>
    <el-table v-loading="loading" :data="payList">
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
            @click="handleDetail(scope.row)"
            v-hasPermi="['mypay:pay:edit']"
          >查看详情</el-button>
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
      <el-form ref="form" :model="form" label-width="80px" :disabled="true">
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
          <image-upload v-model="form.payPic" :aria-readonly="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancel">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import { listPay, getPay, getAnalysisByYear, listByTime} from "@/api/mypay/pay";

export default {
  name: "payAnalysis",
  data() {
    return {
      option: "" ,
      month: [] ,
      salarySum: [],
      year: undefined,
      myChart: "",
      // 计划表格数据
      payList: [],
      // 遮罩层
      loading: true,
      // 是否显示弹出层
      open: false,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        payName: null,
        userId: null,
        weight: null,
        payDate: null,
        payDetail: null,
        payPic: null
      },
      // 表单参数
      form: {},
      // 弹出层标题
      title: "",
    }
  },
  created() {
    this.getAnalysisByYear() ;
  },
  methods: {
    /** 查询支出列表 */
    getList(year, month) {
      this.loading = true;
      listByTime(year, month).then(response => {
        this.payList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getAnalysisByYear() {
      this.month = [] ;
      this.salarySum = [] ;

      // 发起请求，获取数据
      getAnalysisByYear(this.year).then(res=>{
        for(let i in res.data.month) {
          this.month.push(res.data.month[i]);
          this.salarySum.push(res.data.paySum[i]);
        }
        this.showCharts() ;
      })

      this.getList(this.year,"");
    },
    showCharts() {
      this.myChart = this.$echarts.init(document.getElementById('echarts_box'))
      this.option = {
        title: {
          text: '支出折线图'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.month
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.salarySum,
            type: 'line'
          }
        ]
      },
      console.log(this.month)
      console.log(this.salarySum)
      // 3. 使用刚指定的配置项和数据，显示图表
      this.myChart.setOption(this.option) ;

      window.addEventListener("resize", () => {
        // 执行echarts自带的resize方法，即可做到让echarts图表自适应
        this.myChart.resize();
      });

      this.myChart.on('click', (params)=> {
        console.log(params.name);
        let data = params.name.split("-");
        this.getList(data[0],data[1]);
      });
    },
    /** 查看详情按钮操作 */
    handleDetail(row) {
      // this.reset();
      const id = row.id || this.ids
      getPay(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "支出详情";
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      // this.reset();
    },

  },
  // DOM 渲染完成触发
  mounted() {

  }
}
</script>
