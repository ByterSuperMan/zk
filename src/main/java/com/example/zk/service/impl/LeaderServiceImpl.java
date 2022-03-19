package com.example.zk.service.impl;

import com.example.zk.leader.ZrLeaderSelectorListener;
import com.example.zk.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lizhihao
 * @date 2020/12/31 14:59
 **/
@Service
public class LeaderServiceImpl implements LeaderService {
    @Autowired
    private ZrLeaderSelectorListener listener;

    @Override
    public void giveUp() {
        listener.giveUp();
    }
}
