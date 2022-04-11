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
      <span> 当年收入总和：{{salaryYearSum}} 元</span>
    </div>
    <div id="echarts_box" style="width: 100%;height: 400px;margin-top: 20px"></div>
  </div>

</template>

<script>
import { getAnalysisByYear} from "@/api/mysalary/salary";

export default {
  name: "SalaryAnalysis",
  data() {
    return {
      option: "" ,
      // 月份数组
      month: [] ,
      // 收入总和数组
      salarySum: [],
      // 年搜索条件
      year: undefined,
      // 折现图实例
      lineCharts: "",
      // 当前年收入总和
      salaryYearSum:0
    }
  },
  created() {
    this.getAnalysisByYear() ;
  },
  methods: {
    getAnalysisByYear() {
      this.month = [] ;
      this.salarySum = [] ;
      this.salaryYearSum = 0 ;

      // 发起请求，获取数据
      getAnalysisByYear(this.year).then(res=>{
        for(let i in res.data.month) {

          this.month.push(res.data.month[i]);
          this.salarySum.push(res.data.salarySum[i]);
          this.salaryYearSum+=res.data.salarySum[i];
        }
        this.showCharts() ;
      })

    },
    showCharts() {
      this.lineCharts = this.$echarts.init(document.getElementById('echarts_box'))
      this.option = {
        title: [{
          text: '工资折线图'
        }],
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
      // console.log(this.month)
      // console.log(this.salarySum)
      // 使用刚指定的配置项和数据，显示图表
      this.lineCharts.setOption(this.option)

      window.addEventListener("resize", () => {
        // 执行echarts自带的resize方法，即可做到让echarts图表自适应
        this.lineCharts.resize();
      });
    }
  }
}
</script>
