package com.example.zk.controller;

import com.example.zk.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器控制器
 * @author lizhihao
 * @date 2020/12/31 14:55
 **/
@RequestMapping("/api/app")
@RestController
public class ApplicationController {
    @Autowired
    private ApplicationService appService;

    /**
     * 放弃leader身份
     * @return
     */
    @GetMapping("/v1/close")
    public ResponseEntity<?> close() {
        appService.close();
        return ResponseEntity.ok().build();
    }
}
