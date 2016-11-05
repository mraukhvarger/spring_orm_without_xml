package com.raukhvarger.examples.spring_orm.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by jerde on 04.11.2016.
 */

@Entity(name = "Person")
public class Person {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public Integer age;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Person() {}

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
