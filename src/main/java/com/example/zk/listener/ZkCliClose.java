package com.example.zk.listener;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * 关闭zk连接
 *
 * @author lizhihao
 * @date 2020/12/31 14:23
 **/
@Component
public class ZkCliClose implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private CuratorFramework client;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try {
            System.err.println("关闭zk连接");
            client.close();
        } catch (Exception e) {

        }
    }
}
