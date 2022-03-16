import request from '@/utils/request'

// 查询计划列表
export function listPay(query) {
  return request({
    url: '/mypay/pay/list',
    method: 'get',
    params: query
  })
}

// 查询计划详细
export function getPay(id) {
  return request({
    url: '/mypay/pay/' + id,
    method: 'get'
  })
}

// 新增计划
export function addPay(data) {
  return request({
    url: '/mypay/pay',
    method: 'post',
    data: data
  })
}

// 修改计划
export function updatePay(data) {
  return request({
    url: '/mypay/pay',
    method: 'put',
    data: data
  })
}

// 删除计划
export function delPay(id) {
  return request({
    url: '/mypay/pay/' + id,
    method: 'delete'
  })
}

// 查询年度分析
export function getAnalysisByYear(year) {
  return request({
    url: '/mypay/analysis/year/' + year,
    method: 'get',
  })
}
