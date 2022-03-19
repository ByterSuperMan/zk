package com.example.zk.boot;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author lizhihao
 * @date 2020/12/30 16:17
 **/
@Component
public class InitApp implements ApplicationRunner {
    @Autowired
    private LeaderSelector leaderSelector;
    @Autowired
    private CuratorFramework client;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        client.start();
        leaderSelector.autoRequeue();
        leaderSelector.start();
    }
}
