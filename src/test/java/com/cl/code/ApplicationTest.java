package com.cl.code;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
    public void deployment() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/cl-shengpi.bpmn20.xml").name("审批流程").deploy();

        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());

    }

    @Test
    public void startProcess() {
        //部署
        deployment();

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3、根据流程定义Id启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("cl-shengpi");

        //        输出内容
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());
    }

    @Test
    public void task() {

        //部署
        deployment();

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 3、根据流程定义Id启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("cl-shengpi");

        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task);

//        taskService.complete(task.getId());

    }


}
