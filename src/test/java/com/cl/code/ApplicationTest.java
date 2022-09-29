package com.cl.code;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;

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

    @Autowired
    private ProcessRuntime processRuntime;

    @Test
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/agt__.bpmn20.xml").deploy();
    }

    @Test
    public void start() {
        // ProcessEngineConfigurationImpl
        // DbSqlSession
        // AbstractEntityManager
        deploy();
        ArrayList<String> userList = new ArrayList<>();
        userList.add("张三");
        userList.add("李四");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("agt_sp", Collections.singletonMap("assigneeList", userList));
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
