package teams;

import pers.Person;
import pers.Player;
import pers.Trainer;
import utils.Tools;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Mohamed Boutanghach
 * Clase que representa un equipo de fútbol, que incluye información sobre
 * jugadores, entrenador, finanzas y estadísticas del equipo.
 */
public class Team {
    private Leauge leauge;
    private int points; // Puntos acumulados por el equipo
    private int gols; // Goles marcados por el equipo
    private ArrayList<Player> teamPlayers; // Lista de jugadores del equipo
    private Player[] top11; // Mejores 11 jugadores del equipo

    private double chemistry; // Química del equipo
    private Trainer trainer; // Entrenador del equipo
    private final String name; // Nombre del equipo
    private double offensive; // Calificación ofensiva
    private double defensive; // Calificación defensiva
    private double midfield; // Calificación de medio campo
    private double overall; // Calificación general
    private String president; // Presidente del equipo
    private int money; // Dinero disponible del equipo
    private int offer; // Oferta actual

    /**
     * Constructor de la clase Team.
     *
     * @param name Nombre del equipo.
     * @param teamPlayers Lista de jugadores del equipo.
     * @param top11 Mejores 11 jugadores del equipo.
     * @param trainer Entrenador del equipo.
     * @param president Presidente del equipo.
     */
    public Team(String name, ArrayList<Player> teamPlayers, Player[] top11, Trainer trainer, String president) {
        this.name = name;
        this.teamPlayers = teamPlayers;
        this.top11 = top11;
        this.trainer = trainer;
        this.president = president;
        this.gols = 0;
        this.points = 0;
        generateMoney();
    }

    /**
     * Genera una cantidad aleatoria de dinero para el equipo.
     */
    public void generateMoney() {
        Random random = new Random();
        this.money = random.nextInt(210000000) + 90000000;
    }

    /**
     * Obtiene la lista de jugadores del equipo.
     *
     * @return Lista de jugadores.
     */
    public ArrayList<Player> getTeamPlayers() {
        return teamPlayers;
    }

    /**
     * Obtiene los mejores 11 jugadores del equipo.
     *
     * @return Array de los mejores 11 jugadores.
     */
    public Player[] getTop11() {
        return top11;
    }

    /**
     * Actualiza la química del equipo en base a la relación entre el estilo de
     * juego del entrenador y los jugadores.
     */
    public void updateChemestry() {
        double value = 10;
        for (Player player : this.top11) {
            if (player.getStylePlaying().equals(this.trainer.getStylePlaying())) {
                value += 10;
            }
        }
        this.chemistry = value;
    }

    /**
     * Obtiene el nombre del presidente del equipo.
     *
     * @return Nombre del presidente.
     */
    public String getPresident() {
        return president;
    }

    /**
     * Genera una oferta automática basada en el precio del jugador.
     *
     * @param price Precio del jugador.
     */
    public void generateAutoOffer(int price) {
        Random random = new Random();
        this.offer = random.nextInt((price / 5) * 2) + (price / 5) * 4;
    }

    /**
     * Actualiza las calificaciones del equipo en base a los jugadores titulares.
     */
    public void updateRateTeam() {
        double of = 0;
        double mid = 0;
        double def = 0;
        for (Player player : top11) {
            if (Tools.in(player.getPosition(), Tools.getPosOfe()) != -1) {
                of += player.getQuality();
            } else if (Tools.in(player.getPosition(), Tools.getPosMid()) != -1) {
                mid += player.getQuality();
            } else {
                def += player.getQuality();
            }
        }
        this.offensive = Tools.format(of, 2);
        this.midfield = Tools.format(mid, 2);
        this.defensive = Tools.format(def, 2);
        this.overall = Tools.format((of + mid + def) / 11, 2);
    }

    /**
     * Establece la oferta actual para el equipo.
     *
     * @param offer Nueva oferta.
     */
    public void setOffer(int offer) {
        this.offer = offer;
    }

    /**
     * Establece el nombre del presidente del equipo.
     *
     * @param president Nuevo nombre del presidente.
     */
    public void setPresident(String president) {
        this.president = president;
    }

    /**
     * Realiza un pago a un jugador, restando el monto de la oferta del dinero
     * disponible del equipo.
     */
    public void payPlayer() {
        this.money -= this.offer;
    }

    /**
     * Vende un jugador y agrega la cantidad recibida al dinero del equipo.
     *
     * @param money Cantidad de dinero recibida por la venta.
     */
    public void sellPlayer(int money) {
        this.money += money;
    }

    /**
     * Obtiene la cantidad de dinero disponible del equipo.
     *
     * @return Cantidad de dinero.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return Nombre del equipo.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece la liga a la que pertenece el equipo.
     *
     * @param leauge Liga a la que se asignará el equipo.
     */
    public void setLeauge(Leauge leauge) {
        this.leauge = leauge;
    }

