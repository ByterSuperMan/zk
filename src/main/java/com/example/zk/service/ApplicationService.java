package com.example.zk.service;

import org.springframework.context.ApplicationContextAware;

/**
 * 容器操作
 * @author lizhihao
 * @date 2020/12/31
 */
public interface ApplicationService extends ApplicationContextAware {
    /**
     * 关闭容器
     */
    void close();
}
