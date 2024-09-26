package pers;

import teams.Team;
import utils.Tools;

import java.util.Random;

/**
 * @author Mohamed Boutanghach
 * Clase que representa a un jugador de fútbol, que hereda de la clase Person
 * e implementa la interfaz Transfer.
 * Esta clase contiene información específica sobre el jugador, como su calidad,
 * posición, goles y detalles relacionados con su transferencia.
 */
public class Player extends Person implements Transfer {
    private int gols; // Número de goles marcados por el jugador
    private final int id; // Identificador único del jugador
    private double quality; // Calidad del jugador
    private static int numberOfPlayers = 0; // Contador estático de jugadores
    private String position; // Posición del jugador en el campo
    private final double heigth; // Altura del jugador
    private final int weight; // Peso del jugador
    private int number; // Número de dorsal del jugador

    /**
     * Constructor de la clase Player.
     *
     * @param name Nombre del jugador.
     * @param yearOfBirth Año de nacimiento del jugador.
     * @param nation Nacionalidad del jugador.
     * @param surname Apellido del jugador.
     * @param heigth Altura del jugador.
     * @param weight Peso del jugador.
     * @param quality Calidad inicial del jugador.
     * @param stylePlaying Estilo de juego del jugador.
     * @param position Posición en el campo del jugador.
     */
    public Player(String name, int yearOfBirth, String nation, String surname, double heigth, int weight, double quality, String stylePlaying, String position) {
        super(name, yearOfBirth, nation, surname, stylePlaying);
        this.heigth = heigth;
        this.weight = weight;
        this.quality = quality;
        this.id = numberOfPlayers; // Asigna un ID único
        numberOfPlayers++; // Incrementa el contador de jugadores
        this.gols = 0; // Inicializa los goles en 0
        this.position = position; // Asigna la posición del jugador
        generatePrice(); // Genera el precio inicial del jugador
        this.transfer = true; // Inicializa el estado de transferencia como verdadero
    }

    /**
     * Método que simula el entrenamiento del jugador, incrementando su calidad
     * en función de un valor aleatorio.
     *
     * @return La suma del resultado del entrenamiento en la clase base y el resultado del entrenamiento.
     */
    @Override
    public int training() {
        Random random = new Random();
        int result;
        int prob = random.nextInt(100);
        if (prob <= 70) {
            this.quality += 0.1; // Aumento leve en la calidad
            result = 1;
        } else if (prob <= 90) {
            this.quality += 0.2; // Aumento moderado en la calidad
            result = 2;
        } else {
            this.quality += 0.3; // Aumento significativo en la calidad
            result = 3;
        }
        return super.training() + result; // Devuelve el resultado combinado
    }

    /**
     * Método que intenta cambiar la posición del jugador de forma aleatoria.
     *
     * @return true si el cambio de posición fue exitoso, false de lo contrario.
     */
    public boolean changePosition() {
        Random random = new Random();
        int prob;
        boolean done = false;
        prob = random.nextInt(100);
        if (prob >= 95) { // 5% de probabilidad de cambiar de posición
            this.quality++; // Aumenta la calidad por cambiar de posición
            done = true;
            // Cambia a una nueva posición de ataque, medio o defensa
            if (Tools.in(this.position, Tools.getPosOfe()) != -1) {
                this.position = Tools.getPosOfe()[Tools.choseDif(0, Tools.getPosOfe().length, Tools.in(this.position, Tools.getPosOfe()))];
            } else if (Tools.in(this.position, Tools.getPosMid()) != -1) {
                this.position = Tools.getPosMid()[Tools.choseDif(0, Tools.getPosMid().length, Tools.in(this.position, Tools.getPosMid()))];
            } else if (Tools.in(this.position, Tools.getPosDef()) != -1) {
                this.position = Tools.getPosDef()[Tools.choseDif(0, Tools.getPosDef().length, Tools.in(this.position, Tools.getPosDef()))];
            }
        }
        return done; // Retorna el estado del cambio
    }

    /**
     * Genera el precio del jugador en función de su calidad.
     */
    @Override
    public void generatePrice() {
        if (this.price < 60) {
            this.price = (int) (this.quality * 250000);
        } else if (this.quality < 70) {
            this.price = (int) (this.quality * 347000);
        } else if (this.quality < 80) {
            this.price = (int) (this.quality * 1000000);
        } else if (this.quality < 90) {
            this.price = (int) (this.quality * 1500000);
        } else {
            this.price = (int) (this.quality * 1800000);
        }
    }

    /**
     * Aumenta el salario del jugador en función de su calidad.
     *
     * @return El porcentaje de aumento en el salario.
     */
    @Override
    public double increaseSalary() {
        double perc = 0.002; // Porcentaje base de aumento
        perc = Tools.format(perc *= this.quality, 2); // Aumento basado en la calidad
        this.salary *= perc++; // Actualiza el salario del jugador
        return perc * 100; // Devuelve el porcentaje como valor
    }

    /**
     * Aumenta el número de goles del jugador en 1.
     */
    public void addGoal() {
        this.gols++; // Incrementa el contador de goles
    }

    /**
     * Obtiene la calidad del jugador.
     *
     * @return La calidad del jugador.
     */
    public double getQuality() {
        return quality;
    }

    /**
     * Obtiene la posición del jugador en el campo.
     *
     * @return La posición del jugador.
     */
    public String getPosition() {
        return position;
    }

    /**
     * Obtiene el número de dorsal del jugador.
     *
     * @return El número de dorsal.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Establece el número de dorsal del jugador.
     *
     * @param number El número de dorsal a asignar.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Devuelve una representación en forma de cadena del jugador,
     * incluyendo sus atributos como calidad, goles, posición, altura,
     * peso y precio.
     *
     * @return Cadena que representa al jugador.
     */
    @Override
    public String toString() {
        String str = super.toString() + ", Calidad: " + Tools.getColoredNumber(this.quality, 100) +
                ", Goles: " + this.gols + ", Posición: " + this.position +
                ", Altura: " + this.heigth + ", Peso: " + this.weight + ", Precio: " + this.price;
        if (!(this.club == null)) {
            str += ", Dorsal: " + this.number; // Añade el número de dorsal si el club no es nulo
        }
        return str;
    }

    /**
     * Verifica si el jugador puede ser transferido según una oferta recibida.
     *
     * @param offer La oferta de transferencia.
     * @return true si el jugador puede ser transferido, false de lo contrario.
     */
    @Override
    public boolean isTransfer(int offer) {
        Random random = new Random();
        int actualyPrice = this.price - random.nextInt(this.price / 8) + random.nextInt(this.price / 8);
        boolean trans = false;
        if (this.transfer && offer >= actualyPrice) {
            trans = true; // La transferencia es válida si la oferta es suficiente
        }
        return trans;
    }

    /**
     * Transfiere al jugador a un nuevo club.
     *
     * @param t El nuevo equipo al que se transfiere el jugador.
     */
    @Override
    public void transferTo(Team t) {
        this.club.getTeamPlayers().remove(this); // Elimina al jugador del club actual
        Tools.remove(this, this.club.getTop11()); // Elimina al jugador de la alineación titular
        t.getTeamPlayers().add(this); // Agrega al jugador al nuevo equipo
    }

    /**
     * Obtiene el ID del jugador.
     *
     * @return El ID del jugador.
     */
    public int getId() {
        return id;
    }
}
