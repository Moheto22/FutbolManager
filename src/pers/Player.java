package pers;

public class Player extends Person{
    private int gols;
    private double quality;
    private static int numberOfPlayers;
    private String position;
    private int heigth;
    private int weight;
    private int dorsal;

    protected Player(String name, int age, String nation, String surname) {
        super(name, age, nation, surname);
        numberOfPlayers++;
    }
    @Override
    protected void training() {
        super.training();
    }


    public int getGols() {
        return gols;
    }

    public double getQuality() {
        return quality;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getPosition() {
        return position;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWeight() {
        return weight;
    }

    public int getDorsal() {
        return dorsal;
    }

    @Override
    public String toString() {
        return super.toString()+", Calidad: "+this.quality+", Goles: "+this.gols+", Posicion: "+this.position+", Altura: "+this.heigth+", Peso: "+this.weight+", Dorsal: "+this.dorsal;
    }
}
