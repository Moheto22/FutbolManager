package pers;

import teams.Team;

/**
 * @author Mohamed Boutanghach
 * Interfaz que representa las operaciones de transferencia de un jugador o entrenador.
 * Esta interfaz define los métodos necesarios para manejar las transferencias
 * de personas en un contexto deportivo.
 * (Es una interfaz implementada debido a los requerimientos academicos del proyecto,
 * normalmente iria implementado en la clase Player porque es la unica que la precisa)
 */
public interface Transfer {

    /**
     * Método que verifica si una oferta es suficiente para realizar una transferencia.
     *
     * @param offer La cantidad de dinero ofrecida para la transferencia.
     * @return true si la oferta es suficiente para realizar la transferencia,
     *         false de lo contrario.
     */
    boolean isTransfer(int offer);

    /**
     * Método que realiza la transferencia del jugador o entrenador a un nuevo equipo.
     *
     * @param t El equipo al que se va a transferir la persona.
     */
    void transferTo(Team t);
}
