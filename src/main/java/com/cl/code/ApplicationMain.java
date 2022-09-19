package com.cl.code;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author chengliang
 * @date 2022/9/14 15:56
 */
@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ApplicationMain.class, args);
        TaskService bean = run.getBean(TaskService.class);
        ConfigurableListableBeanFactory beanFactory = run.getBeanFactory();
        String[] beanNamesForType = beanFactory.getBeanNamesForType(TaskService.class);
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        System.out.println(bean);
    }


}

