import pers.Person;
import pers.Player;
import pers.Trainer;
import teams.Leauge;
import teams.Team;
import utils.Tools;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Mohamed Boutanghach
 * Clase principal que gestiona un sistema de liga de fútbol con equipos, jugadores y entrenadores.
 * Permite la creación de jugadores, entrenadores, equipos y ligas. Además, posibilita la gestión de equipos
 * (fichar jugadores, despedir entrenadores, etc.), simular partidos y entrenamientos, y mostrar la clasificación de la liga.
 */
public class Main {
    public static void main(String[] args){
        // Inicialización de listas de nombres, apellidos y países aleatorios.
        String[] listSurnames = listSur();
        String[] listNames = listNam();
        String[] listCountrys = listCoun();
        String[] ramdomNames = listRanName();

        // Inicialización de listas para nombres y clubes usados, y listas para almacenar personas y equipos.
        ArrayList<String> randomUsedNamesPers = new ArrayList<>();
        ArrayList<Integer> randomUsedNamesClub = new ArrayList<>();
        ArrayList<Person> personsList = createList(listNames, listSurnames, randomUsedNamesPers, listCountrys);
        ArrayList<Team> teamsList = createListClubs(personsList, randomUsedNamesClub, ramdomNames, listNames, listSurnames, randomUsedNamesPers);

        // Variables de control.
        int option;
        boolean exit = false;
        String name;
        Leauge leauge = null;
        int index;
        String surname;

        // Bucle principal del menú
        while (!exit) {
            option = menu(); // Muestra el menú principal y obtiene la opción del usuario.
            switch (option) {
                case 0:
                    // Caso 0: Salir del programa.
                    exit = true;
                    break;
                case 1:
                    // Caso 1: Mostrar la liga, si existe.
                    if (leauge == null) {
                        leagueIsNull(); // Informa que no existe liga.
                    } else {
                        showLeauge(leauge); // Muestra la liga.
                    }
                    break;
                case 2:
                    // Caso 2: Gestión de equipos.
                    index = Tools.choseOption(showAllTeams(teamsList) + "\n¿Qué equipo quieres gestionar?", 1, teamsList.size()) - 1;
                    while (option != 0) {
                        option = menuTeamManagment(); // Muestra menú de gestión de equipos.
                        switch (option) {
                            case 1:
                                // Eliminar equipo de la liga.
                                leauge = removeTeam(teamsList, leauge, teamsList.get(index), personsList);
                                break;
                            case 2:
                                // Modificar el presidente del equipo.
                                modifyPresident(teamsList.get(index));
                                break;
                            case 3:
                                // Despedir al entrenador del equipo.
                                fireTrainer(personsList, teamsList.get(index));
                                break;
                            case 4:
                                // Fichar un jugador para el equipo.
                                signPlayer(personsList, teamsList.get(index));
                                break;
                            case 5:
                                // Transferir un jugador a otro equipo.
                                transferPlayer(teamsList, teamsList.get(index), index);
                                break;
                        }
                    }
                    break;
                case 3:
                    // Caso 3: Crear un equipo personalizado por el usuario.
                    createTeamUser(personsList, teamsList);
                    break;
                case 4:
                    // Caso 4: Crear un nuevo jugador o entrenador.
                    option = Tools.choseOption("¿Qué quieres crear?\n-1) Jugador\n-2) Entrenador", 1, 2);
                    if (option == 1) {
                        personsList.add(createPlayer(personsList, teamsList)); // Crear un jugador.
                    } else {
                        personsList.add(createTrainer(personsList, teamsList)); // Crear un entrenador.
                    }
                    break;
                case 5:
                    // Caso 5: Buscar un equipo y mostrar su información.
                    int pos = searchClub(teamsList);
                    if (pos != -1) {
                        showClub(teamsList.get(pos));
                    } else {
                        notFound(); // Mensaje de "no encontrado".
                    }
                    break;
                case 6:
                    // Caso 6: Buscar un jugador o entrenador.
                    option = Tools.choseOption("¿Qué quieres buscar?\n-1) Jugador\n-2) Entrenador", 1, 2);
                    name = getName(); // Obtener nombre.
                    surname = getSurname(); // Obtener apellido.
                    if (option == 1) {
                        searchPlayer(name, surname, personsList, teamsList); // Buscar jugador.
                    } else {
                        searchTrainer(name, surname, personsList, teamsList); // Buscar entrenador.
                    }
                    break;
                case 7:
                    // Caso 7: Crear una liga nueva o destruir la actual.
                    if (leauge != null) {
                        if (askCreationLeague(leauge)) {
                            destroyLeauge(leauge); // Destruir la liga actual.
                            leauge = CreateLeauge(teamsList); // Crear una nueva liga.
                        }
                    } else {
                        leauge = CreateLeauge(teamsList); // Crear una liga si no existe.
                    }
                    break;
                case 8:
                    // Caso 8: Simular partidos y gestionar un equipo en la liga.
                    if (leauge != null) {
                        index = Tools.choseOption(showAllTeams(leauge.getTeams()) + "\n¿Con qué equipo quieres jugar la Liga?", 1, leauge.getTeams().size()) - 1;
                        while (option != 0 && leauge.getJourneyNow() < leauge.getTeams().size() - 1) {
                            upDateTransfer(leauge); // Actualizar las transferencias.
                            getPossibleOffer(index, leauge); // Ofertas de jugadores.
                            option = Tools.choseOption(Tools.getYellow() + "Modo liga" + Tools.getReset() + "\n\n\t1) Jugar partido\n\t2) Entrenar equipo\n\t3) Fichar jugador\n\t4) Ver clasificación de la liga\n\t5) Poner a la venta jugador\n\t0) Salir", 0, 5);
                            switch (option) {
                                case 1:
                                    // Jugar un partido.
                                    playGame(leauge);
                                    break;
                                case 2:
                                    // Entrenar al equipo.
                                    trainTeam(leauge.getTeams().get(index));
                                    break;
                                case 3:
                                    // Comprar un jugador.
                                    buyPlayer(leauge, leauge.getTeams().get(index), index);
                                    break;
                                case 4:
                                    // Ver clasificación de la liga.
                                    showLeauge(leauge);
                                    break;
                                case 5:
                                    // Poner un jugador a la venta.
                                    putPlayerTransfer(leauge.getTeams().get(index));
                                    break;
                            }
                        }
                        // Si se ha jugado toda la liga, se muestra el ganador.
                        if (leauge.getJourneyNow() == leauge.getTeams().size() - 1) {
                            leauge.getTeams().get(0).getTrainer().addWinedChamp();
                            leauge.journeyDone();
                        }
                        showTheWinner(leauge);
                    } else {
                        leagueIsNull(); // Mensaje de que no existe liga.
                    }
                    break;
            }
        }
    }


    /**
     * Este método genera una oferta automática por un jugador transferible de un equipo
     * y pregunta si el usuario quiere aceptar la oferta.
     *
     * @param index  Índice del equipo del cual se está ofreciendo un jugador.
     * @param leauge Liga a la que pertenece el equipo.
     */
    private static void getPossibleOffer(int index, Leauge leauge) {
        Random random = new Random();
        int team = Tools.choseDif(0, leauge.getTeams().size() - 1, index); // Selecciona un equipo aleatorio distinto al actual.
        int player = random.nextInt(leauge.getTeams().get(index).getTeamPlayers().size()); // Selecciona un jugador aleatorio.

        // Verifica si el jugador está en la lista de transferibles.
        if (leauge.getTeams().get(index).getTeamPlayers().get(player).getTransfer()) {
            // Genera una oferta automática por el jugador.
            leauge.getTeams().get(team).generateAutoOffer(leauge.getTeams().get(index).getTeamPlayers().get(player).getPrice());

            // Pregunta al usuario si acepta la oferta por el jugador.
            if (Tools.choseOptionYS("\nEl " + leauge.getTeams().get(team).getName() + " está interesado en " +
                    leauge.getTeams().get(index).getTeamPlayers().get(player).getName() + " " +
                    leauge.getTeams().get(index).getTeamPlayers().get(player).getSurname() +
                    " y ofrecen " + leauge.getTeams().get(team).getOffer() + " euros, ¿aceptas?\n (S/N)").equalsIgnoreCase("S")) {

                // Si el usuario acepta, se realiza la transferencia del jugador.
                leauge.getTeams().get(index).sellPlayer(leauge.getTeams().get(team).getOffer());
                leauge.getTeams().get(team).payPlayer();
                leauge.getTeams().get(team).getTeamPlayers().add(leauge.getTeams().get(index).getTeamPlayers().get(player));
                leauge.getTeams().get(index).getTeamPlayers().remove(leauge.getTeams().get(index).getTeamPlayers().get(player));
                Tools.remove(leauge.getTeams().get(index).getTeamPlayers().get(player), leauge.getTeams().get(index).getTop11());
                leauge.getTeams().get(index).replacePlayer();
            }
        }
    }

    /**
     * Este método marca a un jugador de un equipo como transferible.
     *
     * @param team Equipo que está vendiendo al jugador.
     */
    private static void putPlayerTransfer(Team team) {
        int option;
        System.out.println(team.showPlayers()); // Muestra los jugadores del equipo.

        // Selecciona el jugador que el usuario desea poner en venta.
        option = Tools.choseOption("Escoge el jugador que quieras vender", 1, team.getTeamPlayers().size());

        // Marca al jugador como transferible.
        team.getTeamPlayers().get(option).setTransfer(true);
        System.out.println("El jugador es transferible");
    }

    /**
     * Este método actualiza el estado de transferible de los jugadores de los equipos de la liga.
     * Se asigna aleatoriamente si un jugador está disponible para transferencias o no.
     *
     * @param leauge Liga a la que pertenecen los equipos.
     */
    private static void upDateTransfer(Leauge leauge) {
        Random random = new Random();

        // Itera sobre todos los equipos y jugadores de la liga.
        for (int i = 0; i < leauge.getTeams().size(); i++) {
            for (int j = 0; j < leauge.getTeams().get(i).getTeamPlayers().size(); j++) {
                // El 30% de probabilidad de que un jugador esté disponible para transferencia.
                if (random.nextDouble() <= 0.3) {
                    leauge.getTeams().get(i).getTeamPlayers().get(j).setTransfer(true);
                } else {
                    leauge.getTeams().get(i).getTeamPlayers().get(j).setTransfer(false);
                }
            }
        }
    }