    /**
     * Reemplaza un jugador en la alineación titular con otro jugador del equipo.
     */
    public void replacePlayer() {
        Random random = new Random();
        boolean done = false;
        int indexTop11 = Tools.in(null, this.top11);
        if (indexTop11 != -1) {
            while (!done) {
                int indexPlayer = random.nextInt(teamPlayers.size());
                if (Tools.in(teamPlayers.get(indexPlayer), top11) != -1) {
                    top11[indexTop11] = teamPlayers.get(indexPlayer);
                    done = true;
                }
            }
        }
    }

    /**
     * Establece el entrenador del equipo.
     *
     * @param trainer Nuevo entrenador.
     */
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    /**
     * Obtiene la liga a la que pertenece el equipo.
     *
     * @return Liga del equipo.
     */
    public Leauge getLeauge() {
        return leauge;
    }

    /**
     * Obtiene la cantidad de puntos acumulados por el equipo.
     *
     * @return Puntos acumulados.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Obtiene la cantidad de goles marcados por el equipo.
     *
     * @return Goles marcados.
     */
    public int getGols() {
        return gols;
    }

    /**
     * Obtiene la química del equipo.
     *
     * @return Química del equipo.
     */
    public double getChemistry() {
        return chemistry;
    }

    /**
     * Obtiene el entrenador del equipo.
     *
     * @return Entrenador del equipo.
     */
    public Trainer getTrainer() {
        return trainer;
    }

    /**
     * Obtiene la calificación ofensiva del equipo.
     *
     * @return Calificación ofensiva.
     */
    public double getOffensive() {
        return offensive;
    }

    /**
     * Obtiene la calificación defensiva del equipo.
     *
     * @return Calificación defensiva.
     */
    public double getDefensive() {
        return defensive;
    }

    /**
     * Restablece la cantidad de goles marcados por el equipo a cero.
     */
    public void restGols() {
        this.gols = 0;
    }

    /**
     * Restablece la cantidad de puntos acumulados por el equipo a cero.
     */
    public void restPoint() {
        this.points = 0;
    }

    /**
     * Obtiene la calificación de medio campo del equipo.
     *
     * @return Calificación de medio campo.
     */
    public double getMidfield() {
        return midfield;
    }

    /**
     * Agrega 3 puntos al equipo por una victoria.
     */
    public void addPointsVictory() {
        this.points += 3;
    }

    /**
     * Agrega 1 punto al equipo por un empate.
     */
    public void addPointsTie() {
        this.points++;
    }

    /**
     * Agrega una cantidad de goles al equipo.
     *
     * @param gols Cantidad de goles a agregar.
     */
    public void addGoals(int gols) {
        this.gols += gols;
    }

    /**
     * Obtiene la oferta actual.
     *
     * @return Oferta actual.
     */
    public int getOffer() {
        return offer;
    }

    /**
     * Representa la información del equipo en forma de cadena.
     *
     * @return Cadena que representa al equipo.
     */
    @Override
    public String toString() {
        String res="Nombre: "+this.name+"\nPresidente: "+this.president;
        if (this.trainer!=null){
            res+="\nEntrenador: "+this.trainer.toString()+"\nNivel de Calidad: "+Tools.getColoredNumber(this.overall,100)+"\nQuimica: "+Tools.getColoredNumber(this.chemistry,110)+ "\nJugadores:\n"+showPlayers();
        }else {
            res += "\nEntrenador: no existe\nNivel de Calidad: " + Tools.getColoredNumber(this.overall, 100) + "\nQuimica: " + Tools.getColoredNumber(this.chemistry, 110) + "\nJugadores:\n" + showPlayers();
        }
        return res;

    }

    /**
     * Muestra la información de los jugadores del equipo en formato de cadena.
     *
     * @return Una cadena que contiene los nombres, apellidos, posiciones,
     *         calidad y precios de todos los jugadores en el equipo,
     *         enumerados con un índice.
     */
    public String showPlayers() {
        StringBuilder players = new StringBuilder(); // Usamos StringBuilder para construir la cadena de manera eficiente
        for (int i = 0; i < this.teamPlayers.size(); i++) { // Iteramos sobre la lista de jugadores
            // Agregamos la información de cada jugador a la cadena
            players.append("(" + (i + 1) + ")-" + "Nombre: " + this.teamPlayers.get(i).getName() + " " +
                    this.teamPlayers.get(i).getSurname() + ", Posicion: " +
                    this.teamPlayers.get(i).getPosition() + ", Calidad: " +
                    Tools.getColoredNumber(this.teamPlayers.get(i).getQuality(), 100) +
                    ", Precio: " + this.teamPlayers.get(i).getPrice() + "\n");
        }
        return players.toString(); // Devolvemos la cadena construida
    }
}
