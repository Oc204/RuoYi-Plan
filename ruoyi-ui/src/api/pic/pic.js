import request from '@/utils/request'

// 查询图片列表
export function listPic(query) {
  return request({
    url: '/pic/list',
    method: 'get',
    params: query
  })
}

// 查询图片详细
export function getPic(id) {
  return request({
    url: '/pic/' + id,
    method: 'get'
  })
}

// 新增图片
export function addPic(data) {
  return request({
    url: '/pic',
    method: 'post',
    data: data
  })
}

// 修改图片
export function updatePic(data) {
  return request({
    url: '/pic',
    method: 'put',
    data: data
  })
}

// 删除图片
export function delPic(id) {
  return request({
    url: '/pic/' + id,
    method: 'delete'
  })
}
