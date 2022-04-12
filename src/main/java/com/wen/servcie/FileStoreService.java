package com.wen.servcie;

import com.wen.pojo.FileStore;

public interface FileStoreService {

    boolean initStore(int userId);

    FileStore queryStoreByUserId(int userId);
}
