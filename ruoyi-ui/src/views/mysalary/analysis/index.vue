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
    <div id="echarts_box" style="width: 600px;height:400px;"></div>

  </div>

</template>

<script>
import { getAnalysisByYear} from "@/api/mysalary/salary";

export default {
  name: "SalaryAnalysis",
  data() {
    return {
      option: "" ,
      month: [] ,
      salarySum: [],
      year: undefined,
      myChart: "",
    }
  },
  created() {
    this.getAnalysisByYear() ;

  },
  methods: {
    getAnalysisByYear() {
      // this.$echarts.init(document.getElementById("echarts_box")).dispose(); // 销毁实例

      this.month = [] ;
      this.salarySum = [] ;

      // 发起请求，获取数据
      getAnalysisByYear(this.year).then(res=>{
        for(let i in res.data.month) {

          this.month.push(res.data.month[i]);
          this.salarySum.push(res.data.salarySum[i]);
        }
        this.showCharts() ;
      })

    },
    showCharts() {
      this.myChart = this.$echarts.init(document.getElementById('echarts_box'))

      this.option = {
        title: {
          text: '工资折线图'
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
      this.myChart.setOption(this.option)
    }
  },
  // DOM 渲染完成触发
  mounted() {

  }
}
</script>
