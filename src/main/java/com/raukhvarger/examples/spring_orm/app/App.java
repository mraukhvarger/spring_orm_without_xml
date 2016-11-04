package com.raukhvarger.examples.spring_orm.app;

import com.raukhvarger.examples.spring_orm.db.DAO;
import com.raukhvarger.examples.spring_orm.db.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by jerde on 03.11.2016.
 */

@Component
public class App {

    @Autowired
    DAO dao;

    public static void main(String args[]) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigContext.class);

        App app = context.getBean(App.class);

        app.dao.persist(new Person("Ivan Petrov", 25));
        app.dao.persist(new Person("Sidor Ivanov", 31));

        pr(app.dao.findAll());
    }

    public static void pr(Object str) {
        System.out.println(str);
    }

}
