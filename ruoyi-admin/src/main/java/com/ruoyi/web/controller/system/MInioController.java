package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.MinioUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/system/minio")
public class MInioController {

    @Autowired
    MinioUtil minioUtil;

    @ApiOperation("上传一个文件")
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult fileupload(@RequestParam MultipartFile file) throws Exception { //, @RequestParam String bucket,        @RequestParam(required=false) String objectName

        String objectName = file.getName() ;
        String bucket = "ruoyi-plan" ;

        minioUtil.createBucket(bucket);
        if (objectName != null) {
            minioUtil.uploadFile(file.getInputStream(), bucket, objectName+"/"+file.getOriginalFilename());
        } else {
            minioUtil.uploadFile(file.getInputStream(), bucket, file.getOriginalFilename());
        }
        return AjaxResult.success();
    }

    @ApiOperation("下载一个文件")
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(@RequestParam String bucket, @RequestParam String objectName,
                             HttpServletResponse response) throws Exception {
        InputStream stream = minioUtil.download(bucket, objectName);
        ServletOutputStream output = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(stream, output);
    }
}
