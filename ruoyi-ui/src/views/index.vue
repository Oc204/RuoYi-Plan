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


    <!--    <panel-group @handleSetLineChartData="handleSetLineChartData" />-->

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row :gutter="32">
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
    </el-row>


  </div>
</template>

<script>

import { getIndexTomatoStatistics } from "@/api/mytodo/tomato";

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
      totalTime: 0,
      totalTask: 0,
      weekTime: 0,
      weekTask: 0,
      todayTime: 0,
      todayTask: 0,
      lineChartData: lineChartData.newVisitis
    }
  },
  created() {
    this.getIndexTomatoStatistics() ;
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    getIndexTomatoStatistics(){
      getIndexTomatoStatistics().then(response =>{

        this.totalTime = response.data.totalTime ;
        this.totalTask = response.data.totalTask ;
        this.weekTime = response.data.weekTime ;
        this.weekTask = response.data.weekTask ;
        this.todayTime = response.data.todayTime ;
        this.todayTask = response.data.todayTask ;
      })
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
