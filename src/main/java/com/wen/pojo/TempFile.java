package com.wen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Mr.文
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempFile {

    /**
     * 文件ID
     */
    private int tempFileId;
    /**
     * 文件名
     */
    private String tempFileName;
    /**
     * 文件存储路径
     */
    private String tempFilePath;
    /**
     * 用户ID
     */
    private int userID;
    /**
     * 下载次数
     */
    private int downloadCount;
    /**
     * 创建时间
     */
    private Date updateTime;
    /**
     * 文件大小
     */
    private int size;
    /**
     * 文件类型
     */
    private int type;
}
