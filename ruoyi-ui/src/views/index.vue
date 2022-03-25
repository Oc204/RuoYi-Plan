<template>
  <div class="app-container home">
    <el-row :gutter="20">

    <el-card class="box-card">
      <el-row>
        <el-col :span="4" align="center">
          <h1>{{this.totalTime}}</h1>
          <ul>
            <li>累计专注时间(分钟)</li>
          </ul>
        </el-col>
        <el-col :span="4" align="center">
          <h1>{{this.weekTime}}</h1>
          <ul>
            <li>本周专注时间</li>
          </ul>
        </el-col>
        <el-col :span="4" align="center">
          <h1>{{this.todayTime}}</h1>
          <ul>
            <li>今日专注时间</li>
          </ul>
        </el-col>
        <el-col :span="4" align="center">
          <h1>{{this.totalTask}}</h1>
          <ul>
            <li>累计完成任务</li>
          </ul>
        </el-col>
        <el-col :span="4" align="center">
          <h1>{{this.weekTask}}</h1>
          <ul>
            <li>本周完成任务</li>
          </ul>
        </el-col>
        <el-col :span="4" align="center">
          <h1 >{{this.todayTask}}</h1>
          <ul>
            <li>今日完成任务</li>
          </ul>
        </el-col>
      </el-row>
    </el-card>
    </el-row>

    <el-row :gutter="32">
      <el-card>
        <el-col :xs="24" :sm="24" :lg="8">
          <div class="chart-wrapper">
            <div id="pie-chart" style="width: 100%;height: 400px;margin-top: 20px"></div>

<!--            <pie-chart :chart-data="pieChartData"/>-->
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="16">
          <div class="chart-wrapper">
            <bar-chart />
          </div>
        </el-col>
      </el-card>
    </el-row>

    <el-row :gutter="20">

      <el-card >
        <el-date-picker
          v-model="lineChartYear"
          type="year"
          value-format="yyyy"
          placeholder="选择年"
          @change="getIndexTomatoLineCharts()"
        />
        <el-button-group style="margin-left: 20px">
          <el-button  @click='getIndexTomatoLineCharts("YEAR")'   >年</el-button>
          <el-button  @click='getIndexTomatoLineCharts("MONTH")'  >月</el-button>
        </el-button-group>

        <div id="echarts_box" style="width: 100%;height: 400px;margin-top: 20px"></div>

      </el-card>
    </el-row>
  </div>
</template>

<script>

import { getIndexTomatoStatistics, getIndexTomatoLineCharts, getIndexTomatoPieChartsData} from "@/api/mytodo/tomato";

import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      lineChartYear: undefined,
      sum: [],
      time: [] ,
      totalTime: 0,
      totalTask: 0,
      weekTime: 0,
      weekTask: 0,
      todayTime: 0,
      todayTask: 0,
      lineChartData: {},
      pieChartLegend: [],
      pieChartSeries: [],
      pieChartData: {},
      myChart: "",
      pieChart: "",

    }
  },
  created() {
    this.getIndexTomatoStatistics() ;
  },
  methods: {
    getIndexTomatoStatistics(){
      getIndexTomatoStatistics().then(response =>{
        this.totalTime = response.data.totalTime ;
        this.totalTask = response.data.totalTask ;
        this.weekTime = response.data.weekTime ;
        this.weekTask = response.data.weekTask ;
        this.todayTime = response.data.todayTime ;
        this.todayTask = response.data.todayTask ;
      })

      this.getIndexTomatoLineCharts() ;
      this.getIndexTomatoPieChartsData() ;
    },
    getIndexTomatoPieChartsData(){
      getIndexTomatoPieChartsData().then(response =>{
        for (let i= 0;i< response.data.length; i++) {
          this.pieChartLegend.push(response.data[i].taskName) ;
          this.pieChartSeries.push(
            { value: response.data[i].time, name: response.data[i].taskName }
          ) ;
          console.log(response.data[i])
        }

        this.pieChartData.pieChartLegend = this.pieChartLegend ;
        this.pieChartData.pieChartSeries = this.pieChartSeries ;
        console.log(JSON.stringify(this.pieChartData) + "father")
        this.showPieChart();
      })
    },
    getIndexTomatoLineCharts(flag){
      flag = flag === undefined ? "YEAR" : flag ;

      getIndexTomatoLineCharts(this.lineChartYear,flag).then(response =>{
        let count = response.data.time.length ;
        this.sum = [] ;
        this.time = [] ;
        for(let i=0 ; i < count; i++){
          this.sum.push(response.data.sum[i]);
          this.time.push(response.data.time[i]);
        }
        this.showCharts() ;
      })
    },
    showCharts() {
      this.myChart = this.$echarts.init(document.getElementById('echarts_box'))
      this.option = {
        title: {
          text: '番茄时长曲线'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.time
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.sum,
            type: 'line'
          }
        ]
      },
        console.log(this.time)
      console.log(this.sum)
      // 3. 使用刚指定的配置项和数据，显示图表
      this.myChart.setOption(this.option) ;

      window.addEventListener("resize", () => {
        // 执行echarts自带的resize方法，即可做到让echarts图表自适应
        this.myChart.resize();
      });
    },
    showPieChart(){
      this.pieChart = this.$echarts.init(document.getElementById('pie-chart')) ;
      this.pieChartoption = {
        title: {
          text: '清单时间占比',
          subtext: 'Fake Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '清单时间占比',
            type: 'pie',
            radius: '50%',
            data: this.pieChartSeries,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },

      // 3. 使用刚指定的配置项和数据，显示图表
     this.pieChart.setOption(this.pieChartoption) ;

      window.addEventListener("resize", () => {
        // 执行echarts自带的resize方法，即可做到让echarts图表自适应
        this.pieChart.resize();
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.home {

  h1 {
    color: #E80B10FF;
    font-family: inherit;
    font-weight: 800;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  ul {
    list-style-type: none;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}


</style>
