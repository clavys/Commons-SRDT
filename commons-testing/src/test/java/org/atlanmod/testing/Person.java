package org.atlanmod.testing;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {

    private final String name;
    private final int age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
        this.name = "nomParDefaut";
        this.age = 20;
    }

    public Person(String[] st,int ib) {
        this.name = "boubis";
        this.age = ib;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
