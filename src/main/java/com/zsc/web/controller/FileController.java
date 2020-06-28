package com.zsc.web.controller;

import com.zsc.web.config.StorageProperties;
import com.zsc.web.domain.Storage;
import com.zsc.web.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Abouerp
 */
@Controller
@Slf4j
@RequestMapping("/file-operator")
public class FileController {

    private final StorageService storageService;
    private final Path path;

    public FileController(StorageService storageService,
                          StorageProperties storageProperties) {
        this.storageService = storageService;
        this.path = Paths.get(storageProperties.getLocation());
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
        }
        log.info("file storage path = {}", path.toString());
    }

    @PostMapping
    @ResponseBody
    public String upload(MultipartFile file) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            /**
             * MessageDigest此类为应用程序提供消息摘要算法的功能，例如SHA-1或SHA-256,MD5。
             * 消息摘要是安全的单向哈希函数，可接收任意大小的数据并输出固定长度的哈希值。
             */
            MessageDigest md = MessageDigest.getInstance("MD5");
            //获得上传文件的输入流
            InputStream inputStream = file.getInputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                //利用指定的字节执行摘要更新
                md.update(buffer, 0, len);
            }
            inputStream.close();
            //digest():获取摘要字节数组
            byte[] b = md.digest();
            //通过BigInteger类提供的方法进行16进制的转换
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将bigInteger转为string，得到一个32位字符串
        String md5 = bi.toString(16);
        //在数据库中查询这个md5值是否存在，如果存在，则说明这个文件上传过，如果不存在，
        //则将文件的原始名称，文件格式，文件md5值保存到数据库，实现服务器端的资源节约，避免重复文件占用资源
        Storage storage = storageService.findByMd5(md5);
        if (storage == null) {
            try {
                //创建路径的资源文件，保存的文件名称为这个文件的md5值，确保唯一
                //并且注意此时的文件还是为空，相当于linux中touch xxxx  文件此时并没指定格式
                Path p = Paths.get(path.toString()+"/"+md5);
                //将Path资源转化为文件
                File files = p.toFile();
                //将所传文件转换为字节码文件并存入上面的文件files,注意存储的是字节码文件，
                //如果不存储为字节码文件，在window中下载文件的话没有问题，但如果在linux中会出现文件损坏的情况
                file.transferTo(files);
            }catch (IOException e){
                e.printStackTrace();
                log.info("file save error ...");
            }
            storage = new Storage()
                    .setContentType(file.getContentType())
                    .setMd5(md5)
                    .setOriginalFilename(file.getOriginalFilename());
            //将上面的文件对象属性值存储在数据库里面
            storageService.save(storage);
        }
        return md5;
    }

    /**
     * 下载文件
     */
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) throws Exception{
        Storage storage = storageService.findByMd5(id);
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", new String(storage.getOriginalFilename().getBytes("utf-8"),"ISO8859-1"))
        ).body(new FileUrlResource(path.toString()+"/"+id));
    }

    @ResponseBody
    @GetMapping
    public List<Storage> findAll(){
        return storageService.findAll();
    }
}
