package com.example.zk.service;

/**
 * leader
 * @author lizhihao
 * @date 2020/12/31
 */
public interface LeaderService {

    /**
     * 放弃leader身份
     */
    void giveUp();
}
