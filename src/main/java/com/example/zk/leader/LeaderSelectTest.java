package com.example.zk.leader;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhihao
 * @date 2020/12/30 16:24
 **/
public class LeaderSelectTest {

    static int num = 2;

    private static List<LeaderSelector> leaderSelectors = new ArrayList<>(num);

    private static List<CuratorFramework> clients = new ArrayList<>(num);

    private static List<Thread> ts = new ArrayList<>(num);

    private static Random r = new Random();

    /**
     * 创建连接
     */
    private static void createClients() {
        RetryPolicy retryPolicy;
        for (int i = 0; i < num; i++) {
            retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client1 = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
            clients.add(client1);
        }
    }

    /**
     * 启动连接
     */
    private static void start() {
        clients.forEach(CuratorFramework::start);
    }

    /**
     * 关闭连接
     */
    private static void close() {
        leaderSelectors.forEach(LeaderSelector::close);
        clients.forEach(CuratorFramework::close);
    }

    /**
     * 设置选举配置
     */
    public static void leader() {
        clients.forEach(e -> {
            Thread t = new Thread(() -> {
                LeaderSelector ls = new LeaderSelector(e, "/myzk/leader", new LeaderSelectorListener() {

                    @Override
                    public void takeLeadership(CuratorFramework client) throws Exception {
                        System.out.println(Thread.currentThread().getName() + "被选举为leader...");
                        TimeUnit.MILLISECONDS.sleep(r.nextInt(3000));
                    }

                    @Override
                    public void stateChanged(CuratorFramework client, ConnectionState newState) {
                        System.err.println("stateChanged...");
                    }
                });
                ls.autoRequeue();
                ls.start();
                leaderSelectors.add(ls);
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("结束");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });
            ts.add(t);
        });
    }

    public static void main(String[] args) throws Exception {
        //创建连接
        createClients();
        //启动连接
        start();
        //设置选举配置
        leader();
        //开始选举
        ts.forEach(Thread::start);
        //等待选举
        TimeUnit.SECONDS.sleep(20);
        //选举结束，关闭所有连接
        close();
    }
}
