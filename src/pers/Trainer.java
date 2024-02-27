package pers;

public class Trainer extends Person{

    private boolean natSelect;

    protected Trainer(String name, int yearOfBirth, String nation, String surname) {
        super(name, yearOfBirth, nation, surname);
    }

    @Override
    protected void training() {
        super.training();
    }

    @Override
    public String toString() {
        return super.toString()+" Entrenador Nacional: "+this.natSelect;
    }
}