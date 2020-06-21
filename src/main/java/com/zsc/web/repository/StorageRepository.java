package com.zsc.web.repository;

import com.zsc.web.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Abouerp
 */
public interface StorageRepository extends JpaRepository<Storage, Integer> {

    Optional<Storage> findByMd5(String md5);
}
