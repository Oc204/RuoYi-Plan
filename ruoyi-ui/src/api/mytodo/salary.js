import request from '@/utils/request'

// 查询计划列表
export function listSalary(query) {
  return request({
    url: '/mytodo/salary/list',
    method: 'get',
    params: query
  })
}

// 查询计划详细
export function getSalary(id) {
  return request({
    url: '/mytodo/salary/' + id,
    method: 'get'
  })
}

// 新增计划
export function addSalary(data) {
  return request({
    url: '/mytodo/salary',
    method: 'post',
    data: data
  })
}

// 修改计划
export function updateSalary(data) {
  return request({
    url: '/mytodo/salary',
    method: 'put',
    data: data
  })
}

// 删除计划
export function delSalary(id) {
  return request({
    url: '/mytodo/salary/' + id,
    method: 'delete'
  })
}
