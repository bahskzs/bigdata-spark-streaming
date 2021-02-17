package com.yqy.logserver.web.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * @author bahsk
 * @createTime 2021-02-14 0:31
 * @description 日志服务的服务端，负责接收用户上传的日志，并落地到磁盘
 */

@RestController
public class LogController {

    private static final Logger logger = Logger.getLogger(LogController.class);

    @PostMapping("/upload")
    @ResponseBody
    public void upload(@RequestBody String info) {
        logger.info(info);
    }
}
