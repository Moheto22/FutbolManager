package pers;

public class Trainer extends Person{

    private boolean natSelect;

    protected Trainer(String name, int yearOfBirth, String nation, String surname, boolean natSelect) {
        super(name, yearOfBirth, nation, surname);
        this.natSelect=natSelect;
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