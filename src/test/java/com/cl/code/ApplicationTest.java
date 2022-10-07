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
import java.util.List;

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
    private HistoryService historyService;

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
        List<String> users = new ArrayList<>();
        users.add("张三");
        users.add("李四");

        deploy();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("agt_sp");

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        taskService.complete(task.getId(), Collections.singletonMap("assigneeList", users));


        task = taskService.createTaskQuery().taskAssignee("李四").singleResult();
        taskService.complete(task.getId());

//        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();

//
//        task = taskService.createTaskQuery().taskAssignee("lxh").singleResult();
//        taskService.complete(task.getId());

    }

    @Test
    public void task() {

    }

    @Test
    public void close() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.close();
    }


}