    /**
     * Este método crea un equipo personalizado por el usuario, permitiéndole
     * asignar el nombre del equipo, presidente, jugadores y entrenador.
     *
     * @param personsList Lista de personas (jugadores y entrenadores) disponibles.
     * @param teamsList   Lista de equipos a la que se añadirá el nuevo equipo.
     */
    private static void createTeamUser(ArrayList<Person> personsList, ArrayList<Team> teamsList) {
        Scanner scanner = new Scanner(System.in);
        String name;
        String president;
        ArrayList<Player> teamPlayers;
        Player[] top11;
        Trainer trainer;

        // Pide al usuario el nombre del equipo.
        System.out.println("¿Qué nombre va a tener el equipo?");
        name = scanner.nextLine();

        // Verifica que el nombre no esté en uso.
        while (nameIsFound(name, teamsList)) {
            System.out.println("Este nombre ya existe, escoge otro");
            name = scanner.nextLine();
        }

        // Pide el nombre del presidente del equipo.
        System.out.println("¿Quién es el presidente?");
        president = scanner.nextLine();

        // Genera la lista de jugadores del equipo.
        teamPlayers = generateTeamPlayers(personsList);

        // Genera la alineación titular (Top 11).
        top11 = generateTop11(teamPlayers);

        // Selecciona el entrenador del equipo.
        trainer = choseTrainer(personsList);

        // Añade el nuevo equipo a la lista de equipos.
        teamsList.add(new Team(name, teamPlayers, top11, trainer, president));
        teamsList.get(teamsList.size() - 1).updateRateTeam();
        teamsList.get(teamsList.size() - 1).updateChemestry();
    }

    /**
     * Este método permite al usuario seleccionar un entrenador para su equipo.
     *
     * @param personsList Lista de personas disponibles para ser seleccionadas como entrenadores.
     * @return Entrenador seleccionado por el usuario.
     */
    private static Trainer choseTrainer(ArrayList<Person> personsList) {
        Trainer trainer;
        int index;

        // Selecciona un entrenador de la lista de personas.
        index = selectTrainer(personsList);

        // Obtiene el índice real del entrenador en la lista de personas.
        index = realIndexGenerationT(index, personsList);

        // Asigna el entrenador y lo elimina de la lista de personas disponibles.
        trainer = (Trainer) personsList.get(index);
        personsList.remove(index);
        return trainer;
    }

    /**
     * Genera los jugadores del equipo solicitando al usuario que seleccione jugadores de la lista de personas disponibles
     * hasta completar el equipo con una estructura específica (2 porteros, 5 defensas, 5 medios y 5 delanteros).
     * También solicita al usuario que asigne un dorsal único a cada jugador.
     *
     * @param personsList Lista de personas, de donde se seleccionarán los jugadores.
     * @return Un ArrayList de jugadores seleccionados.
     */
    private static ArrayList<Player> generateTeamPlayers(ArrayList<Person> personsList) {
        ArrayList<Player> array = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int golk = 0; // Contador de porteros
        int def = 0; // Contador de defensas
        int mid = 0; // Contador de mediocampistas
        int ofe = 0; // Contador de delanteros
        int index;
        int dorsal;
        boolean valide; // Bandera para verificar si se puede agregar el jugador

        // Se repite hasta que el equipo tenga la estructura correcta de jugadores
        while (golk < 2 || def < 5 || mid < 5 || ofe < 5) {
            showNow(golk, def, mid, ofe); // Muestra el estado actual del equipo
            index = selectPlayer(personsList); // Selecciona un jugador de la lista
            index = realIndexGenerationP(index, personsList);

            // Verifica la posición del jugador y si puede ser agregado al equipo
            if (Tools.in(((Player) personsList.get(index)).getPosition(), Tools.getPosDef()) != -1) {
                if (def < 5) {
                    valide = true;
                    def++;
                } else {
                    valide = false;
                    System.out.println("No puedes agregar más defensas al equipo");
                }
            } else if (Tools.in(((Player) personsList.get(index)).getPosition(), Tools.getPosMid()) != -1) {
                if (mid < 5) {
                    valide = true;
                    mid++;
                } else {
                    valide = false;
                    System.out.println("No puedes agregar más mediocampistas al equipo");
                }
            } else if (Tools.in(((Player) personsList.get(index)).getPosition(), Tools.getPosOfe()) != -1) {
                if (ofe < 5) {
                    valide = true;
                    ofe++;
                } else {
                    valide = false;
                    System.out.println("No puedes agregar más delanteros al equipo");
                }
            } else {
                if (golk < 2) {
                    valide = true;
                    golk++;
                } else {
                    valide = false;
                    System.out.println("No puedes agregar más porteros al equipo");
                }
            }

            // Si es válido agregar el jugador, se le asigna un dorsal único
            if (valide) {
                array.add(((Player) personsList.get(index)));
                personsList.remove(personsList.get(index));
                System.out.println("¿Qué dorsal quieres para el jugador?");
                dorsal = scanner.nextInt();
                while (isNotInArray(array, dorsal)) {
                    System.out.println("Este dorsal ya se encuentra en uso, elige otro");
                    dorsal = scanner.nextInt();
                }
                array.get(array.size() - 1).setNumber(dorsal);
            }
        }
        return array;
    }

    /**
     * Verifica si el dorsal proporcionado ya está en uso dentro del equipo.
     *
     * @param array Lista de jugadores en el equipo.
     * @param dorsal Dorsal que se desea asignar.
     * @return true si el dorsal ya está en uso, false si no lo está.
     */
    private static boolean isNotInArray(ArrayList<Player> array, int dorsal) {
        int i = 0;
        boolean found = false;
        while (i < array.size() && !found) {
            if (array.get(i).getNumber() == dorsal) {
                found = true;
            } else {
                i++;
            }
        }
        return found;
    }

    /**
     * Muestra el estado actual del equipo en términos de cuántos jugadores de cada posición se han añadido.
     *
     * @param golk Número de porteros en el equipo.
     * @param def Número de defensas en el equipo.
     * @param mid Número de mediocampistas en el equipo.
     * @param ofe Número de delanteros en el equipo.
     */
    private static void showNow(int golk, int def, int mid, int ofe) {
        System.out.println("Porteros " + golk + "/2");
        System.out.println("Defensas " + def + "/5");
        System.out.println("Medios " + mid + "/5");
        System.out.println("Delanteros " + ofe + "/5");
    }

    /**
     * Verifica si el nombre proporcionado ya está en uso por algún equipo de la lista.
     *
     * @param name Nombre del equipo a verificar.
     * @param teamsList Lista de equipos existentes.
     * @return true si el nombre ya está en uso, false en caso contrario.
     */
    private static boolean nameIsFound(String name, ArrayList<Team> teamsList) {
        boolean found = false;
        int i = 0;
        while (!found && i < teamsList.size()) {
            if (teamsList.get(i).getName().equals(name)) {
                found = true;
            } else {
                i++;
            }
        }
        return found;
    }

    /**
     * Muestra el equipo ganador de la liga.
     *
     * @param leauge Liga actual de la que se mostrará el equipo ganador.
     */
    private static void showTheWinner(Leauge leauge) {
        System.out.println(Tools.getBlue() + "\nEl ganador de la liga es " + leauge.getTeams().get(0).getName() + Tools.getReset());
    }

    /**
     * Juega una jornada completa de la liga, haciendo que cada equipo juegue su partido.
     * Al final de la jornada, actualiza la clasificación de la liga.
     *
     * @param leauge Liga en la que se juegan los partidos.
     */
    private static void playGame(Leauge leauge) {
        ArrayList<Integer> teamPlay = new ArrayList<>();
        int[] result;
        int i = 0;
        while (teamPlay.size() < leauge.getTeams().size()) {
            if (!teamPlay.contains(i)) {
                result = playMatch(leauge.getTeams().get(i), leauge.getTeams().get((leauge.getJourneysLeauge().get(i).get(leauge.getJourneyNow()))));
                teamPlay.add(i);
                teamPlay.add(leauge.getJourneysLeauge().get(i).get(leauge.getJourneyNow()));
                showResult(result, leauge.getTeams().get(i), leauge.getTeams().get(leauge.getJourneysLeauge().get(i).get(leauge.getJourneyNow())));
            }
            i++;
        }
        leauge.upDateClasification();
        leauge.journeyDone();
    }

    /**
     * Muestra el resultado del partido entre dos equipos.
     *
     * @param result Resultado del partido (goles de cada equipo).
     * @param team1 Primer equipo.
     * @param team2 Segundo equipo.
     */
    private static void showResult(int[] result, Team team1, Team team2) {
        System.out.println("El resultado ha sido el siguiente:\n" + team1.getName() + " " + result[0] + "-" + result[1] + " " + team2.getName());
    }

    /**
     * Juega un partido entre dos equipos y retorna el resultado en términos de goles.
     *
     * @param team1 Primer equipo.
     * @param team2 Segundo equipo.
     * @return Array con los goles anotados por cada equipo.
     */
    private static int[] playMatch(Team team1, Team team2) {
        Random random = new Random();
        int oc1 = (int) ((team1.getMidfield() + team1.getChemistry()) / 16);
        int oc2 = (int) ((team2.getMidfield() + team2.getChemistry()) / 16);
        int gols1 = getGols(team1.getOffensive() + team1.getChemistry(), oc1, team2.getDefensive() + team2.getChemistry());
        int gols2 = getGols(team2.getOffensive() + team2.getChemistry(), oc2, team1.getDefensive() + team1.getChemistry());
        team1.addGoals(gols1);
        assignGolsPlayers(team1, gols1);
        team2.addGoals(gols2);
        assignGolsPlayers(team2, gols2);

        // Asigna puntos en base al resultado
        if (gols1 > gols2) {
            team1.addPointsVictory();
        } else if (gols2 > gols1) {
            team2.addPointsVictory();
        } else if (gols2 == gols1) {
            team1.addPointsTie();
            team2.addPointsTie();
        }
        return new int[]{gols1, gols2};
    }

    /**
     * Asigna los goles a los jugadores del equipo basado en los goles anotados.
     *
     * @param team Equipo al que se le asignarán los goles.
     * @param gols Número de goles anotados.
     */
    private static void assignGolsPlayers(Team team, int gols) {
        Random random = new Random();
        for (int i = 0; i < gols; i++) {
            team.getTop11()[random.nextInt(9) + 1].addGoal();
        }
    }

    /**
     * Método para calcular la cantidad de goles anotados en función de las características ofensivas,
     * defensivas y el número de ocasiones generadas.
     *
     * @param ofe Valor ofensivo del equipo.
     * @param oca Número de ocasiones generadas por el equipo.
     * @param def Valor defensivo del equipo contrario.
     * @return Número total de goles anotados.
     */
    private static int getGols(double ofe, int oca, double def) {
        int gols = 0;
        Random random = new Random();
        double prob = 10 + (ofe - def) * 0.125;
        for (int i = 0; i < oca; i++) {
            if (random.nextDouble() * 100 <= prob) {
                gols++;
            }
        }
        return gols;
    }

