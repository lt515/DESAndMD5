package com.zsc.web.controller;

import com.zsc.web.config.StorageProperties;
import com.zsc.web.domain.Storage;
import com.zsc.web.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Abouerp
 */
@Controller
@Slf4j
@RequestMapping("/file-operator")
public class FileController {

    private final StorageService storageService;
    private final String path;

    public FileController(StorageService storageService,
                          StorageProperties storageProperties) {
        this.storageService = storageService;
        this.path = storageProperties.getLocation();
    }

    @PostMapping
    @ResponseBody
    public String upload(MultipartFile file) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            InputStream inputStream = file.getInputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            inputStream.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String md5 = bi.toString(16);
        Storage storage = storageService.findFirstByMd5(md5).orElse(null);
        if (storage == null) {
            try {
                File files = new File(path, md5);
                file.transferTo(files);
            }catch (IOException e){
                log.info("file save error ...");
            }
            storage = new Storage()
                    .setContentType(file.getContentType())
                    .setMd5(md5)
                    .setOriginalFilename(file.getOriginalFilename());
            storageService.save(storage);
        }
        return md5;
    }
}