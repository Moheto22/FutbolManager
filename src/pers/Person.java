package pers;

import teams.Team;

public abstract class Person {
    protected final String name;
    protected final int age;
    protected final String nation;
    protected final String surname;
    protected Team club;
    protected double motivation;
    protected int salary;
    protected Person(String name, int age, String nation, String surname) {
        this.name = name;
        this.age = age;
        this.nation = nation;
        this.surname = surname;
    }
}