    /**
     * Método para fichar un jugador de otro equipo de la liga.
     *
     * @param leauge La liga en la que se realiza la compra.
     * @param team El equipo que está realizando la compra.
     * @param index Índice del equipo en el que está buscando el jugador.
     */
    private static void buyPlayer(Leauge leauge, Team team, int index) {
        int option;
        int player;
        System.out.println("Bienvenido al mercado\t\t\tDinero: " + team.getMoney());
        option = Tools.choseOption(showAllTeams(leauge.getTeams(), index) + "\n¿A qué equipo quieres mirar?", 1, leauge.getTeams().size() - 1);
        option = generateRealIndex(option, index);
        player = Tools.choseOption(leauge.getTeams().get(option).showPlayers() + "\n¿Qué jugador quieres fichar? (Pulsa 0 si quieres salir)", 0, leauge.getTeams().get(option).getTeamPlayers().size()) - 1;
        if (player != -1) {
            team.setOffer(askOffer(team.getMoney()));
            if (leauge.getTeams().get(option).getTeamPlayers().get(player).isTransfer(team.getOffer())) {
                System.out.println(leauge.getTeams().get(option).getName() + " acepta la oferta");
                team.payPlayer();
                leauge.getTeams().get(option).sellPlayer(team.getOffer());
                leauge.getTeams().get(option).getTeamPlayers().get(player).setNumber(getNewNumber(team));
                leauge.getTeams().get(option).getTeamPlayers().get(player).transferTo(team);
            } else {
                System.out.println(leauge.getTeams().get(option).getName() + " rechaza la oferta");
            }
        }
    }

    /**
     * Método que solicita al usuario la oferta de dinero que está dispuesto a pagar por un jugador.
     *
     * @param money Dinero disponible del equipo.
     * @return Cantidad ofrecida por el jugador.
     */
    private static int askOffer(int money) {
        Scanner scanner = new Scanner(System.in);
        int offer;
        System.out.println("¿Cuánto dinero estás dispuesto a pagar por él?");
        offer = scanner.nextInt();
        while (offer > money) {
            System.out.println("No tienes suficiente dinero, vuelve a introducir una oferta más baja");
            offer = scanner.nextInt();
        }
        return offer;
    }

    /**
     * Método para entrenar un equipo completo.
     *
     * @param team El equipo que va a ser entrenado.
     */
    private static void trainTeam(Team team) {
        System.out.println("Vamos a realizar el entrenamiento...");
        int option;
        int result;
        for (int i = 0; i < team.getTeamPlayers().size(); i++) {
            option = Tools.choseOption("¿Qué quieres hacer con " + team.getTeamPlayers().get(i).getName() + " " + team.getTeamPlayers().get(i).getSurname() + "?\n1) Entrenamiento normal\n2) Entrenamiento especial", 1, 2);
            if (option == 1) {
                result = team.getTeamPlayers().get(i).training();
                switch (result) {
                    case 1:
                        System.out.println(team.getTeamPlayers().get(i).getName() + " " + team.getTeamPlayers().get(i).getSurname() + " ha subido " + Tools.getRed() + "+0,1" + Tools.getReset() + " de media");
                        break;
                    case 2:
                        System.out.println(team.getTeamPlayers().get(i).getName() + " " + team.getTeamPlayers().get(i).getSurname() + " ha subido " + Tools.getYellow() + "+0,2" + Tools.getReset() + " de media");
                        break;
                    case 3:
                        System.out.println(team.getTeamPlayers().get(i).getName() + " " + team.getTeamPlayers().get(i).getSurname() + " ha subido " + Tools.getGreen() + "+0,3" + Tools.getReset() + " de media");
                        break;
                }
            } else {
                if (team.getTeamPlayers().get(i).changePosition()) {
                    System.out.println(team.getTeamPlayers().get(i).getName() + " " + team.getTeamPlayers().get(i).getSurname() + " ha subido " + Tools.getBlue() + "+1" + Tools.getReset() + " de media y ha cambiado su posición a " + team.getTeamPlayers().get(i).getPosition());
                }
            }
            team.getTeamPlayers().get(i).generatePrice();
            System.out.println("El jugador ha aumentado su salario un " + team.getTeamPlayers().get(i).increaseSalary() + "%");
        }
        if (team.getTrainer().training() == 1) {
            System.out.println("El entrenador " + team.getTrainer().getName() + " " + team.getTrainer().getSurname() + " ha subido la motivación " + Tools.getBlue() + "+0,3" + Tools.getReset());
        } else {
            System.out.println("El entrenador " + team.getTrainer().getName() + " " + team.getTrainer().getSurname() + " ha subido la motivación " + Tools.getGreen() + "+0,15" + Tools.getReset());
        }
    }

    /**
     * Método para transferir un jugador de un equipo a otro.
     *
     * @param teamsList Lista de equipos.
     * @param team El equipo que recibe el jugador.
     * @param index Índice del equipo actual en la lista.
     */
    private static void transferPlayer(ArrayList<Team> teamsList, Team team, int index) {
        int option;
        int indexPlayer;
        int number;
        option = Tools.choseOption(showAllTeams(teamsList, index) + "\nElige el equipo del cual quieres transferir un jugador", 1, teamsList.size() - 1);
        option = generateRealIndex(option, index);
        indexPlayer = Tools.choseOption(teamsList.get(option).showPlayers() + "\n¿Qué jugador quieres transferir?", 1, teamsList.get(option).getTeamPlayers().size()) - 1;
        number = getNewNumber(team);
        teamsList.get(option).getTeamPlayers().get(indexPlayer).setNumber(number);
        teamsList.get(option).getTeamPlayers().get(indexPlayer).transferTo(team);
        System.out.println("El jugador fue fichado");
        if (Tools.in(null, teamsList.get(option).getTop11()) != -1) {
            teamsList.get(option).replacePlayer();
            teamsList.get(option).updateChemestry();
            teamsList.get(option).updateRateTeam();
        }
    }

    /**
     * Método para generar el índice real de un equipo en la lista,
     * corrigiendo la posición si es necesario.
     *
     * @param option Opción seleccionada por el usuario.
     * @param index Índice del equipo actual en la lista.
     * @return Índice real del equipo.
     */
    private static int generateRealIndex(int option, int index) {
        if (option < index) {
            option--;
        }
        return option;
    }

    /**
     * Método para fichar un jugador o un entrenador.
     *
     * @param personsList Lista de personas disponibles.
     * @param team El equipo que realiza el fichaje.
     */
    private static void signPlayer(ArrayList<Person> personsList, Team team) {
        int number;
        int index;
        int option;
        option = Tools.choseOption("¿Qué quieres fichar?\n1)- Jugador\n2)- Entrenador", 1, 2);
        if (option == 1) {
            index = selectPlayer(personsList);
            index = realIndexGenerationP(index, personsList);
            number = getNewNumber(team);
            personsList.get(index).setTransfer(false);
            ((Player) personsList.get(index)).setNumber(number);
            team.getTeamPlayers().add((Player) personsList.get(index));
            System.out.println("El jugador ha sido fichado");
        } else {
            index = selectTrainer(personsList);
            index = realIndexGenerationT(index, personsList);
            personsList.add(team.getTrainer());
            team.setTrainer((Trainer) personsList.get(index));
            System.out.println("El entrenador ha sido fichado");
        }
        personsList.remove(index);
    }

    /**
     * Método para generar el índice real de un entrenador en la lista de personas.
     *
     * @param index Índice del entrenador seleccionado.
     * @param personsList Lista de personas disponibles.
     * @return Índice real del entrenador en la lista.
     */

    private static int realIndexGenerationT(int index, ArrayList<Person> personsList) {
        int i=0;
        boolean found=false;
        while (!found){
            if (personsList.get(i) instanceof Trainer){
                index--;
                if (index==0){
                    found=true;
                }else {
                    i++;
                }
            }else {
                i++;
            }
        }
        return i;
    }

    /**
     * Selecciona un entrenador de la lista de personas.
     * Pide al usuario que elija un entrenador y valida la opción seleccionada.
     *
     * @param personsList Lista de personas, donde se encuentran entrenadores y jugadores.
     * @return El índice del entrenador seleccionado.
     */
    private static int selectTrainer(ArrayList<Person> personsList) {
        Scanner scanner=new Scanner(System.in);
        int option;
        int numberTrainers;
        numberTrainers=showAllTrainers(personsList);
        System.out.println("Escoje un entrenador entre todos ellos");
        option=scanner.nextInt();
        while (option>numberTrainers || option<1){
            System.out.println("Esta opcion no es valida");
            option=scanner.nextInt();
        }
        return option;
    }

    /**
     * Muestra todos los entrenadores presentes en la lista de personas.
     * Recorre la lista y muestra solo aquellos que sean entrenadores.
     *
     * @param personsList Lista de personas, donde se encuentran entrenadores y jugadores.
     * @return El número total de entrenadores.
     */
    private static int showAllTrainers(ArrayList<Person> personsList) {
        int j=1;
        for (int i = 0; i <personsList.size(); i++) {
            if (personsList.get(i) instanceof Trainer){
                System.out.println(j+") "+((Trainer)personsList.get(i)).toString());
                j++;
            }
        }
        return j;
    }

    /**
     * Pide al usuario que introduzca un nuevo número de dorsal para un jugador.
     * Valida que el número no esté en uso por otro jugador en el equipo.
     *
     * @param team Equipo al que pertenece el jugador.
     * @return El número de dorsal válido seleccionado.
     */
    private static int getNewNumber(Team team) {
        Scanner scanner=new Scanner(System.in);
        int number;
        boolean valide=false;
        System.out.println("Que dorsal quieres que tenga el jugador?");
        number=scanner.nextInt();
        valide=checkNumber(team,number);
        while (!valide){
            System.out.println("Este dorsal ya existe en este equipo, escoje otro");
            number=scanner.nextInt();
            valide=checkNumber(team,number);
        }
        return number;
    }

    /**
     * Comprueba si un número de dorsal ya está en uso en el equipo.
     *
     * @param team Equipo al que pertenece el jugador.
     * @param number Número de dorsal a comprobar.
     * @return true si el número está disponible, false si ya está en uso.
     */
    private static boolean checkNumber(Team team, int number) {
        int i=0;
        boolean valide=true;
        while (i<team.getTeamPlayers().size()&&valide){
            if (number==team.getTeamPlayers().get(i).getNumber()){
                valide=false;
            }
            i++;
        }
        return valide;
    }

