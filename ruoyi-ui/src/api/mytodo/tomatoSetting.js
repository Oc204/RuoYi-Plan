import request from '@/utils/request'

// 查询番茄列表
export function listSetting(query) {
  return request({
    url: '/mytodo/setting/tomato/list',
    method: 'get',
    params: query
  })
}

// 查询番茄详细
export function getSetting(id) {
  return request({
    url: '/mytodo/setting/tomato/' + id,
    method: 'get'
  })
}

// 新增番茄
export function addSetting(data) {
  return request({
    url: '/mytodo/setting/tomato',
    method: 'post',
    data: data
  })
}

// 修改番茄
export function updateSetting(data) {
  return request({
    url: '/mytodo/setting/tomato',
    method: 'put',
    data: data
  })
}

// 设置番茄
export function settingTomato() {
  return request({
    url: '/mytodo/setting/tomato/getTomato',
    method: 'get',
  })
}

// 删除番茄
export function delSetting(id) {
  return request({
    url: '/mytodo/setting/tomato/' + id,
    method: 'delete'
  })
}
