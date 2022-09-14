package com.cl.code;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chengliang
 * @date 2022/9/14 15:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Test
    public void test() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/cl-shengpi.bpmn20.xml").name("审批流程").deploy();

        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());

    }

    @Test
    public void test2() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        RuntimeService runtimeService = processEngine.getRuntimeService();


        //        3、根据流程定义Id启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("cl-shengpi");

        //        输出内容
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());


    }

}
