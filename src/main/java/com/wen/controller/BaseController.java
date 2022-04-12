package com.wen.controller;

import com.wen.servcie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @Autowired
    FileFolderService fileFolderService;
    @Autowired
    FileStoreService fileStoreService;
    @Autowired
    TokenService tokenService;

}
