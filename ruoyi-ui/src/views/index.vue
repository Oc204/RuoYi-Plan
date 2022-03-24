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

    <el-row :gutter="32">
      <el-card>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
      </el-card>
    </el-row>

  </div>
</template>

<script>

import { getIndexTomatoStatistics, getIndexTomatoLineCharts} from "@/api/mytodo/tomato";

import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

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
      myChart: "",

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
          text: '番茄曲线'
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
