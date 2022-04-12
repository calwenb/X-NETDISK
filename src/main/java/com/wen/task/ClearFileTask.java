package com.wen.task;

import com.wen.servcie.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClearFileTask {
    @Autowired
    FileService fileService;

    //@Scheduled(cron = "0 0 0 */1 * ?")
    //@Scheduled(cron = "0/15 * * * * ? ")
    public void clearBadFile() {
        List<String> rs = fileService.clearBadFile();
        System.out.println("发现文件共:" + rs.size());
        System.out.println(rs);
    }
}
