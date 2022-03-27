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
            <el-button-group style="margin-left: 20px">
              <el-button  @click='getIndexTomatoPieChartsData("DAY")'  >日</el-button>
              <el-button  @click='getIndexTomatoPieChartsData("WEEK")'  >周</el-button>
              <el-button  @click='getIndexTomatoPieChartsData("MONTH")'  >月</el-button>
            </el-button-group>
            <span style="margin-left: 10px">当前总时长：{{this.pieChartTotalTime}}分钟</span>
<!--            <span style="margin-left: 10px">计量单位：分</span>-->
            <pie-chart :chartData="pieChartData"/>
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

        <div id="linecharts" style="width: 100%;height: 400px;margin-top: 20px"></div>
      </el-card>
    </el-row>
  </div>
</template>

<script>

import { getIndexTomatoStatistics, getIndexTomatoLineCharts, getIndexTomatoPieChartsData} from "@/api/mytodo/tomato";

import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

export default {
  name: 'Index',
  components: {
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      // 番茄时长曲线图选中的值
      lineChartYear: undefined,
      lineChartSum: [],
      lineChartTime: [] ,
      // 累计专注时间
      totalTime: 0,
      // 本周专注时间
      weekTime: 0,
      // 今日专注时间
      todayTime: 0,
      // 累计完成任务
      totalTask: 0,
      // 本周完成任务
      weekTask: 0,
      // 今日完成任务
      todayTask: 0,
      // 番茄曲线数据
      lineChartData: {},
      // 饼图数据
      pieChartData: {
        pieChartLegend: [],
        pieChartSeries: []
      },
      // 饼图当前总时长
      pieChartTotalTime: 0,
      // 番茄曲线实例
      lineChart: "",
    }
  },
  created() {
    this.getIndexTomatoStatistics() ;
  },
  methods: {
    // 渲染当前页面的图表
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
    // 根据日、周、月标识获取饼图数据
    getIndexTomatoPieChartsData(flag){
      // 默认获取当日
      flag = flag === undefined ? "DAY" : flag ;
      // 绘画前对数据置空
      this.pieChartData = {
        pieChartLegend: [],
        pieChartSeries: []
      } ;
      this.pieChartTotalTime = 0;
      // 获取饼图数据
      getIndexTomatoPieChartsData(flag).then(response =>{
        if(response.data.length===0){
          this.pieChartData.pieChartLegend.push("暂无数据") ;
          this.pieChartData.pieChartSeries.push(
            { value: 0, name: "暂无数据" }
          ) ;

        } else {
          for (let i= 0;i< response.data.length; i++) {
            // 饼图番茄总时长
            this.pieChartTotalTime+=response.data[i].time ;
            // 添加饼图数据
            this.pieChartData.pieChartLegend.push(response.data[i].taskName) ;
            this.pieChartData.pieChartSeries.push(
              { value: response.data[i].time, name: response.data[i].taskName }
            ) ;
            console.log(response.data[i])
          }
        }
      })
    },
    // 番茄时长曲线数据获取
    getIndexTomatoLineCharts(flag){
      // 默认获取当年数据
      flag = flag === undefined ? "YEAR" : flag ;

      getIndexTomatoLineCharts(this.lineChartYear,flag).then(response =>{
        let count = response.data.time.length ;
        this.lineChartSum = [] ;
        this.lineChartTime = [] ;
        for(let i=0 ; i < count; i++){
          this.lineChartSum.push(response.data.sum[i]);
          this.lineChartTime.push(response.data.time[i]);
        }
        this.showLineCharts() ;
      })
    },
    showLineCharts() {
      this.lineChart = this.$echarts.init(document.getElementById('linecharts'))
      this.lineChartOption = {
        title: {
          text: '番茄时长曲线'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.lineChartTime
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.lineChartSum,
            type: 'line'
          }
        ]
      },
      console.log(this.lineChartSum)
      // 使用刚指定的配置项和数据，显示图表
      this.lineChart.setOption(this.lineChartOption) ;

      window.addEventListener("resize", () => {
        // 执行echarts自带的resize方法，即可做到让echarts图表自适应
        this.lineChart.resize();
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
