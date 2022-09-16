package com.cl.code;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chengliang
 * @date 2022/9/14 15:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;


    @Test
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/cl-shengpi.bpmn20.xml").name("审批流程").deploy();
    }


    @Test
    public void start() {
        //部署
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("cl-shengpi");
        String processInstanceId = processInstance.getProcessInstanceId();
        System.out.println(processInstanceId);
    }

    @Test
    public void task() {
        Task task = taskService.createTaskQuery().processInstanceId("0c7d4dc1-3569-11ed-b03d-00ff49cde35d").singleResult();
        taskService.complete(task.getId());
    }

    @Test
    public void close() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.close();
    }


}