    /**
     * Genera el índice real de un jugador en la lista de personas.
     * Busca el índice correcto considerando solo los objetos de tipo Player.
     *
     * @param index Índice del jugador seleccionado.
     * @param personsList Lista de personas, que incluye jugadores y entrenadores.
     * @return El índice real del jugador en la lista.
     */
    private static int realIndexGenerationP(int index, ArrayList<Person> personsList) {
        int i=0;
        boolean found=false;
        while (!found){
            if (personsList.get(i) instanceof Player){
                index--;
                if (index==0){
                    found=true;
                }else {
                    i++;
                }
            }else {
                i++;
            }
        }
        return i;
    }

    /**
     * Selecciona un jugador de la lista de personas mediante paginación.
     * Permite al usuario navegar por páginas y seleccionar un jugador de la lista.
     *
     * @param personsList Lista de personas, que incluye jugadores y entrenadores.
     * @return El índice del jugador seleccionado.
     */
    private static int selectPlayer(ArrayList<Person> personsList) {
        Scanner scanner=new Scanner(System.in);
        String resp = "";
        boolean selected=false;
        int totalTrainers=allTrainersList(personsList);
        int pages=Tools.aproxUp((double) (personsList.size()-totalTrainers)/20);
        int pagesIn=1;
        boolean valide;
        int index=0;
        System.out.println("Ahora se le mostrara la lista de jugadores diponibles de forma pagina:\n-Si quieres avanzar una pagina pulsa D\n-Si quieres retroceder una pagina pulsa A\n-Si quieres seleccionar su indice pulsa I");
        while (!selected){
            valide=false;
            showPagePlayers(pages,pagesIn,personsList);
            while (!valide){
                resp=scanner.nextLine();
                if (resp.equalsIgnoreCase("D")){
                    if ((pagesIn+1)<=pages){
                        pagesIn++;
                        valide=true;
                    }else {
                        System.out.println("Ya no hay mas paginas");

                    }
                }else if (resp.equalsIgnoreCase("A")){
                    if ((pagesIn-1)>=1){
                        pagesIn--;
                        valide=true;
                    }else {
                        System.out.println("No puedes retroceder mas paginas");
                    }
                }else if (resp.equalsIgnoreCase("I")){
                    System.out.println("Indica el indice");
                    index=scanner.nextInt();
                    if (index>=(pagesIn-1)*20&&index<=pagesIn*20){
                        System.out.println("El jugador a sido seleccionado");
                        valide=true;
                        selected=true;
                    }else{
                        System.out.println("La respuesta no es valida");
                    }
                }
            }

        }

        return index;

    }

    /**
     * Muestra los jugadores disponibles en una página de la lista de personas.
     *
     * @param pages Número total de páginas.
     * @param pagesIn Número de página actual.
     * @param personsList Lista de personas, que incluye jugadores y entrenadores.
     */
    private static void showPagePlayers(int pages, int pagesIn, ArrayList<Person> personsList) {
        int index = (pagesIn-1)*20;
        int j=0;
        int i=0;
        int cuanty=0;
        while (j<20 && i<personsList.size()){
            if (personsList.get(i) instanceof Player){
                if (cuanty==index){
                    System.out.println((index+1+j)+")- "+personsList.get(i).toString());
                    j++;
                }else {
                    cuanty++;
                }

            }
            i++;
        }
        System.out.println("\t pag: "+pagesIn+"/"+pages);
    }

    /**
     * Cuenta todos los entrenadores presentes en la lista de personas.
     *
     * @param personsList Lista de personas, que incluye jugadores y entrenadores.
     * @return El número total de entrenadores.
     */
    private static int allTrainersList(ArrayList<Person> personsList) {
        int count=0;
        for (int i = 0; i < personsList.size(); i++) {
            if (personsList.get(i) instanceof Trainer){
                count++;
            }
        }
        return count;
    }

    /**
     * Destituye al entrenador actual del equipo.
     * Pregunta al usuario si desea destituir al entrenador y, en caso afirmativo,
     * lo elimina del equipo y lo agrega nuevamente a la lista de personas.
     *
     * @param personsList Lista de personas que incluye entrenadores y jugadores.
     * @param team El equipo del cual se destituirá al entrenador.
     */
    private static void fireTrainer(ArrayList<Person> personsList, Team team) {
        String str;
        str = Tools.choseOptionYS("Quieres destituir al entrenador?\n(S/N)");
        if (str.equalsIgnoreCase("S")) {
            personsList.add(team.getTrainer());
            team.setTrainer(null);
            System.out.println("El entrenador ha sido destituido");
        } else {
            System.out.println("El entrenador no ha sido destituido");
        }
    }

    /**
     * Modifica el nombre del presidente de un equipo.
     * Pregunta al usuario si desea cambiar al presidente actual, y si acepta,
     * le solicita un nuevo nombre.
     *
     * @param team El equipo cuyo presidente será modificado.
     */
    private static void modifyPresident(Team team) {
        Scanner scanner = new Scanner(System.in);
        String str;
        String name;
        str = Tools.choseOptionYS("El presidente de este club es " + team.getPresident() + " ¿quieres modificarlo?\n(S/N)");
        if (str.equalsIgnoreCase("S")) {
            System.out.println("¿Qué nombre tendrá el nuevo presidente?");
            name = scanner.nextLine();
            if (name.equals(team.getPresident())) {
                System.out.println("Este presidente ya existe");
            } else {
                team.setPresident(name);
                System.out.println("Se ha asignado el nuevo presidente");
            }
        } else {
            System.out.println("El presidente no ha sido modificado");
        }
    }

    /**
     * Elimina un equipo de la lista de equipos y, si pertenece a una liga, también elimina la liga.
     * Pregunta al usuario si desea eliminar el equipo, y en caso afirmativo,
     * elimina tanto el equipo como la liga, devolviendo los jugadores a la lista de personas.
     *
     * @param teamsList Lista de equipos.
     * @param leauge Liga a la que pertenece el equipo.
     * @param t El equipo que se desea eliminar.
     * @param personsList Lista de personas a la que se devolverán los jugadores del equipo eliminado.
     * @return La liga, que puede ser null si se ha eliminado.
     */
    private static Leauge removeTeam(ArrayList<Team> teamsList, Leauge leauge, Team t, ArrayList<Person> personsList) {
        Scanner scanner = new Scanner(System.in);
        String str;
        if (leauge != null) {
            if (t.getLeauge() != null) {
                str = Tools.choseOptionYS("Este equipo pertenece a la liga actual, si eliminas este equipo,\ntambién eliminarás la liga. ¿Estás seguro de querer borrar el equipo?\n(S/N)");
                if (str.equalsIgnoreCase("S")) {
                    leauge = destroyLeauge(leauge);
                    returnPlayers(personsList, t);
                    teamsList.remove(t);
                    System.out.println("El equipo y la liga han sido eliminados");
                } else {
                    System.out.println("El equipo no se ha eliminado");
                }
            }
        } else {
            str = Tools.choseOptionYS("¿Estás seguro de eliminar a este equipo?");
            if (str.equalsIgnoreCase("S")) {
                returnPlayers(personsList, t);
                teamsList.remove(t);
                System.out.println("El equipo ha sido eliminado");
            } else {
                System.out.println("El equipo no se ha eliminado");
            }
        }
        return leauge;
    }

    /**
     * Devuelve todos los jugadores de un equipo a la lista de personas.
     *
     * @param personsList Lista de personas que incluye entrenadores y jugadores.
     * @param t El equipo cuyos jugadores serán devueltos.
     */
    private static void returnPlayers(ArrayList<Person> personsList, Team t) {
        for (int i = 0; i < t.getTeamPlayers().size(); i++) {
            personsList.add(t.getTeamPlayers().get(i));
        }
    }

    /**
     * Destruye la liga eliminando sus equipos y reiniciando sus goles y puntos.
     * También elimina la asociación de los equipos con la liga y marca a sus jugadores como transferibles.
     *
     * @param leauge La liga que será eliminada.
     * @return null, ya que la liga ha sido destruida.
     */
    private static Leauge destroyLeauge(Leauge leauge) {
        for (int i = 0; i < leauge.getTeams().size(); i++) {
            leauge.getTeams().get(i).restGols();
            leauge.getTeams().get(i).restPoint();
            leauge.getTeams().get(i).setLeauge(null);
            restTransfer(leauge);
        }
        leauge = null;
        return leauge;
    }

    /**
     * Marca a todos los jugadores de los equipos de una liga como transferibles.
     *
     * @param leauge La liga cuyos jugadores serán marcados como transferibles.
     */
    private static void restTransfer(Leauge leauge) {
        for (int i = 0; i < leauge.getTeams().size(); i++) {
            for (int j = 0; j < leauge.getTeams().get(i).getTeamPlayers().size(); j++) {
                leauge.getTeams().get(i).getTeamPlayers().get(j).setTransfer(true);
            }
        }
    }

    /**
     * Busca un entrenador por su nombre y apellido en la lista de personas y equipos.
     * Si lo encuentra, imprime su información.
     *
     * @param name Nombre del entrenador.
     * @param surname Apellido del entrenador.
     * @param personsList Lista de personas que incluye entrenadores y jugadores.
     * @param teamsList Lista de equipos.
     */
    private static void searchTrainer(String name, String surname, ArrayList<Person> personsList, ArrayList<Team> teamsList) {
        int index;
        index = searchInPersonLisTrain(personsList, name, surname);
        if (index != -1) {
            System.out.println(personsList.get(index).toString());
        } else {
            index = searchInTeamListTrain(teamsList, name, surname);
            if (index != -1) {
                System.out.println(teamsList.get(0).getTrainer().toString());
            } else {
                System.out.println("Este entrenador no existe");
            }
        }
    }

    /**
     * Busca un entrenador por su nombre y apellido en la lista de equipos.
     *
     * @param teamsList Lista de equipos.
     * @param name Nombre del entrenador.
     * @param surname Apellido del entrenador.
     * @return El índice del equipo que contiene al entrenador o -1 si no se encuentra.
     */
    private static int searchInTeamListTrain(ArrayList<Team> teamsList, String name, String surname) {
        int i = 0;
        boolean found = false;
        while (!found && i < teamsList.size()) {
            if (teamsList.get(0).getTrainer().getName().equalsIgnoreCase(name)) {
                if (teamsList.get(0).getTrainer().getSurname().equalsIgnoreCase(surname)) {
                    found = true;
                }
            }
            i++;
        }
        if (!found) {
            i = -1;
        } else {
            i--;
        }
        return i;
    }

