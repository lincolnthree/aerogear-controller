package org.jboss.as.quickstarts.poh5helloworld;

import org.jboss.as.quickstarts.poh5helloworld.model.Car;

public class Home {

    public void index() {
        System.out.println("hello from controller");
    }

    public Car anotherPage() {
        return new Car("silver", "delorean");
    }
}
