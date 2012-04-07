package org.jboss.aerogear.controller.demo;

import org.jboss.aerogear.controller.demo.model.Car;

public class Home {

    public void index() {
        System.out.println("hello from controller");
    }

    public Car anotherPage() {
        return new Car("silver", "delorean");
    }
}
