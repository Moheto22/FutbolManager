package pers;

import java.util.Random;

/**
 * @author Mohamed Boutanghach
 * Clase que representa a un entrenador de fútbol, que hereda de la clase Person.
 * Esta clase contiene información específica sobre el entrenador, como si es
 * seleccionador nacional, los campeonatos ganados y su precio.
 */
public class Trainer extends Person {
    private boolean natSelect; // Indica si el entrenador es seleccionador nacional
    private int winedChamp; // Número de campeonatos ganados por el entrenador

    /**
     * Constructor de la clase Trainer.
     *
     * @param name Nombre del entrenador.
     * @param yearOfBirth Año de nacimiento del entrenador.
     * @param nation Nacionalidad del entrenador.
     * @param surname Apellido del entrenador.
     * @param natSelect Indica si el entrenador es seleccionador nacional.
     * @param stylePlaying Estilo de juego del entrenador.
     */
    public Trainer(String name, int yearOfBirth, String nation, String surname, boolean natSelect, String stylePlaying) {
        super(name, yearOfBirth, nation, surname, stylePlaying);
        this.natSelect = natSelect; // Asigna si es seleccionador nacional
        this.winedChamp = 0; // Inicializa los campeonatos ganados en 0
        generatePrice(); // Genera el precio inicial del entrenador
    }

    /**
     * Genera el precio del entrenador aleatoriamente, entre 2,500,000 y 7,500,000.
     */
    @Override
    public void generatePrice() {
        Random random = new Random();
        this.price = random.nextInt(5000000) + 2500000; // Precio entre 2,500,000 y 7,500,000
    }

    /**
     * Método que simula el entrenamiento realizado por el entrenador,
     * incrementando la motivación del mismo dependiendo de si es seleccionador nacional.
     *
     * @return 1 si es seleccionador nacional, 0 de lo contrario.
     */
    @Override
    public int training() {
        int res;
        if (natSelect) {
            motivation += 0.3; // Aumenta la motivación más si es seleccionador nacional
            res = 1;
        } else {
            motivation += 0.15; // Aumento menor si no es seleccionador nacional
            res = 0;
        }
        return res; // Devuelve el resultado del entrenamiento
    }

    /**
     * Aumenta el salario del entrenador en un 5%.
     *
     * @return El porcentaje de aumento en el salario (5).
     */
    @Override
    public double increaseSalary() {
        this.salary *= 1.05; // Aumenta el salario en un 5%
        return 5; // Devuelve el porcentaje de aumento
    }

    /**
     * Devuelve una representación en forma de cadena del entrenador,
     * incluyendo sus atributos como si es seleccionador nacional,
     * precio y campeonatos ganados.
     *
     * @return Cadena que representa al entrenador.
     */
    @Override
    public String toString() {
        return super.toString() + " Entrenador Nacional: " + this.natSelect +
                ", Precio: " + this.price + ", Campeonatos ganados: " + this.winedChamp;
    }

    /**
     * Aumenta el número de campeonatos ganados por el entrenador en 1.
     * También incrementa el precio y el salario en un 10%.
     */
    public void addWinedChamp() {
        this.winedChamp++; // Incrementa el contador de campeonatos ganados
        this.price *= 1.1; // Aumenta el precio en un 10%
        this.salary *= 1.1; // Aumenta el salario en un 10%
    }
}