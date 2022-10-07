package com.cl.code;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author chengliang
 * @date 2022/10/6 0:21
 */
@Component
public class A implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.out.println(applicationStartedEvent.getApplicationContext());
    }

}
