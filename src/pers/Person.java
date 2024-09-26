package pers;

import teams.Team;

import java.util.Random;

/**
 * @author Mohamed Boutanghach
 * Clase abstracta que representa a una persona en el contexto de un club deportivo.
 * Esta clase contiene información general sobre la persona, como su nombre, año de nacimiento,
 * nacionalidad, y otros atributos relacionados con su carrera.
 */
public abstract class Person {
    protected final String name; // Nombre de la persona
    protected final int yearOfBirth; // Año de nacimiento de la persona
    protected final String nation; // Nacionalidad de la persona
    protected final String surname; // Apellido de la persona
    protected Team club; // Club al que pertenece la persona
    protected boolean transfer; // Indica si la persona está en transferencia
    protected double motivation; // Nivel de motivación de la persona
    protected int salary; // Salario de la persona
    protected String stylePlaying; // Estilo de juego de la persona
    protected int price; // Precio de la persona en el mercado

    /**
     * Constructor de la clase Person.
     *
     * @param name Nombre de la persona.
     * @param yearOfBirth Año de nacimiento de la persona.
     * @param nation Nacionalidad de la persona.
     * @param surname Apellido de la persona.
     * @param stylePlaying Estilo de juego de la persona.
     */
    protected Person(String name, int yearOfBirth, String nation, String surname, String stylePlaying) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.nation = nation;
        this.surname = surname;
        motivation = 5; // Inicializa la motivación en 5
        this.stylePlaying = stylePlaying;
        this.transfer = true; // Inicializa el estado de transferencia como verdadero
    }

    /**
     * Incrementa el nivel de motivación de la persona durante el entrenamiento.
     *
     * @return 0 como resultado del entrenamiento.
     */
    public int training() {
        this.motivation += 0.2; // Aumenta la motivación en 0.2
        return 0;
    }

    /**
     * Obtiene el precio de la persona.
     *
     * @return El precio de la persona.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Método abstracto que debe ser implementado por las clases que extiendan Person
     * para generar el precio de la persona.
     */
    public abstract void generatePrice();

    /**
     * Obtiene el nombre de la persona.
     *
     * @return El nombre de la persona.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el año de nacimiento de la persona.
     *
     * @return El año de nacimiento de la persona.
     */
    public int getYearOfBirth() {
        return yearOfBirth;
    }

    /**
     * Obtiene la nacionalidad de la persona.
     *
     * @return La nacionalidad de la persona.
     */
    public String getNation() {
        return nation;
    }

    /**
     * Método para aumentar el salario de la persona. Este método debe ser implementado
     * por las subclases.
     *
     * @return 0 como resultado de la operación.
     */
    public double increaseSalary() {
        return 0; // Este método debe ser sobreescrito
    }

    /**
     * Obtiene el apellido de la persona.
     *
     * @return El apellido de la persona.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Obtiene el club al que pertenece la persona.
     *
     * @return El club de la persona.
     */
    public Team getClub() {
        return club;
    }

    /**
     * Obtiene el nivel de motivación de la persona.
     *
     * @return El nivel de motivación.
     */
    public double getMotivation() {
        return motivation;
    }

    /**
     * Obtiene el salario de la persona.
     *
     * @return El salario de la persona.
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Obtiene el estilo de juego de la persona.
     *
     * @return El estilo de juego de la persona.
     */
    public String getStylePlaying() {
        return stylePlaying;
    }

    /**
     * Verifica si la persona está en transferencia.
     *
     * @return true si está en transferencia, false de lo contrario.
     */
    public boolean getTransfer() {
        return transfer;
    }

    /**
     * Establece el estado de transferencia de la persona.
     *
     * @param transfer Estado de transferencia.
     */
    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    /**
     * Establece el club al que pertenece la persona.
     *
     * @param club Club al que se asignará la persona.
     */
    public void setClub(Team club) {
        this.club = club;
    }

    /**
     * Devuelve una representación en forma de cadena de la persona,
     * incluyendo nombre, apellido, nacionalidad, año de nacimiento, motivación,
     * estilo de juego y club si está asignado.
     *
     * @return Cadena que representa a la persona.
     */
    @Override
    public String toString() {
        String result = "Nombre: " + this.name + ", Apellido: " + this.surname + ", Nacionalidad: " + this.nation +
                ", Año de nacimiento: " + this.yearOfBirth + ", Motivación: " + this.motivation +
                ", Estilo: " + stylePlaying;
        if (!(this.club == null)) {
            result += ", Club: " + this.club.getName();
        }
        return result;
    }
}