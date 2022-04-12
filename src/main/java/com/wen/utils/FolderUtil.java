package com.wen.utils;

import com.wen.mapper.FileFolderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FolderUtil {
    @Autowired
    FileFolderMapper fileFolderMapper;

    /**
     * 异步任务 处理无效文件夹以及文件
     *
     * @param a
     */
    @Async
    public void delErrorFolAndFile(int a) {
        //fileFolderMapper.
    }

}
