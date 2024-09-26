package utils;

import pers.Player;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Mohamed Boutanghach
 * Clase que proporciona herramientas y utilidades para el manejo de datos en el sistema de gestión de fútbol.
 */
public class Tools {
    /** Posiciones ofensivas. */
    final static String[] posOfe = {"EI", "ED", "DLC"};

    /** Posiciones de mediocampo. */
    final static String[] posMid = {"MC", "MCD", "MCO"};

    /** Posiciones defensivas. */
    final static String[] posDef = {"DFC", "LI", "LD"};

    /** Estilos de juego. */
    final static String[] styles = {"Possesion", "Box to box", "Repliegue", "Contra ataque"};

    /** Color verde para la salida de texto en consola. */
    final static String green = "\u001B[32m";

    /** Color azul para la salida de texto en consola. */
    final static String blue = "\u001B[34m";

    /** Color amarillo para la salida de texto en consola. */
    final static String yellow = "\u001B[33m";

    /** Color rojo para la salida de texto en consola. */
    final static String red = "\u001B[31m";

    /** Restablece el color de la salida de texto en consola. */
    final static String reset = "\u001B[0m";

    /**
     * Determina los índices de los dos valores máximos en un arreglo de dobles.
     *
     * @param array Arreglo de dobles en el que se buscarán los dos máximos.
     * @return Una cadena que representa los índices de los dos valores máximos.
     */
    public static String wich2Max(double[] array) {
        double max = 0;
        double max2 = 0;
        int id = 0;
        int id2 = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max && max2 == 0) {
                max = array[i];
                id = i;
            } else if (array[i] > max) {
                id2 = id;
                id = i;
                max2 = max;
                max = array[i];
            } else if (array[i] > max2) {
                id2 = i;
                max2 = array[i];
            }
        }
        return "" + id + id2;
    }

    /**
     * Busca un valor en un arreglo de cadenas y devuelve su índice.
     *
     * @param value El valor a buscar.
     * @param array El arreglo en el que se realizará la búsqueda.
     * @return El índice del valor en el arreglo, o -1 si no se encuentra.
     */
    public static int in(String value, String[] array) {
        int i = 0;
        boolean found = false;
        while (i < array.length && !found) {
            if (array[i].equals(value)) {
                found = true;
            }
            i++;
        }
        if (!found) {
            i = -1;
        }
        return i;
    }

    /**
     * Solicita al usuario que elija una opción dentro de un rango específico.
     *
     * @param options Mensaje que muestra las opciones disponibles.
     * @param min El valor mínimo permitido.
     * @param max El valor máximo permitido.
     * @return La opción elegida por el usuario.
     */
    public static int choseOption(String options, int min, int max) {
        int response;
        Scanner scanner = new Scanner(System.in);
        System.out.println(options);
        response = scanner.nextInt();
        while (response < min || response > max) {
            System.out.println("La respuesta no es valida:");
            response = scanner.nextInt();
        }
        return response;
    }

    /**
     * Solicita al usuario que elija entre "S" (Sí) o "N" (No).
     *
     * @param options Mensaje que muestra las opciones disponibles.
     * @return La opción elegida por el usuario ("S" o "N").
     */
    public static String choseOptionYS(String options) {
        String str;
        Scanner scanner = new Scanner(System.in);
        System.out.println(options);
        str = scanner.next();
        while (!str.equalsIgnoreCase("S") && !str.equalsIgnoreCase("N")) {
            System.out.println("La respuesta no es valida:");
            str = scanner.next();
        }
        return str;
    }
    /**
     * Busca un objeto Player en un arreglo de Players y devuelve su índice.
     *
     * @param value El jugador a buscar.
     * @param array El arreglo de jugadores donde se realizará la búsqueda.
     * @return El índice del jugador en el arreglo, o -1 si no se encuentra.
     */
    public static int in(Player value, Player[] array) {
        int i = 0;
        boolean found = false;
        while (i < array.length && !found) {
            if (array[i].equals(value)) {
                found = true;
            }
            i++;
        }
        if (!found) {
            i = -1;
        }
        return i;
    }

    /**
     * Formatea un número decimal a un número de decimales específico.
     *
     * @param d El número a formatear.
     * @param deci El número de decimales que se desea.
     * @return El número formateado como un valor double.
     */
    public static double format(double d, int deci) {
        DecimalFormat formato = new DecimalFormat("#." + "0".repeat(deci));
        String str = formatString(formato.format(d));
        return Double.parseDouble(str);
    }

    /**
     * Reemplaza las comas en una cadena de texto por puntos.
     *
     * @param str La cadena que se desea formatear.
     * @return La cadena formateada.
     */
    private static String formatString(String str) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ',') {
                res.append(str.charAt(i));
            } else {
                res.append('.');
            }
        }
        return res.toString();
    }

    /**
     * Genera una cadena a partir de dos números, asegurándose de que tengan al menos un dígito.
     *
     * @param n El primer número para generar la cadena.
     * @param s El segundo número para generar la cadena.
     * @return La cadena generada a partir de los números proporcionados.
     */
    public static String generateString(int n, int s) {
        String name = "";
        String surname = "";
        if (n % 10 >= 1) {
            name += n;
        } else {
            name += 0 + n;
        }
        if (s % 10 >= 1) {
            surname += s;
        } else {
            surname += 0 + s;
        }
        return name + surname;
    }

    /**
     * Elimina un jugador de un arreglo de jugadores.
     *
     * @param player El jugador que se desea eliminar.
     * @param array El arreglo de jugadores del que se eliminará el jugador.
     */
    public static void remove(Player player, Player[] array) {
        for (Player comp : array) {
            if (player.getId() == comp.getId()) {
                comp = null;
            }
        }
    }

    /**
     * Elige un número aleatorio diferente de un número dado dentro de un rango específico.
     *
     * @param min El valor mínimo del rango.
     * @param max El valor máximo del rango.
     * @param not El número que no se debe elegir.
     * @return Un número aleatorio diferente de 'not' dentro del rango.
     */
    public static int choseDif(int min, int max, int not) {
        Random random = new Random();
        int chose = not;
        while (chose == not) {
            chose = random.nextInt(max - min + 1) + min;
        }
        return chose;
    }

    /**
     * Devuelve las posiciones ofensivas disponibles.
     *
     * @return Un arreglo de cadenas con las posiciones ofensivas.
     */
    public static String[] getPosOfe() {
        return posOfe;
    }

    /**
     * Devuelve las posiciones defensivas disponibles.
     *
     * @return Un arreglo de cadenas con las posiciones defensivas.
     */
    public static String[] getPosDef() {
        return posDef;
    }

    /**
     * Devuelve las posiciones de mediocampo disponibles.
     *
     * @return Un arreglo de cadenas con las posiciones de mediocampo.
     */
    public static String[] getPosMid() {
        return posMid;
    }

    /**
     * Devuelve los estilos de juego disponibles.
     *
     * @return Un arreglo de cadenas con los estilos de juego.
     */
    public static String[] getStyles() {
        return styles;
    }

    /**
     * Devuelve el color verde para la salida de texto en consola.
     *
     * @return Una cadena que representa el color verde.
     */
    public static String getGreen() {
        return green;
    }

    /**
     * Devuelve el color azul para la salida de texto en consola.
     *
     * @return Una cadena que representa el color azul.
     */
    public static String getBlue() {
        return blue;
    }

    /**
     * Devuelve el color amarillo para la salida de texto en consola.
     *
     * @return Una cadena que representa el color amarillo.
     */
    public static String getYellow() {
        return yellow;
    }

    /**
     * Devuelve el color rojo para la salida de texto en consola.
     *
     * @return Una cadena que representa el color rojo.
     */
    public static String getRed() {
        return red;
    }

    /**
     * Restablece el color de la salida de texto en consola.
     *
     * @return Una cadena que representa el color de reinicio.
     */
    public static String getReset() {
        return reset;
    }

    /**
     * Devuelve una representación en cadena de un número coloreado según su valor relativo a un umbral.
     *
     * @param number El número a colorear.
     * @param x El umbral usado para determinar el color.
     * @return Una cadena que representa el número coloreado.
     */
    public static String getColoredNumber(double number, int x) {
        String result;
        if (number < 0.69 * x) {
            result = red + number + reset;
        } else if (number < 0.79 * x) {
            result = yellow + number + reset;
        } else if (number < 0.89 * x) {
            result = green + number + reset;
        } else {
            result = blue + number + reset;
        }
        return result;
    }

    /**
     * Aproxima un valor hacia arriba al siguiente entero.
     *
     * @param value El valor a aproximar.
     * @return El valor aproximado hacia arriba como un entero.
     */
    public static int aproxUp(double value) {
        if (value % 1 != 0) {
            value = (int) (value + (1 - (value % 1)));
        }
        return (int) value;
    }
}