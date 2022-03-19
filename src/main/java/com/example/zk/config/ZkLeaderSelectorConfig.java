package com.example.zk.config;

import com.example.zk.leader.ZrLeaderSelectorListener;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zk 选举配置
 *
 * @author lizhihao
 * @date 2020/12/31 09:46
 **/
@Configuration
public class ZkLeaderSelectorConfig {

    @Value("${zk.clients:192.168.1.212:2181}")
    private String zkClients;

    @Value("${zk.leader.path:/myzk/leader}")
    private String leaderPath;

    /**
     * zk重试策略
     *
     * @return
     */
    @Bean
    public RetryPolicy zkRetryPolicy() {
        return new ExponentialBackoffRetry(1000, 3);
    }

    /**
     * zk客户端连接
     *
     * @return
     */
    @Bean
    public CuratorFramework zkClient() {
        return CuratorFrameworkFactory.newClient(zkClients, 100, 15000,zkRetryPolicy());
    }

    @Bean
    public ZrLeaderSelectorListener zrLeaderSelectorListener() {
        return new ZrLeaderSelectorListener();
    }

    /**
     * zk master选举器
     *
     * @return
     */
    @Bean
    public LeaderSelector zkLeaderSelector() {
        return new LeaderSelector(zkClient(), leaderPath, zrLeaderSelectorListener());
    }
}
