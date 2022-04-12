/*
package com.wen;

import com.wen.pojo.MyFile;
import com.wen.pojo.User;
import com.wen.servcie.FileFolderService;
import com.wen.servcie.FileService;
import com.wen.servcie.RedisService;
import com.wen.servcie.UserService;
import com.wen.utils.FileUtil;
import com.wen.utils.LogHandlerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NetdiscApplicationTests {
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void t1() {
        for (User user : userService.queryUsers()) {
            System.out.println(user);
        }
    }


    @Autowired
    FileService fileService;

    @Test
    void t3() {
        System.out.println(fileService);
        List<MyFile> myFiles = fileService.queryMyFiles(40, -1, 1);
        for (MyFile myFile : myFiles) {
            System.out.println(myFile);
        }
    }

    @Autowired
    FileFolderService fileFolderService;

    @Test
    void t4() {
        String storeRootPath = FileUtil.STORE_ROOT_PATH;
        System.out.println(storeRootPath);

    }

    @Autowired
    RedisService redisService;

    @Test
    void t5() throws InterruptedException {
        Thread.sleep(3000);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            RedisService service = (RedisService) new LogHandlerUtil().newProxyInstance(redisService);
            service.redisWarmUp();
        }
        System.out.println(System.currentTimeMillis() - l + "ms");

    }
    @Test
    void t6() throws InterruptedException {
        fileService.shareFile(1267);
    }



}
*/
