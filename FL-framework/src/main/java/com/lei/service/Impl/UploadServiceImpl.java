package com.lei.service.Impl;

import com.google.gson.Gson;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.entity.ResponseResult;
import com.lei.exception.SystemException;
import com.lei.mapper.utils.PathUtils;
import com.lei.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/9/11 18:02
 * @Version : 1.0.0
 */

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //对文件上传的类型进行判断
        //获取原始文件名
        String filename = img.getOriginalFilename();
        //对原始文件名进行判断
        if (!filename.endsWith(".png")&&!filename.endsWith(".jpg")&&!filename.endsWith(".jpeg")){
            throw new SystemException(AppHttpCodeEnum.UPLOAD_TYPE_ERROR);
        }
        String filePath = PathUtils.generateFilePath(filename);

        String upload = ossUpload(img,filePath);
        return ResponseResult.okResult(upload);
    }

    private String accessKey;
    private String secretKey;
    private String bucket;




    public String  ossUpload(MultipartFile img, String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
//        String accessKey = "your access key";
//        String secretKey = "your secret key";
//        String bucket = "fl-blog";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            InputStream fis = img.getInputStream();

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fis,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
//                http://ri15lis3e.hn-bkt.clouddn.com/2022/09/12/47d56f9cdb814cf5af35f20ace06aae5.jpeg
//                return "http://ri15lis3e.hn-bkt.clouddn.com/"+filePath;
                  return "http://rpg37ll21.hn-bkt.clouddn.com/"+filePath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    ex2.printStackTrace();
                    //ignore
                }
            }
        } catch (Exception e) {
            //ignore
            e.printStackTrace();
        }
        return null;
    }

}
