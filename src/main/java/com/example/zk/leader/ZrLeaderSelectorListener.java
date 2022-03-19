package com.example.zk.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;

/**
 * 自定义选举监听器
 *
 * @author lizhihao
 * @date 2020/12/31 09:42
 **/
public class ZrLeaderSelectorListener implements LeaderSelectorListener {

    private boolean hasLeaderShip = false;

    /**
     * 放弃leader身份
     */
    public synchronized void giveUp() {
        if (hasLeaderShip) {
            System.err.println("当前为leader身份，主动放弃leader身份...");
            hasLeaderShip = false;
            notifyAll();
        }
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        //当前JVM成为leader
        synchronized (this) {
            hasLeaderShip = true;
            //业务处理，通知业务线程开始处理业务

            //占有leader身份
            while (hasLeaderShip) {
                System.err.println(Thread.currentThread().getName() + "成为leader...wait...");
                wait();
            }
        }
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        //连接状态发生变化，若为false放弃leader身份
        if (!newState.isConnected() && hasLeaderShip) {
            System.err.println("当前为leader身份，连接不稳定，放弃leader身份...");
            synchronized (this) {
                hasLeaderShip = false;
                notifyAll();
            }
        }
    }
}
