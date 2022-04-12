package com.wen.mapper;

import com.wen.pojo.FileStore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mr.文
 */
@Mapper
@Repository
@CacheConfig(cacheNames = "store")
public interface FileStoreMapper {
    List<FileStore> queryFileStores();

    FileStore queryStoreById(int fileStoreId);

    int addFileStore(FileStore fileStore);

    @Cacheable(key = "'uid:'+#p0")
    FileStore queryFileStoreByUserId(int userId);
}
