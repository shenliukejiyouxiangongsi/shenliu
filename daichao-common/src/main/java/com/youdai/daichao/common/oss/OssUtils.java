package com.youdai.daichao.common.oss;

import com.aliyun.oss.*;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static com.aliyun.oss.common.utils.CodingUtils.assertParameterNotNull;
import static com.aliyun.oss.common.utils.IOUtils.checkFile;
import static com.aliyun.oss.common.utils.LogUtils.logException;
import static com.aliyun.oss.internal.OSSConstants.DEFAULT_CHARSET_NAME;
import static com.aliyun.oss.internal.OSSConstants.DEFAULT_OSS_ENDPOINT;
import static com.aliyun.oss.internal.OSSUtils.OSS_RESOURCE_MANAGER;
import static com.aliyun.oss.internal.OSSUtils.ensureBucketNameValid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.ServiceSignature;
import com.aliyun.oss.common.comm.*;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.internal.*;
import com.aliyun.oss.model.*;
import com.aliyun.oss.model.SetBucketCORSRequest.CORSRule;

/**
 * @Description 上传图片
 * @Author zhuys
 * @Create 2019/3/15
 */
@Configuration
public class OssUtils {


    @Value("${spring.profiles.active}")
    private String PROFILES_PROPERTY;

    @Autowired
    private OssConfig config;

    private OSSClient client;

    private volatile String filePath;

    public OssUtils(OssConfig config) {
        this.config = config;
        this.client = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
    }

    public String putFile(String key, MultipartFile file) throws IOException {
        return this.putFile(key, file.getInputStream());
    }

    public String putFile(String key, File file) throws IOException {
        return this.putFile(key, new FileInputStream(file));
    }

    public String putFile(String key, InputStream in) throws IOException {
        return this.putFile(key, in, PROFILES_PROPERTY);
    }

    public String putFile(String key, InputStream in, String folder) throws IOException {
        return this.putFile(key, in, config.getBucketName(), folder);
    }

    public String putFile(String key, MultipartFile file, String folder) throws IOException {
        return this.putFile(key, file.getInputStream(), PROFILES_PROPERTY + "/" +folder);
    }

    public String putFile(String key, InputStream in, String bucketName, String folder) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength((long)in.available());
        this.client.putObject(bucketName, folder + "/" + key, in, meta);
        return this.getFilePath() + "/" + key;
    }

    public String getFilePath() {
        if(StringUtils.isEmpty(this.filePath)) {
            synchronized(this) {
                if(StringUtils.isEmpty(this.filePath)) {
                    String endpoint = this.config.getEndpoint();
                    endpoint = endpoint.endsWith("/")?endpoint:endpoint + "/";
                    String protocol = endpoint.startsWith("https://")?"https://":"http://";
                    this.filePath = "https://" + this.config.getBucketName() + "." + endpoint.replace(protocol, "") + PROFILES_PROPERTY + "/";
                }
            }
        }
        return this.filePath;
    }

    public  void shutDown(){
        this.client.shutdown();
    }

    public static void main(String[] args) {
        try {
            OSSClient ossClient = new OSSClient("oss-cn-hangzhou.aliyuncs.com", "LTAIbTprB9CrWD6r", "NVJo6kC8iv6y9RwicVY7VCYCL4NQkO");
            // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
            String content = "Hello OSS";
            ossClient.putObject("shenliu-daichao", "test.txt", new ByteArrayInputStream(content.getBytes()));
            ossClient.shutdown();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

}
