package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


import java.util.List;

@Configuration
@ComponentScan("com.softserve.itacademy.service")
public class Config {

//    @Bean
//    @Primary
//    public UserService userService() {
//        return new UserServiceImpl();
//    }
//    @Bean
//    @Primary
//    public ToDoService toDoService() {
//        return new ToDoServiceImpl(userService());
//    }
}
