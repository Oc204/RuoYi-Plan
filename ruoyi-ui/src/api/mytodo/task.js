import request from '@/utils/request'

// 查询清单任务关联列表
export function listTask(query) {
  return request({
    url: '/mytodo/task/list',
    method: 'get',
    params: query
  })
}

// 查询当前用户当前清单的任务列表
export function currentTaskList(query) {
  return request({
    url: '/mytodo/task/user/plist/list',
    method: 'get',
    params: query
  })
}

// 查询清单任务关联详细
export function getTask(id) {
  return request({
    url: '/mytodo/task/' + id,
    method: 'get'
  })
}

// 新增清单任务关联
export function addTask(data) {
  return request({
    url: '/mytodo/task',
    method: 'post',
    data: data
  })
}

// 修改清单任务关联
export function updateTask(data) {
  return request({
    url: '/mytodo/task',
    method: 'put',
    data: data
  })
}

// 删除清单任务关联
export function delTask(id) {
  return request({
    url: '/mytodo/task/' + id,
    method: 'delete'
  })
}
