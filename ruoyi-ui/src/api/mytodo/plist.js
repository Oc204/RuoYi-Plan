import request from '@/utils/request'

// 查询计划清单关联列表
export function listPlist(query) {
  return request({
    url: '/system/plist/list',
    method: 'get',
    params: query
  })
}

// 查询计划清单关联详细
export function getPlist(id) {
  return request({
    url: '/system/plist/' + id,
    method: 'get'
  })
}

// 新增计划清单关联
export function addPlist(data) {
  return request({
    url: '/system/plist',
    method: 'post',
    data: data
  })
}

// 修改计划清单关联
export function updatePlist(data) {
  return request({
    url: '/system/plist',
    method: 'put',
    data: data
  })
}

// 删除计划清单关联
export function delPlist(id) {
  return request({
    url: '/system/plist/' + id,
    method: 'delete'
  })
}