    /**
     * Busca un entrenador por su nombre y apellido en la lista de personas.
     *
     * @param personsList Lista de personas que incluye entrenadores y jugadores.
     * @param name Nombre del entrenador.
     * @param surname Apellido del entrenador.
     * @return El índice del entrenador o -1 si no se encuentra.
     */
    private static int searchInPersonLisTrain(ArrayList<Person> personsList, String name, String surname) {
        int i = 0;
        boolean found = false;
        while (!found && i < personsList.size()) {
            if (personsList.get(i) instanceof Trainer) {
                if (personsList.get(i).getName().equalsIgnoreCase(name)) {
                    if (personsList.get(i).getSurname().equalsIgnoreCase(surname)) {
                        found = true;
                    }
                }
            }
            i++;
        }
        if (!found) {
            i = -1;
        } else {
            i--;
        }
        return i;
    }

    /**
     * Busca un jugador por su nombre y apellido en la lista de personas y equipos.
     * Si lo encuentra, imprime su información.
     *
     * @param name Nombre del jugador.
     * @param surname Apellido del jugador.
     * @param personsList Lista de personas que incluye entrenadores y jugadores.
     * @param teamsList Lista de equipos.
     */
    private static void searchPlayer(String name, String surname, ArrayList<Person> personsList, ArrayList<Team> teamsList) {
        int index;
        int[] index2;
        index = searchInPersonLisPlayer(personsList, name, surname);
        if (index != -1) {
            System.out.println(personsList.get(index).toString());
        } else {
            index2 = searchInTeamListPlayer(teamsList, name, surname);
            if (index2 != null) {
                System.out.println(teamsList.get(index2[0]).getTeamPlayers().get(index2[1]).toString());
            }
        }
    }

    /**
     * Busca un jugador por su nombre y apellido en la lista de equipos.
     *
     * @param teamsList Lista de equipos.
     * @param name Nombre del jugador.
     * @param surname Apellido del jugador.
     * @return Un array con los índices del equipo y jugador, o null si no se encuentra.
     */
    private static int[] searchInTeamListPlayer(ArrayList<Team> teamsList, String name, String surname) {
        int i=0;
        int j=0;
        int [] res;
        boolean found=false;
        while (!found && i<teamsList.size()){
            while (!found && j<teamsList.get(i).getTeamPlayers().size()){
                if (teamsList.get(i).getTeamPlayers().get(j).getName().equalsIgnoreCase(name)){
                    if (teamsList.get(i).getTeamPlayers().get(j).getSurname().equalsIgnoreCase(surname)){
                        found=true;
                    }
                }
                if (!found){
                    j++;
                }
            }
            if (!found){
                i++;
                j=0;
            }
        }
        if (found){
            res= new int[]{i, j};
        }else {
            res=null;
        }
        return res;
    }

    /**
     * Busca en la lista de personas a un jugador por su nombre y apellido.
     * Si encuentra al jugador, devuelve el índice en la lista. Si no, devuelve -1.
     *
     * @param personsList Lista de personas donde buscar.
     * @param name Nombre del jugador.
     * @param surname Apellido del jugador.
     * @return Índice del jugador en la lista o -1 si no se encuentra.
     */
    private static int searchInPersonLisPlayer(ArrayList<Person> personsList, String name, String surname) {
        int i = 0;
        boolean found = false;
        // Bucle que busca en la lista de personas
        while (!found && i < personsList.size()) {
            if (personsList.get(i) instanceof Player) {  // Verifica si la persona es un jugador
                if (personsList.get(i).getName().equalsIgnoreCase(name)) {  // Compara el nombre
                    if (personsList.get(i).getSurname().equalsIgnoreCase(surname)) {  // Compara el apellido
                        found = true;  // Si coincide, lo marca como encontrado
                    }
                }
            }
            i++;
        }
        if (!found) {
            i = -1;  // Si no se encuentra, se devuelve -1
        } else {
            i--;  // Se ajusta el índice al valor correcto
        }
        return i;
    }

