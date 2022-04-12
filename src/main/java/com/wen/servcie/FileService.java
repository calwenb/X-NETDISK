package com.wen.servcie;

import com.wen.pojo.MyFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {

    boolean uploadFile(MultipartFile file, int userId, String fatherFileFolderId);

    List<MyFile> queryMyFiles(int userId, int parentFolderId, int pageNum);

    List<MyFile> queryFilesByType(int userId, String type, int pageNum);

    boolean deleteByMyFileId(int fileId);

    /**
     * 文件下载业务
     */
    ResponseEntity<InputStreamResource> downloadByMyFileId(int fileId) throws IOException;

    boolean updateFileName(int fileId, String newName);

    /**
     * 生成分享码，保存在redis中
     * {key：fid，value：code}
     *
     * @param fileId 文件id
     * @return 文件分享码
     */
    String shareFile(int fileId);

    /**
     * 通过分享码在redis中找到fid，在通过数据库返回文件信息
     *
     * @param shareCode 分享码
     * @return
     */
    MyFile getShareFile(String shareCode);

    List<String> clearBadFile();
}
