package com.zsc.web.service;

import com.zsc.web.repository.StorageRepository;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class StorageService {

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }


}
