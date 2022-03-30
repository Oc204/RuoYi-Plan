import request from '@/utils/request'

// 查询图片列表
export function listPic(query) {
  return request({
    url: '/pic/list',
    method: 'get',
    params: query
  })
}

// 查询公开图片墙列表
export function publicPic(query) {
  return request({
    url: '/pic/public/list',
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

// 上传图片(所有用户通用方式)
export function uploadPic(data) {
  return request({
    url: '/pic/upload',
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

// 审核通过
export function approvePass(id) {
  return request({
    url: '/pic/pass/' + id,
    method: 'get'
  })
}

// 审核不通过
export function approveNoPass(id) {
  return request({
    url: '/pic/noPass/' + id,
    method: 'get'
  })
}
