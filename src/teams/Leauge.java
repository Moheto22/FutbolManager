package teams;

import java.util.*;

/**
 * @author Mohamed Boutanghach
 * Clase que representa una liga de fútbol, incluyendo equipos, jornadas y clasificación.
 */
public class Leauge {
    private ArrayList<Team> teams; // Lista de equipos en la liga
    private String name; // Nombre de la liga
    private ArrayList<ArrayList<Integer>> journeysLeauge; // Jornadas de la liga
    private int journeyNow; // Jornada actual

    /**
     * Constructor de la clase Leauge.
     *
     * @param teams Lista de equipos que forman parte de la liga.
     * @param name Nombre de la liga.
     */
    public Leauge(ArrayList<Team> teams, String name) {
        this.teams = teams;
        this.name = name;
        journeysLeauge = generateJourneys(this.teams);
        this.journeyNow = 0;
    }

    /**
     * Genera las jornadas de la liga asegurando que no se repitan partidos entre los mismos equipos.
     *
     * @param teams Lista de equipos en la liga.
     * @return Un arreglo de arreglos que representa las jornadas de la liga.
     */
    private ArrayList<ArrayList<Integer>> generateJourneys(ArrayList<Team> teams) {
        ArrayList<ArrayList<Integer>> globalJourney = new ArrayList<>();
        Random random = new Random();
        ArrayList<Integer> used = new ArrayList<>();
        boolean valide = false;
        int trys = 0;
        boolean finish = false;

        while (!finish) {
            for (int i = 0; i < this.teams.size(); ++i) {
                ArrayList<Integer> journey = this.fillArray(teams.size() - 1);

                for (int j = 0; j < this.teams.size() - 1; ++j) {
                    used.clear();
                    int otherTeam = this.searchConection(globalJourney, j, i);
                    if (otherTeam != -1) {
                        journey.set(j, otherTeam);
                    } else {
                        this.bannedNumbers(globalJourney, j, i, used);
                        int match;
                        do {
                            valide = false;
                            match = random.nextInt(teams.size());
                            if (!used.contains(match)) {
                                used.add(match);
                                if (!journey.contains(match) && match != i) {
                                    if (i == 0) {
                                        valide = true;
                                    } else if (this.checkJourney(i, j, globalJourney, match)) {
                                        valide = true;
                                    } else {
                                        valide = false;
                                    }
                                }
                            }
                        } while (!valide && used.size() < teams.size() - 1);

                        if (valide) {
                            journey.set(j, match);
                        }

                        if (!valide) {
                            j = this.teams.size();
                            --i;
                            ++trys;
                            if (trys == 50) {
                                i = this.teams.size();
                                globalJourney.clear();
                            }
                        }
                    }
                }

                if (valide) {
                    globalJourney.add(journey);
                    if (globalJourney.size() == this.teams.size()) {
                        finish = true;
                    }
                }
            }
        }

        return globalJourney;
    }

    /**
     * Agrega números prohibidos a una lista de utilizados en la jornada actual.
     *
     * @param globalJourney Lista de jornadas anteriores.
     * @param j Índice del equipo en la jornada actual.
     * @param i Índice de la jornada actual.
     * @param used Lista donde se almacenan los números prohibidos.
     */
    private void bannedNumbers(ArrayList<ArrayList<Integer>> globalJourney, int j, int i, ArrayList<Integer> used) {
        for (int k = 0; k < i; k++) {
            if (globalJourney.get(k).get(j) != i) {
                used.add(k);
            }
        }
    }

    /**
     * Busca si ya existe una conexión (partido) entre dos equipos en jornadas anteriores.
     *
     * @param globalJourney Lista de jornadas anteriores.
     * @param j Índice del equipo en la jornada actual.
     * @param i Índice de la jornada actual.
     * @return El índice del equipo conectado, o -1 si no existe conexión.
     */
    private int searchConection(ArrayList<ArrayList<Integer>> globalJourney, int j, int i) {
        int index = 0;
        boolean found = false;
        if (i != 0) {
            while (index != i && !found) {
                if (globalJourney.get(index).get(j) == i) {
                    found = true;
                } else {
                    index++;
                }
            }
            if (!found) {
                index = -1;
            }
        } else {
            index = -1;
        }
        return index;
    }

    /**
     * Rellena un arreglo con un tamaño específico con valores -1.
     *
     * @param size Tamaño del arreglo a llenar.
     * @return Un arreglo de enteros llenado con -1.
     */
    private ArrayList<Integer> fillArray(int size) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            array.add(-1);
        }
        return array;
    }

    /**
     * Verifica si un partido ya ha sido jugado en las jornadas anteriores.
     *
     * @param i Índice de la jornada actual.
     * @param j Índice del equipo en la jornada actual.
     * @param globalJourney Lista de jornadas anteriores.
     * @param match Índice del equipo con el que se juega el partido.
     * @return true si el partido es válido (no ha sido jugado), false de lo contrario.
     */
    private boolean checkJourney(int i, int j, ArrayList<ArrayList<Integer>> globalJourney, int match) {
        boolean valide = true;
        int index = 0;
        while (index != i && valide) {
            if (globalJourney.get(index).get(j) == match) {
                valide = false;
            } else {
                index++;
            }
        }
        return valide;
    }

    /**
     * Obtiene las jornadas de la liga.
     *
     * @return Un arreglo de arreglos que representa las jornadas de la liga.
     */
    public ArrayList<ArrayList<Integer>> getJourneysLeauge() {
        return journeysLeauge;
    }

    /**
     * Obtiene la jornada actual de la liga.
     *
     * @return La jornada actual.
     */
    public int getJourneyNow() {
        return journeyNow;
    }

    /**
     * Incrementa la jornada actual en uno.
     */
    public void journeyDone() {
        this.journeyNow++;
    }

    /**
     * Actualiza la clasificación de los equipos en la liga, ordenándolos por puntos y goles.
     */
    public void upDateClasification() {
        Collections.sort(teams, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                int puntos = Integer.compare(o2.getPoints(), o1.getPoints());
                if (puntos != 0) {
                    return puntos;
                }
                return Integer.compare(o2.getGols(), o1.getGols());
            }
        });
    }

    /**
     * Obtiene la lista de equipos en la liga.
     *
     * @return La lista de equipos.
     */
    public ArrayList<Team> getTeams() {
        return teams;
    }

    /**
     * Obtiene el nombre de la liga.
     *
     * @return El nombre de la liga.
     */
    public String getName() {
        return name;
    }
}