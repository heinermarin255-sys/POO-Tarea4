package pasatiempos;

import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import nucleo.Jugable;
import nucleo.PuntuacionComparable;

public class Ahorcado implements Jugable {
    private static final String[] PALABRAS = {
        "JAVA", "CONSOLA", "VIDEOJUEGO", "ARQUITECTURA", "PROGRAMACION"
    };
    private static final int MAX_ERRORES = 6;

    @Override
    public PuntuacionComparable start(Scanner scanner) {
        String palabra = PALABRAS[new Random().nextInt(PALABRAS.length)];
        boolean[] adivinadas = new boolean[palabra.length()];
        StringBuilder letrasProbadas = new StringBuilder();
        int errores = 0;

        System.out.println("\n=== AHORCADO ===");
        System.out.println("Adivina la palabra. Tienes " + MAX_ERRORES + " errores disponibles.");

        while (errores < MAX_ERRORES && !palabraCompleta(adivinadas)) {
            mostrarPalabra(palabra, adivinadas);
            System.out.println("Letras probadas: " + (letrasProbadas.length() == 0 ? "ninguna" : letrasProbadas));
            System.out.print("Ingresa una letra: ");

            String entrada = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
            if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.out.println("Ingresa una sola letra.");
                continue;
            }

            char letra = entrada.charAt(0);
            if (letrasProbadas.indexOf(String.valueOf(letra)) >= 0) {
                System.out.println("Ya probaste esa letra.");
                continue;
            }
            letrasProbadas.append(letra).append(' ');

            boolean encontrada = false;
            for (int i = 0; i < palabra.length(); i++) {
                if (palabra.charAt(i) == letra) {
                    adivinadas[i] = true;
                    encontrada = true;
                }
            }

            if (encontrada) {
                System.out.println("¡Correcto!");
            } else {
                errores++;
                System.out.println("La letra no está en la palabra. Errores: " + errores + "/" + MAX_ERRORES);
            }
        }

        boolean victoria = palabraCompleta(adivinadas);
        if (victoria) {
            System.out.println("\n¡Ganaste! La palabra era " + palabra + ".");
        } else {
            mostrarPalabra(palabra, adivinadas);
            System.out.println("\nSe acabaron los intentos. La palabra era " + palabra + ".");
        }

        int letrasAdivinadas = 0;
        for (boolean adivinada : adivinadas) {
            if (adivinada) {
                letrasAdivinadas++;
            }
        }
        int puntaje = victoria ? letrasAdivinadas : 0;

        System.out.print("Ingresa tu nombre para los registros del ahorcado: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            nombre = "Anonimo";
        }

        final String jugador = nombre;
        final String detalle = (victoria ? "Victoria" : "Derrota")
            + " (" + letrasAdivinadas + " letras adivinadas)";
        final int valor = puntaje;

        return new PuntuacionComparable() {
            @Override
            public int compara(PuntuacionComparable otra) {
                return Integer.compare(getValor(), otra.getValor());
            }

            @Override
            public String getJugador() {
                return jugador;
            }

            @Override
            public int getValor() {
                return valor;
            }

            @Override
            public String getDetalle() {
                return detalle;
            }

            @Override
            public String getNombreJuego() {
                return "Ahorcado";
            }
        };
    }

    private static boolean palabraCompleta(boolean[] adivinadas) {
        for (boolean adivinada : adivinadas) {
            if (!adivinada) {
                return false;
            }
        }
        return true;
    }

    private static void mostrarPalabra(String palabra, boolean[] adivinadas) {
        System.out.print("Palabra: ");
        for (int i = 0; i < palabra.length(); i++) {
            System.out.print(adivinadas[i] ? palabra.charAt(i) : '_');
            System.out.print(' ');
        }
        System.out.println();
    }
}
