import request from '@/utils/request'

// 查询清单任务关联列表
export function listTask(query) {
  return request({
    url: '/system/task/list',
    method: 'get',
    params: query
  })
}

// 查询清单任务关联详细
export function getTask(id) {
  return request({
    url: '/system/task/' + id,
    method: 'get'
  })
}

// 新增清单任务关联
export function addTask(data) {
  return request({
    url: '/system/task',
    method: 'post',
    data: data
  })
}

// 修改清单任务关联
export function updateTask(data) {
  return request({
    url: '/system/task',
    method: 'put',
    data: data
  })
}

// 删除清单任务关联
export function delTask(id) {
  return request({
    url: '/system/task/' + id,
    method: 'delete'
  })
}