    /**
     * Solicita al usuario ingresar un apellido.
     *
     * @return El apellido introducido por el usuario.
     */
    private static String getSurname() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Qué apellido tiene?");
        return scanner.next();
    }

    /**
     * Solicita al usuario ingresar un nombre.
     *
     * @return El nombre introducido por el usuario.
     */
    private static String getName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Qué nombre tiene?");
        return scanner.next();
    }

    /**
     * Crea un nuevo entrenador, solicitando al usuario la información necesaria.
     * Comprueba que no exista un entrenador con el mismo nombre y apellido.
     *
     * @param personsList Lista de personas.
     * @param teamsList Lista de equipos.
     * @return Un nuevo objeto de tipo Trainer.
     */
    private static Person createTrainer(ArrayList<Person> personsList, ArrayList<Team> teamsList) {
        Scanner scanner = new Scanner(System.in);
        String name;
        String surname;
        int yearOfBirth;
        String nation;
        String str;
        int idstylePlaying;
        String stylePlaying;
        boolean nationalSelect;

        System.out.println("¿Qué nombre va a tener el entrenador?");
        name = scanner.next();
        System.out.println("¿Qué apellido va a tener el entrenador?");
        surname = scanner.next();

        // Comprueba que el nombre no exista en la lista de personas o equipos
        while (nameExist(personsList, teamsList, name, surname)) {
            System.out.println("Este nombre ya existe");
            System.out.println("¿Qué nombre va a tener el entrenador?");
            name = scanner.next();
            System.out.println("¿Qué apellido va a tener el entrenador?");
            surname = scanner.next();
        }

        System.out.println("¿En qué año nació el entrenador?");
        yearOfBirth = scanner.nextInt();
        System.out.println("¿En qué país nació el entrenador?");
        scanner.nextLine();  // Consumir el salto de línea pendiente
        nation = scanner.nextLine();

        System.out.println("¿Este entrenador ha sido seleccionador nacional? (S/N)");
        str = scanner.next();
        // Valida que la entrada sea S o N
        while (!str.equalsIgnoreCase("S") && !str.equalsIgnoreCase("N")) {
            System.out.println("La respuesta no es válida:");
            str = scanner.next();
        }

        // Asigna si es seleccionador nacional o no
        nationalSelect = str.equalsIgnoreCase("S");

        System.out.println("¿Qué estilo de juego va a tener tu entrenador?");
        System.out.println("-1) Posesión\n-2) Box to box\n-3) Repliegue\n-4) Contraataque");
        idstylePlaying = scanner.nextInt();

        // Valida que el estilo de juego sea una opción válida
        while (idstylePlaying > 4 || idstylePlaying < 1) {
            System.out.println("Escoge una opción válida");
            idstylePlaying = scanner.nextInt();
        }

        // Asigna el estilo de juego según la elección del usuario
        stylePlaying = Tools.getStyles()[idstylePlaying - 1];

        System.out.println("¡El entrenador fue creado!");
        // Retorna un nuevo objeto Trainer
        return new Trainer(name, yearOfBirth, nation, surname, nationalSelect, stylePlaying);
    }

    /**
     * Crea un nuevo jugador, solicitando al usuario la información necesaria.
     * Comprueba que no exista un jugador con el mismo nombre y apellido.
     *
     * @param personsList Lista de personas.
     * @param teamsList Lista de equipos.
     * @return Un nuevo objeto de tipo Player.
     */
    private static Person createPlayer(ArrayList<Person> personsList, ArrayList<Team> teamsList) {
        Scanner scanner = new Scanner(System.in);
        String name;
        String surname;
        int yearOfBirth;
        String nation;
        double heigth;
        int weight;
        double quality;
        int option;
        int idstylePlaying;
        String stylePlaying;
        String position = "POR";  // Posición por defecto: portero
        int idposition = 1;

        System.out.println("¿Qué nombre va a tener el jugador?");
        name = scanner.next();
        System.out.println("¿Qué apellido va a tener el jugador?");
        surname = scanner.next();

        // Comprueba que el nombre no exista en la lista de personas o equipos
        while (nameExist(personsList, teamsList, name, surname)) {
            System.out.println("Este nombre ya existe");
            System.out.println("¿Qué nombre va a tener el jugador?");
            name = scanner.next();
            System.out.println("¿Qué apellido va a tener el jugador?");
            surname = scanner.next();
        }

        System.out.println("¿En qué año nació el jugador?");
        yearOfBirth = scanner.nextInt();
        System.out.println("¿En qué país nació el jugador?");
        scanner.nextLine();  // Consumir el salto de línea pendiente
        nation = scanner.nextLine();
        System.out.println("¿Qué altura tiene el jugador?");
        heigth = Tools.format(scanner.nextDouble(), 2);  // Formatea la altura con 2 decimales
        System.out.println("¿Qué nivel de calidad tiene el jugador?");
        quality = Tools.format(scanner.nextDouble(), 1);  // Formatea la calidad con 1 decimal

        // Selección de posición del jugador
        option = Tools.choseOption("¿En qué posición del campo quieres que juegue el jugador?\n-1) En la portería\n-2) En la defensa\n-3) En el mediocampo\n-4) En el ataque", 1, 4);
        if (option == 2) {
            idposition = Tools.choseOption("Escoge una posición:\n-1) DFC\n-2) LI\n-3) LD", 1, 3);
        } else if (option == 3) {
            idposition = Tools.choseOption("Escoge una posición:\n-1) MC\n-2) MCD\n-3) MCO", 1, 3);
        } else if (option == 4) {
            idposition = Tools.choseOption("Escoge una posición:\n-1) EI\n-2) ED\n-3) DLC", 1, 3);
        }

        // Asigna la posición según la elección del usuario
        if (option == 2) {
            position = Tools.getPosDef()[idposition - 1];
        } else if (option == 3) {
            position = Tools.getPosMid()[idposition - 1];
        } else if (option == 4) {
            position = Tools.getPosOfe()[idposition - 1];
        }

        // Selección del estilo de juego del jugador
        idstylePlaying = Tools.choseOption("¿Qué estilo de juego va a tener tu jugador?\n-1) Posesión\n-2) Box to box\n-3) Repliegue\n-4) Contraataque", 1, 4);
        stylePlaying = Tools.getStyles()[idstylePlaying - 1];

        System.out.println("¿Cuánto pesa el jugador?");
        weight = scanner.nextInt();

        System.out.println("¡El jugador fue creado!");
        // Retorna un nuevo objeto Player
        return new Player(name, yearOfBirth, nation, surname, heigth, weight, quality, stylePlaying, position);
    }

    /**
     * Comprueba si ya existe una persona o jugador con el mismo nombre y apellido.
     *
     * @param personsList Lista de personas.
     * @param teamsList Lista de equipos.
     * @param name Nombre a verificar.
     * @param surname Apellido a verificar.
     * @return true si ya existe una persona o jugador con ese nombre, false en caso contrario.
     */
    private static boolean nameExist(ArrayList<Person> personsList, ArrayList<Team> teamsList, String name, String surname) {
        boolean found=false;
        int i=0;
        int j=0;
        while (!found && i<personsList.size()){
            if ((name+surname).equalsIgnoreCase(personsList.get(i).getName()+personsList.get(i).getSurname())){
                found=true;
            }
            i++;
        }
        if (!found){
            i=0;
            while (!found && i<teamsList.size()){
                j=0;
                if ((name+surname).equalsIgnoreCase(teamsList.get(i).getTrainer().getName()+teamsList.get(i).getTrainer().getSurname())){
                    found=true;
                }
                while (!found && j<teamsList.get(i).getTeamPlayers().size()){
                    if ((name+surname).equalsIgnoreCase(teamsList.get(i).getTeamPlayers().get(j).getName()+teamsList.get(i).getTeamPlayers().get(j).getSurname())){
                        found=true;
                    }
                    j++;
                }
                i++;
            }
        }
        return found;
    }

    /**
     * Método que pregunta al usuario si desea crear una nueva liga.
     * Si se elige crear una nueva liga, los valores de la liga actual serán reiniciados.
     *
     * @param leauge La liga actual que podría ser reemplazada.
     * @return true si el usuario decide crear una nueva liga, false en caso contrario.
     */
    private static boolean askCreationLeague(Leauge leauge) {
        boolean response;
        String str;
        str = Tools.choseOptionYS("Si creas una nueva Liga, la antigua Liga se destruira, ¿quieres crearla de todas formas?\n(S/N)");
        if (str.equalsIgnoreCase("S")) {
            resetValues(leauge);
            response = true;
        } else {
            response = false;
            System.out.println("No se creará ninguna nueva liga");
        }
        return response;
    }

    /**
     * Método que reinicia los valores de una liga, restableciendo los goles y puntos de todos los equipos.
     *
     * @param leauge La liga cuyos valores serán reiniciados.
     */
    private static void resetValues(Leauge leauge) {
        for (int i = 0; i < leauge.getTeams().size(); i++) {
            leauge.getTeams().get(i).restGols();
            leauge.getTeams().get(i).restPoint();
        }
    }

    /**
     * Método que permite crear una nueva liga a partir de una lista de equipos.
     * El usuario selecciona los equipos que formarán parte de la nueva liga.
     *
     * @param teamsList Lista de equipos disponibles para formar la liga.
     * @return La nueva liga creada.
     */
    private static Leauge CreateLeauge(ArrayList<Team> teamsList) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> idClubes = new ArrayList<>();
        ArrayList<Team> teamsLeauge;
        boolean finish = false;
        String respuesta;
        int select;
        String name;
        int i = 0;

        // Muestra todos los equipos disponibles
        System.out.println(showAllTeams(teamsList));

        // Proceso para añadir equipos a la liga
        while (!finish) {
            System.out.println("Selecciona el equipo número " + (i + 1));
            select = scanner.nextInt();
            if (select > teamsList.size()) {
                System.out.println("Selecciona una de las opciones posibles");
                i--;
            } else if (idClubes.contains(select - 1)) {
                System.out.println("Este club ya ha sido seleccionado");
                i--;
            } else {
                System.out.println("El club fue añadido");
                idClubes.add(select - 1);
            }

            // Permitir finalizar la selección de clubes cuando hay al menos 8
            if (i >= 7 && i % 2 == 1) {
                respuesta = Tools.choseOptionYS("¿Quieres dejar de añadir clubes?\n(S/N)");
                if (respuesta.equalsIgnoreCase("S")) {
                    finish = true;
                }
            }
            i++;
        }

        // Crear la lista de equipos seleccionados para la liga
        teamsLeauge = createTeamLeague(teamsList, idClubes);

        System.out.println("¿Qué nombre tendrá la liga?");
        scanner.nextLine(); // Limpia el buffer
        name = scanner.nextLine();

        // Crear la nueva liga
        Leauge leauge = new Leauge(teamsLeauge, name);
        assignLeauge(leauge);

        return leauge;
    }

    /**
     * Método que asigna una liga a todos los equipos de la liga creada.
     *
     * @param leauge La liga a asignar.
     */
    private static void assignLeauge(Leauge leauge) {
        for (int i = 0; i < leauge.getTeams().size(); i++) {
            leauge.getTeams().get(i).setLeauge(leauge);
        }
    }

    /**
     * Método que crea una lista de equipos seleccionados para la liga a partir de una lista de IDs.
     *
     * @param teamsList Lista original de equipos disponibles.
     * @param idClubes Lista de IDs de equipos seleccionados.
     * @return Lista de equipos seleccionados para la liga.
     */
    private static ArrayList<Team> createTeamLeague(ArrayList<Team> teamsList, ArrayList<Integer> idClubes) {
        ArrayList<Team> clubs = new ArrayList<>();
        for (int i = 0; i < idClubes.size(); i++) {
            clubs.add(teamsList.get(idClubes.get(i)));
        }
        return clubs;
    }

    /**
     * Método que muestra todos los equipos disponibles en forma de lista numerada.
     *
     * @param teamsList Lista de equipos a mostrar.
     * @return Una cadena de texto con todos los equipos numerados.
     */
    private static String showAllTeams(ArrayList<Team> teamsList) {
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < teamsList.size(); i++) {
            strB.append((i + 1) + ") " + teamsList.get(i).getName() + "\n");
        }
        return strB.toString();
    }

    /**
     * Método que muestra todos los equipos disponibles excepto uno especificado.
     *
     * @param teamsList Lista de equipos a mostrar.
     * @param not Índice del equipo a omitir.
     * @return Una cadena de texto con los equipos numerados, omitiendo el especificado.
     */
    private static String showAllTeams(ArrayList<Team> teamsList, int not) {
        StringBuilder strB = new StringBuilder();
        int j = 1;
        for (int i = 0; i < teamsList.size(); i++) {
            if (i != not) {
                strB.append(j + ") " + teamsList.get(i).getName() + "\n");
                j++;
            }
        }
        return strB.toString();
    }

    /**
     * Método que imprime un mensaje de error cuando no se encuentra un club.
     */
    private static void notFound() {
        System.out.println("Este club no existe");
    }

    /**
     * Método que muestra los detalles de un equipo.
     *
     * @param team El equipo a mostrar.
     */
    private static void showClub(Team team) {
        System.out.println(team.toString());
    }

    /**
     * Método que busca un club en la lista de equipos según el nombre proporcionado por el usuario.
     *
     * @param teamsList Lista de equipos disponibles.
     * @return El índice del club encontrado o -1 si no se encuentra.
     */

    private static int searchClub(ArrayList<Team> teamsList) {
        Scanner scanner=new Scanner(System.in);
        String name;
        boolean found=false;
        int i=0;
        System.out.println("Introduce el nombre del club que estas buscando");
        name = scanner.nextLine();
        while (!found && i<teamsList.size()){
            if (teamsList.get(i).getName().equals(name)){
                found=true;
            }else {
                i++;
            }
        }
        if (!found){
            i=-1;
        }
        return i;
    }

    /**
     * Método que crea una lista de clubes (equipos) a partir de una lista de personas.
     * Se generan equipos con un entrenador y un conjunto de jugadores seleccionados aleatoriamente.
     *
     * @param personsList Lista de personas de las cuales se seleccionarán los jugadores y entrenadores.
     * @param randomUsedNamesClub Lista de nombres de clubes utilizados para evitar duplicados.
     * @param ramdomNamesTeams Nombres de equipos disponibles para asignar.
     * @param listNames Lista de nombres disponibles para los presidentes de los clubes.
     * @param listSurnames Lista de apellidos disponibles para los presidentes de los clubes.
     * @param randomUsedNamesPers Lista de nombres de presidentes utilizados para evitar duplicados.
     * @return Lista de equipos creados.
     */
    private static ArrayList<Team> createListClubs(ArrayList<Person> personsList, ArrayList<Integer> randomUsedNamesClub, String[] ramdomNamesTeams, String[] listNames, String[] listSurnames, ArrayList<String> randomUsedNamesPers) {
        ArrayList<Player> players;
        ArrayList<Team> listClub = new ArrayList<>();
        String nameTeam;
        String namePresident;
        int def, mid, ofe, golk;
        Random random = new Random();
        Player[] top11;
        int m, amount = 0;
        Trainer trainer;

        for (int i = 0; i < 20; i++) {
            def = 0;
            mid = 0;
            ofe = 0;
            golk = 0;
            players = new ArrayList<>();
            m = 0;
            trainer = null;

            // Selección de jugadores y un entrenador para el club
            while (def < 5 || mid < 5 || ofe < 5 || golk < 2 || trainer == null) {
                m = random.nextInt(500 - amount);
                if (personsList.get(m) instanceof Player) {
                    if (Tools.in(((Player) personsList.get(m)).getPosition(), Tools.getPosOfe()) != -1 && ofe < 5) {
                        players.add((Player) personsList.get(m));
                        personsList.remove(m);
                        ofe++;
                        amount++;
                    } else if (Tools.in(((Player) personsList.get(m)).getPosition(), Tools.getPosMid()) != -1 && mid < 5) {
                        players.add((Player) personsList.get(m));
                        personsList.remove(m);
                        mid++;
                        amount++;
                    } else if (Tools.in(((Player) personsList.get(m)).getPosition(), Tools.getPosDef()) != -1 && def < 5) {
                        players.add((Player) personsList.get(m));
                        personsList.remove(m);
                        def++;
                        amount++;
                    } else if (golk < 2) {
                        players.add((Player) personsList.get(m));
                        personsList.remove(m);
                        golk++;
                        amount++;
                    }
                } else if (trainer == null) {
                    trainer = (Trainer) personsList.get(m);
                    personsList.remove(m);
                    amount++;
                }
            }

            // Establecer números de camiseta, generar nombres y crear el equipo
            setNumbers(players);
            top11 = generateTop11(players);
            namePresident = generateNamePresident(randomUsedNamesPers, listNames, listSurnames);
            nameTeam = generateNameTeam(randomUsedNamesClub, ramdomNamesTeams);
            listClub.add(new Team(nameTeam, players, top11, trainer, namePresident));

            // Actualizar la tasa y la química del equipo
            listClub.get(i).updateRateTeam();
            listClub.get(i).updateChemestry();
            assignClub(listClub.get(i));
            setNotTransfer(listClub.get(i));
        }
        return listClub;
    }

    /**
     * Método que establece que los jugadores de un equipo no están en transferencia.
     *
     * @param team El equipo cuyo estado de transferencia se establecerá en falso para todos los jugadores.
     */
    private static void setNotTransfer(Team team) {
        for (int i = 0; i < team.getTeamPlayers().size(); i++) {
            team.getTeamPlayers().get(i).setTransfer(false);
        }
    }

    /**
     * Método que asigna un club a cada jugador y al entrenador del equipo.
     *
     * @param team El equipo al que se asignarán los jugadores y el entrenador.
     */
    private static void assignClub(Team team) {
        for (int i = 0; i < team.getTeamPlayers().size(); i++) {
            team.getTeamPlayers().get(i).setClub(team);
        }
        team.getTrainer().setClub(team);
    }

    /**
     * Método que genera un nombre de equipo aleatorio.
     * Asegura que no se repitan los nombres de los clubes ya utilizados.
     *
     * @param randomUsedNamesClub Lista de índices de nombres de clubes ya utilizados.
     * @param ramdomNamesTeams Lista de nombres de equipos disponibles.
     * @return El nombre del equipo generado.
     */
    private static String generateNameTeam(ArrayList<Integer> randomUsedNamesClub, String[] ramdomNamesTeams) {
        Random random = new Random();
        int index = 0;
        boolean valide = false;

        while (!valide) {
            index = random.nextInt(50);
            if (!randomUsedNamesClub.contains(index)) {
                valide = true;
                randomUsedNamesClub.add(index);
            }
        }
        return ramdomNamesTeams[index];
    }

    /**
     * Método que genera un nombre de presidente aleatorio.
     * Asegura que no se repitan los nombres de presidentes ya utilizados.
     *
     * @param randomUsedNamesPers Lista de nombres de presidentes ya utilizados.
     * @param listNames Lista de nombres disponibles para los presidentes.
     * @param listSurnames Lista de apellidos disponibles para los presidentes.
     * @return El nombre completo del presidente generado.
     */
    private static String generateNamePresident(ArrayList<String> randomUsedNamesPers, String[] listNames, String[] listSurnames) {
        Random random = new Random();
        int inxName = 0, inxSurn = 0;
        boolean valide = false;

        while (!valide) {
            inxName = random.nextInt(100);
            inxSurn = random.nextInt(100);
            if (!randomUsedNamesPers.contains(Tools.generateString(inxName, inxSurn))) {
                valide = true;
                randomUsedNamesPers.add(Tools.generateString(inxName, inxSurn));
            }
        }
        return listNames[inxName] + " " + listSurnames[inxSurn];
    }

    /**
     * Método que asigna números de camiseta únicos a los jugadores del equipo.
     *
     * @param players Lista de jugadores a los que se les asignarán números.
     */
    private static void setNumbers(ArrayList<Player> players) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        int number;

        for (int i = 0; i < players.size(); i++) {
            number = random.nextInt(100);
            if (!numbers.contains(number)) {
                players.get(i).setNumber(number);
            } else {
                i--; // Reintentar si el número ya está asignado
            }
        }
    }

    /**
     * Método que genera un equipo titular (top 11) a partir de una lista de jugadores.
     * La selección se basa en la calidad de los jugadores y sus posiciones.
     *
     * @param players Lista de jugadores de los cuales se seleccionará el equipo titular.
     * @return Un arreglo de jugadores que representa al equipo titular.
     */
    private static Player[] generateTop11(ArrayList<Player> players) {
        Player[] top11;
        double ofe = 0;
        double def = 0;
        double mid = 0;
        String option;
        double maxGoalkeper=0;
        int idGoalkeper=0;
        for (int i = 0; i <players.size() ; i++) {
            if (Tools.in(players.get(i).getPosition(),Tools.getPosOfe())!=-1){
               ofe+=players.get(i).getQuality();
            }else if (Tools.in(players.get(i).getPosition(),Tools.getPosMid())!=-1){
                mid+=players.get(i).getQuality();
            }else if (Tools.in(players.get(i).getPosition(),Tools.getPosDef())!=-1){
                def+=players.get(i).getQuality();
            }else if (players.get(i).getQuality()>maxGoalkeper){
                maxGoalkeper=players.get(i).getQuality();
                idGoalkeper=i;
            }
        }
        option=Tools.wich2Max(new double[] {def,mid,ofe});
        if (option.equals("01")){
            top11=createTeam(players,idGoalkeper,5,3,2);
        }else if (option.equals("02")){
            top11=createTeam(players,idGoalkeper,4,2,4);
        }else if (option.equals("10")||option.equals("12")){
            top11=createTeam(players,idGoalkeper,3,4,3);
        }else {
            top11=createTeam(players,idGoalkeper,3,3,4);
        }
        return top11;
    }

    /**
     * Crea un equipo de fútbol de 11 jugadores.
     *
     * @param players          Lista de jugadores disponibles.
     * @param idGoalkeper     Índice del portero en la lista de jugadores.
     * @param maxDef          Número máximo de defensores.
     * @param maxMid          Número máximo de centrocampistas.
     * @param maxOfe          Número máximo de delanteros.
     * @return Un array de 11 jugadores que representa el equipo.
     */
    private static Player[] createTeam(ArrayList<Player> players, int idGoalkeper, int maxDef, int maxMid, int maxOfe) {
        Player[] top11 = new Player[11]; // Array para almacenar los 11 jugadores del equipo
        top11[0] = players.get(idGoalkeper); // Asigna el portero al equipo
        int id = 1; // Índice del siguiente jugador a agregar
        int def = 0; // Contador de defensores
        int mid = 0; // Contador de centrocampistas
        int ofe = 0; // Contador de delanteros
        int i = 0; // Índice para recorrer la lista de jugadores

        // Se asegura de que se cumplan los requisitos máximos para defensores, centrocampistas y delanteros
        while (def < maxDef || mid < maxMid || ofe < maxOfe) {
            if (Tools.in(players.get(i).getPosition(), Tools.getPosDef()) != -1) { // Si es defensor
                if (def < maxDef) {
                    top11[id] = players.get(i); // Asigna al defensor al equipo
                    id++;
                    def++; // Incrementa el contador de defensores
                }
            } else if (Tools.in(players.get(i).getPosition(), Tools.getPosMid()) != -1) { // Si es centrocampista
                if (mid < maxMid) {
                    top11[id] = players.get(i); // Asigna al centrocampista al equipo
                    id++;
                    mid++; // Incrementa el contador de centrocampistas
                }
            } else if (Tools.in(players.get(i).getPosition(), Tools.getPosOfe()) != -1) { // Si es delantero
                if (ofe < maxOfe) {
                    top11[id] = players.get(i); // Asigna al delantero al equipo
                    id++;
                    ofe++; // Incrementa el contador de delanteros
                }
            }
            i++; // Avanza al siguiente jugador
        }
        return top11; // Devuelve el equipo completo
    }

    /**
     * Genera una lista de nombres de equipos de fútbol aleatorios.
     *
     * @return Un array de nombres de equipos de fútbol.
     */
    private static String[] listRanName() {
        return new String[] {
                "Rayos Cósmicos CF", "FC Truenos Galácticos", "Dragones de Neptuno", "Centauron FC", "Montecastro FC",
                "CF Titanes", "FC Sierra", "Cerdanyola CF", "Sant Cugat CF", "Lugo FC", "Sant Quirze CF", "CF MontBlanc",
                "FC Bolonia", "Raimon CF", "Los Nebulosa FC", "Royal Academy", "Zeus CF", "Real Castellar CF",
                "Triple Entente CF", "Dublin CF", "Racing de Bilbao", "Sabadell CF", "Estrella Betica CF",
                "Atletico Oriental", "FC Real Ceramica", "Astro CF", "Roma CF", "Santander SD", "Villa Caldo FC",
                "Damia CD", "Academy Sillon", "Granollers SD", "Real Villegas", "Varsovia SD", "Estrella Dalton",
                "Spurs", "Olimpic Salva", "Real Zaragoza", "Vintus CD", "Marsella SD", "S", "Atletico Toledo",
                "Real Alhambra CF", "Real Londres SD", "Olimpic Covadonga", "Racing Chicago", "Santa Sevilla CF",
                "Real Mosku SD", "Racing Salamanca", "FC Santa Coloma"
        };
    }

    /**
     * Genera una lista de países.
     *
     * @return Un array de nombres de países.
     */
    private static String[] listCoun() {
        return new String[] {
                "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda",
                "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria",
                "Azerbaiyán", "Bahamas", "Bangladés", "Barbados", "Baréin", "Bélgica", "Belice",
                "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana",
                "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde",
                "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre",
                "Ciudad del Vaticano", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur",
                "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica",
                "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia",
                "Eslovenia", "España", "Estados Unidos", "Estonia", "Etiopía", "Filipinas", "Finlandia",
                "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia",
                "Guatemala", "Guyana", "Guinea", "Guinea ecuatorial", "Guinea-Bisáu", "Haití",
                "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia",
                "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania",
                "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia",
                "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar",
                "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania",
                "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique",
                "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda",
                "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea",
                "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana",
                "República Checa", "República de Macedonia", "República del Congo",
                "República Democrática del Congo", "República Dominicana", "República Sudafricana",
                "Ruanda", "Rumanía", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino",
                "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal",
                "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka",
                "Suazilandia", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia",
                "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago",
                "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Ur"
        };
    }

    /**
     * Crea una lista de personas (jugadores y entrenadores) aleatorias.
     *
     * @param listNames       Array de nombres disponibles.
     * @param listSurnames    Array de apellidos disponibles.
     * @param randomUsed      Lista de nombres generados para evitar duplicados.
     * @param listCountrys    Array de países disponibles.
     * @return Una lista de objetos Person (jugadores y entrenadores).
     */
    private static ArrayList<Person> createList(String[] listNames, String[] listSurnames, ArrayList<String> randomUsed, String[] listCountrys) {
        ArrayList<Person> list = new ArrayList<>(); // Lista para almacenar los objetos Person
        Random random = new Random(); // Generador de números aleatorios
        boolean valide; // Bandera para verificar la validez de los índices
        int indexName = 0; // Índice para seleccionar nombres
        int indexSurname = 0; // Índice para seleccionar apellidos
        String name; // Variable para el nombre
        String surname; // Variable para el apellido
        String nation; // Variable para el país
        int yearOfBirth; // Variable para el año de nacimiento
        boolean nationalSelec; // Bandera para verificar si el entrenador es seleccionado nacional
        String position; // Variable para la posición del jugador
        String style; // Variable para el estilo del jugador o entrenador

        // Se crean 500 personas (jugadores y entrenadores)
        for (int i = 0; i < 500; i++) {
            valide = false;
            while (!valide) {
                // Genera índices aleatorios para el nombre y el apellido
                indexName = random.nextInt(100);
                indexSurname = random.nextInt(100);
                String index = Tools.generateString(indexName, indexSurname); // Genera un índice único
                // Verifica si el índice ya ha sido utilizado
                if (!randomUsed.contains(index)) {
                    valide = true; // Si es válido, se puede usar
                    randomUsed.add(index); // Agrega a la lista de usados
                }
            }

            // Asignación de valores
            name = listNames[indexName];
            surname = listSurnames[indexSurname];
            nation = listCountrys[random.nextInt(100)];
            yearOfBirth = random.nextInt(21) + 1986; // Años de nacimiento entre 1986 y 2006
            style = Tools.getStyles()[random.nextInt(4)];

            // Se crean entrenadores para los primeros 40 índices
            if (i < 40) {
                nationalSelec = random.nextInt(2) == 0; // Selecciona aleatoriamente si es entrenador nacional
                list.add(new Trainer(name, yearOfBirth, nation, surname, nationalSelec, style));
            } else {
                // Para jugadores, se generan características adicionales
                double height = Tools.format(random.nextDouble() * (0.5) + 1.6, 2); // Altura entre 1.6 y 2.1
                int weight = random.nextInt(61) + 55; // Peso entre 55 y 115
                double quality = Tools.format(random.nextDouble() * (69) + 30, 1); // Calidad entre 30 y 99

                // Asignación de posición según el índice
                if (i < 180) {
                    position = Tools.getPosDef()[random.nextInt(3)]; // Posición defensiva
                } else if (i < 320) {
                    position = Tools.getPosMid()[random.nextInt(3)]; // Posición de mediocampo
                } else if (i < 460) {
                    position = Tools.getPosOfe()[random.nextInt(3)]; // Posición ofensiva
                } else {
                    position = "POR"; // Portero
                }
                list.add(new Player(name, yearOfBirth, nation, surname, height, weight, quality, style, position)); // Agrega un jugador a la lista
            }
        }
        return list; // Devuelve la lista de personas
    }

    /**
     * Devuelve una lista de nombres comunes.
     *
     * @return Un array de nombres como {@link String}.
     */
    private static String[] listNam() {
        return new String[]{
                "Ana", "Juan", "María", "Carlos", "Goku", "Pedro", "Sofía", "Diego",
                "Valentina", "Andrés", "Camila", "Luis", "Isabella", "Gabriel", "Lucía",
                "Miguel", "Valeria", "Javier", "Elena", "Daniel", "Carmen", "Alejandro",
                "Paula", "Adrián", "Aimad", "David", "Sara", "José", "Alba", "Pablo",
                "Clara", "Frank", "Natalia", "Fernando", "Olivia", "Roberto", "Inés",
                "Francisco", "Aitana", "Alberto", "Celia", "Jorge", "Conor", "Mario",
                "Julia", "Ricardo", "Valeria", "Antonio", "Lucía", "Manuel", "Marina",
                "Ángel", "Irene", "Rubén", "Alicia", "Guillem", "Paulina", "Óscar",
                "Daniela", "Sergio", "Carla", "Hugo", "Cristina", "Eduardo", "Ilia",
                "Federico", "Violeta", "Joaquín", "Ainhoa", "Mateo", "Jimena", "Samuel",
                "Aurora", "Emilio", "Luna", "Gonzalo", "Valentía", "Diego", "Bianca",
                "Bruno", "Noa", "Iker", "Leire", "Marcos", "Marta", "Nicolás", "Iris",
                "Tomás", "Alma", "Víctor", "Ariadna", "Leo", "Eva", "Adrián", "Lía",
                "Nacho", "Nora", "Iván", "Candela", "Javier", "Vegeta", "Carlos", "Mohamed", "Jualius", "Chapo", "Adolf"
        };
    }

    /**
     * Devuelve una lista de apellidos comunes.
     *
     * @return Un array de apellidos como {@link String}.
     */
    private static String[] listSur() {
        return new String[]{
                "García", "Rodríguez", "Martínez", "López", "Pérez", "González",
                "Hernández", "Sánchez", "Fernández", "Torres", "Ramírez", "Díaz",
                "Ruiz", "Vargas", "Jiménez", "Moreno", "Alonso", "Romero", "Ortega",
                "Silva", "Ramos", "Molina", "Castro", "Convalia", "Chávez", "Cruz",
                "Vega", "Gómez", "Mendoza", "Álvarez", "Reyes", "Flores", "Guerrero",
                "Medina", "Núñez", "Herrera", "Cortés", "Rojas", "Vásquez", "Peralta",
                "Delgado", "Mcgregor", "Valdez", "Montes", "Cabrera", "Santos",
                "Villanueva", "León", "Carrillo", "Vidal", "Cervantes", "Bautista",
                "Navarro", "Miranda", "Zamora", "Escobar", "Pacheco", "Cordero",
                "Cisneros", "Barrera", "Cano", "Cárdenas", "Lara", "Ríos", "Salas",
                "Villarreal", "Maldonado", "Valencia", "Aguilar", "Orozco", "Cuevas",
                "Gallegos", "Bernal", "Serrano", "Téllez", "Castaño", "Cuevas",
                "Aisa", "Becerra", "Ruano", "Carranza", "Mata", "Córdova", "Villa",
                "Villegas", "Topuria", "Canales", "Cárdenas", "Cuesta", "Munguía",
                "Bustamante", "Cruz", "Cervantes", "Bautista", "Navarro", "Boutanghach", "Messi",
                "Ronaldo", "Carlos", "Hernandez"
        };
    }

    /**
     * Muestra un menú para la gestión de equipos y retorna la opción seleccionada por el usuario.
     *
     * @return Opción seleccionada como {@link int}.
     */
    private static int menuTeamManagment(){
        Scanner sc =new Scanner(System.in);
        int option;
        System.out.println(Tools.getYellow()+"Gestionar Equipo\n"+Tools.getReset());
        System.out.println("\t"+Tools.getYellow()+"1- "+Tools.getReset()+"Dar de baja a un equipo");
        System.out.println("\t"+Tools.getYellow()+"2- "+Tools.getReset()+"Modificar Presidente");
        System.out.println("\t"+Tools.getYellow()+"3- "+Tools.getReset()+"Destituir entrenador");
        System.out.println("\t"+Tools.getYellow()+"4- "+Tools.getReset()+"Fixar jugador o entrenador");
        System.out.println("\t"+Tools.getYellow()+"5- "+Tools.getReset()+"Transferir jugador");
        System.out.println("\t"+Tools.getYellow()+"0- "+Tools.getReset()+"Salir");
        option=sc.nextInt();
        while (option<0 || option>5 ){
            System.out.println("Escoje un valor entre 0 y 5");
            option=sc.nextInt();
        }
        return option;
    }

    /**
     * Muestra la clasificación de la liga.
     *
     * @param leauge La liga cuyo ranking se desea mostrar.
     */
    private static void showLeauge(Leauge leauge) {
        // Imprime el nombre de la liga en color amarillo.
        System.out.println("\t" + Tools.getYellow() + "-( " + leauge.getName() + " )-" + Tools.getReset() + "\n");

        // Recorre la lista de equipos de la liga.
        for (int i = 0; i < leauge.getTeams().size(); i++) {
            // Cambia el color de la numeración según la posición del equipo.
            if (i == 0) {
                System.out.print(Tools.getBlue() + "(" + (i + 1) + ")- " + Tools.getReset());
            } else if (i < 3) {
                System.out.print(Tools.getGreen() + "(" + (i + 1) + ")- " + Tools.getReset());
            } else if (i < leauge.getTeams().size() - 2) {
                System.out.print("(" + (i + 1) + ")- ");
            } else {
                System.out.print(Tools.getRed() + "(" + (i + 1) + ")- " + Tools.getReset());
            }
            // Imprime el nombre del equipo, sus puntos y goles.
            System.out.println(leauge.getTeams().get(i).getName() + "  Puntos: " + leauge.getTeams().get(i).getPoints() + "  Goles: " + leauge.getTeams().get(i).getGols());
        }
    }

    /**
     * Muestra un mensaje indicando que la liga es nula y no se puede mostrar.
     */
    private static void leagueIsNull() {
        System.out.println("No se puede mostrar porque la liga es Nula");
    }

    /**
     * Muestra el menú principal del Futbol Manager y obtiene la decisión del usuario.
     *
     * @return La opción seleccionada por el usuario.
     */
    private static int menu() {
        Scanner sc = new Scanner(System.in);
        int decision;

        // Imprime un mensaje de bienvenida.
        System.out.println(Tools.getYellow() + "Bienvenido al Futbol Manager" + Tools.getReset() + "\n");

        // Muestra las opciones del menú.
        System.out.println("\t" + Tools.getYellow() + "1- " + Tools.getReset() + "Ver clasificacion de la Liga");
        System.out.println("\t" + Tools.getYellow() + "2- " + Tools.getReset() + "Gestionar equipos");
        System.out.println("\t" + Tools.getYellow() + "3- " + Tools.getReset() + "Dar de alta a equipo");
        System.out.println("\t" + Tools.getYellow() + "4- " + Tools.getReset() + "Dar de alta a jugador o entrenador");
        System.out.println("\t" + Tools.getYellow() + "5- " + Tools.getReset() + "Consultar datos de equipo");
        System.out.println("\t" + Tools.getYellow() + "6- " + Tools.getReset() + "Consultar datos de jugador o entrenador");
        System.out.println("\t" + Tools.getYellow() + "7- " + Tools.getReset() + "Crear Liga");
        System.out.println("\t" + Tools.getYellow() + "8- " + Tools.getReset() + "Jugar Liga");
        System.out.println("\t" + Tools.getYellow() + "0- " + Tools.getReset() + "Salir");

        // Obtiene la opción seleccionada por el usuario.
        decision = sc.nextInt();

        // Valida la opción seleccionada.
        while (decision < 0 || decision > 8) {
            System.out.println("Escoje una opcion del 0 al 8");
            decision = sc.nextInt();
        }

        return decision;
    }
}
