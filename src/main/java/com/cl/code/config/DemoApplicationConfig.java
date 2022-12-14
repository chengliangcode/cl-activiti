package com.cl.code.config;

import org.activiti.api.process.runtime.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DemoApplicationConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Connector processTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            String contentToProcess = (String) inBoundVariables.get("fileContent");
            // Logic Here to decide if content is approved or not
            if (contentToProcess.contains("activiti")) {
                integrationContext.addOutBoundVariable("approved", true);
            } else {
                integrationContext.addOutBoundVariable("approved", false);
            }
            return integrationContext;
        };
    }

    @Bean
    public Connector tagTextConnector() {
        return integrationContext -> {
            String contentToTag = (String) integrationContext.getInBoundVariables().get("fileContent");
            contentToTag += " :) ";
            integrationContext.addOutBoundVariable("fileContent", contentToTag);
            System.out.println("Final Content: " + contentToTag);
            return integrationContext;
        };
    }

    @Bean
    public Connector discardTextConnector() {
        return integrationContext -> {
            String contentToDiscard = (String) integrationContext.getInBoundVariables().get("fileContent");
            contentToDiscard += " :( ";
            integrationContext.addOutBoundVariable("fileContent", contentToDiscard);
            System.out.println("Final Content: " + contentToDiscard);
            return integrationContext;
        };
    }

    @Bean
    public UserDetailsService myUserDetailsService() {
//        ???????????????????????????
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        ?????????????????????
        String[][] usersGroupAndRoles = {{"jack", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"}, {"rose", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"}, {"tom", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"}, {"jerry", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"}, {"other", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"}, {"system", "password", "ROLE_ACTIVITI_USER"}, {"admin", "password", "ROLE_ACTIVITI_ADMIN"}};

        for (String[] users : usersGroupAndRoles) {
//            ?????????????????????
            List<String> authStrList = Arrays.asList(Arrays.copyOfRange(users, 2, users.length));

            log.info("> Registering new user: {} with the following Authorities[{}]", users[0], authStrList);

            inMemoryUserDetailsManager.createUser(new User(users[0], passwordEncoder().encode(users[1]), authStrList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
        }
        return inMemoryUserDetailsManager;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
