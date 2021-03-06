import request from '@/utils/request'

// 查询计划列表
export function listSalary(query) {
  return request({
    url: '/mysalary/salary/list',
    method: 'get',
    params: query
  })
}

// 查询计划详细
export function getSalary(id) {
  return request({
    url: '/mysalary/salary/' + id,
    method: 'get'
  })
}

// 新增计划
export function addSalary(data) {
  return request({
    url: '/mysalary/salary',
    method: 'post',
    data: data
  })
}

// 修改计划
export function updateSalary(data) {
  return request({
    url: '/mysalary/salary',
    method: 'put',
    data: data
  })
}

// 删除计划
export function delSalary(id) {
  return request({
    url: '/mysalary/salary/' + id,
    method: 'delete'
  })
}

// 下载图片测试
export function picTest() {
  return request({
    url: '/system/minio/downloadFile?bucket=ruoyi-plan&objectName=wallhaven-odk96l.jpg',
    method: 'get'
    // data: {
    //   bucket: 'ruoyi-plan',
    //   objectName: 'wallhaven-odk96l.jpg'
    // }
  })
}

// 查询年度分析
export function getAnalysisByYear(year) {
  return request({
    url: '/mysalary/analysis/year/' + year,
    method: 'get',
  })
}
