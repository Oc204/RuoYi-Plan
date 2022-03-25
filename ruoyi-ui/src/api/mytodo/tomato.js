import request from '@/utils/request'

// 查询番茄列表
export function listTomato(query) {
  return request({
    url: '/system/tomato/list',
    method: 'get',
    params: query
  })
}

// 查询番茄详细
export function getTomato(id) {
  return request({
    url: '/system/tomato/' + id,
    method: 'get'
  })
}

// 查询番茄详细
export function getIndexTomatoStatistics() {
  return request({
    url: '/mytodo/tomato/index/statistics',
    method: 'get'
  })
}

// 查询番茄时间曲线
export function getIndexTomatoLineCharts(year, flag) {
  return request({
    url: '/tomato/analysis/year/'+year +"/" + flag,
    method: 'get'
  })
}

// 查询番茄饼图
export function getIndexTomatoPieChartsData(flag) {
  return request({
    url: '/tomato/analysis/index/pieCharts/'+"WEEK" ,
    method: 'get'
  })
}

// 新增番茄
export function addTomato(data) {
  return request({
    url: '/system/tomato',
    method: 'post',
    data: data
  })
}

// 修改番茄
export function updateTomato(data) {
  return request({
    url: '/system/tomato',
    method: 'put',
    data: data
  })
}

// 删除番茄
export function delTomato(id) {
  return request({
    url: '/system/tomato/' + id,
    method: 'delete'
  })
}
