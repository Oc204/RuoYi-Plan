package com.ruoyi.web.controller.common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.File;
import com.ruoyi.system.domain.dto.ChunkInfo;
import com.ruoyi.system.domain.vo.FileInfoVO;
import com.ruoyi.system.service.IChunkInfoService;
import com.ruoyi.system.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IChunkInfoService chunkInfoService ;

    @Autowired
    private IFileService fileService ;

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    @PostMapping("/chunk")
    public AjaxResult uploadChunk(ChunkInfo chunkInfo){

        String result = "200" ;
        MultipartFile file = chunkInfo.getUpfile() ;
        log.info("file originName: {}, chunkNumber: {}", file.getOriginalFilename(), chunkInfo.getChunkNumber());

        try{
            byte[] bytes = file.getBytes() ;
            Path path = Paths.get(FileInfoUtils.generatePath(RuoYiConfig.getUploadPath(), chunkInfo)) ;

            Files.write(path, bytes) ;
            if(chunkInfoService.insertChunkInfo(chunkInfo) < 0) {
                result = "415" ;
            }
        }catch (Exception e){
            e.printStackTrace();
            result = "415";
        }

        return AjaxResult.success(result) ;
    }

    @PostMapping("/mergeFile")
    public AjaxResult mergeFile(FileInfoVO fileInfoVO){

        String result = "FALURE" ;

        File file = new File() ;
        file.setFilePath(fileInfoVO.getRelativePath());
//        file.setId(fileInfoVO.getId());
        // todo 记录文件大小
//        file.setTotalSize(fileInfoVO.getSize());

        // 进行文件合并操作
        String fileName = fileInfoVO.getName() ;
        String fileLocat = RuoYiConfig.getUploadPath() + "/" + fileInfoVO.getUniqueIdentifier() + "/" + fileName ;
        String folder = RuoYiConfig.getUploadPath() + "/" + fileInfoVO.getUniqueIdentifier() ;
        String fileSuccess = FileInfoUtils.merge(fileLocat, folder, fileName) ;

        if ("200".equals(fileSuccess)) {
            if (fileService.insertFile(file) > 0){
                result = "SUCCESS" ;
            }
        }

        //如果已经存在，则判断是否同一个项目，同一个项目的不用新增记录，否则新增
        if("300".equals(fileSuccess)) {
            List<File> fileList = fileService.selectFileList(file) ;
            if(fileList != null) {
                if(fileList.size() == 0 ){
                    if(fileService.insertFile(file) > 0){
                        result = "SUCCESS" ;
                    }
                }
            }

        }

        return AjaxResult.success(result) ;
    }
}

