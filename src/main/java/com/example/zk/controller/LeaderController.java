package com.example.zk.controller;

import com.example.zk.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizhihao
 * @date 2020/12/31 14:51
 **/
@RequestMapping("/api/leader")
@RestController
public class LeaderController {
    @Autowired
    private LeaderService leaderService;

    /**
     * 放弃leader身份
     * @return
     */
    @GetMapping("/v1/give_up")
    public ResponseEntity<?> giveUp() {
        leaderService.giveUp();
        return ResponseEntity.ok().build();
    }
}
