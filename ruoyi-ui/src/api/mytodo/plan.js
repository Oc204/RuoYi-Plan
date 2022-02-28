import request from '@/utils/request'

// 查询计划列表
export function listPlan(query) {
  return request({
    url: '/mytodo/plan/list',
    method: 'get',
    params: query
  })
}

// 查询计划详细
export function getPlan(id) {
  return request({
    url: '/mytodo/plan/' + id,
    method: 'get'
  })
}

// 新增计划
export function addPlan(data) {
  return request({
    url: '/mytodo/plan',
    method: 'post',
    data: data
  })
}

// 修改计划
export function updatePlan(data) {
  return request({
    url: '/mytodo/plan',
    method: 'put',
    data: data
  })
}

// 删除计划
export function delPlan(id) {
  return request({
    url: '/mytodo/plan/' + id,
    method: 'delete'
  })
}
