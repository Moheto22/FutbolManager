package pers;

public class Player extends Person{
    private int gols;
    private double quality;
    private static int numberOfPlayers;
    private String position;


    protected Player(String name, int age, String nation, String surname) {
        super(name, age, nation, surname);
    }
    @Override
    protected void training() {
        super.training();
        numberOfPlayers++;
    }

}
