package com.softserve.itacademy;

import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        ToDoServiceImpl toDoService = annotationConfigContext.getBean(ToDoServiceImpl.class);
        UserServiceImpl userService = annotationConfigContext.getBean((UserServiceImpl.class));
        annotationConfigContext.close();

//        ToDoService toDoService;
//        toDoService = annotationConfigContext.getBean(ToDoService.class);
//        annotationConfigContext.close();
    }

}
