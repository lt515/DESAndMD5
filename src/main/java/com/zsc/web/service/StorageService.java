package com.zsc.web.service;

import com.zsc.web.domain.Storage;
import com.zsc.web.repository.StorageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class StorageService {

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Storage findByMd5(String md5){
        return storageRepository.findByMd5(md5).orElse(null);
    }

    public Storage save(Storage storage){
        return storageRepository.save(storage);
    }

}
