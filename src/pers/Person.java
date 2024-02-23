package pers;

import teams.Team;

public abstract class Person {
    protected final String name;
    protected final int yearOfBirth;
    protected final String nation;
    protected final String surname;
    protected Team club;
    protected double motivation;
    protected int salary;
    private String stylePlaying;
    protected Person(String name, int yearOfBirth, String nation, String surname) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.nation = nation;
        this.surname = surname;

    }
    protected void training() {
        this.motivation += 0.2;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getNation() {
        return nation;
    }

    public String getSurname() {
        return surname;
    }
    public Team getClub() {
        return club;
    }
    public double getMotivation() {
        return motivation;
    }

    public int getSalary() {
        return salary;
    }

    public String getStylePlaying() {
        return stylePlaying;
    }

    public void setStylePlaying(String stylePlaying) {
        this.stylePlaying = stylePlaying;
    }
}
