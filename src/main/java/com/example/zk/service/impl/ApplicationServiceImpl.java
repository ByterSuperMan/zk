package com.example.zk.service.impl;

import com.example.zk.service.ApplicationService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author lizhihao
 * @date 2020/12/31 14:57
 **/
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private ConfigurableApplicationContext context;

    @Override
    public void close() {
        context.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.context = (ConfigurableApplicationContext) applicationContext;
        }
    }
}
