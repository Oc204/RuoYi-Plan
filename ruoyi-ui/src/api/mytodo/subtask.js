import request from '@/utils/request'

// 查询子任务列表
export function listSubtask(query) {
  return request({
    url: '/mytodo/subtask/list',
    method: 'get',
    params: query
  })
}

// 查询子任务详细
export function getSubtask(id) {
  return request({
    url: '/mytodo/subtask/' + id,
    method: 'get'
  })
}

// 新增子任务
export function addSubtask(data) {
  return request({
    url: '/mytodo/subtask',
    method: 'post',
    data: data
  })
}

// 修改子任务
export function updateSubtask(data) {
  return request({
    url: '/mytodo/subtask',
    method: 'put',
    data: data
  })
}

// 删除子任务
export function delSubtask(id) {
  return request({
    url: '/mytodo/subtask/' + id,
    method: 'delete'
  })
}
