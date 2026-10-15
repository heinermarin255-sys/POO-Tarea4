package nucleo;

import java.io.*;
import java.util.*;

class Registro implements PuntuacionComparable {
    String jugador;
    int puntaje;
    String detalle;
    String juego;
    boolean menosEsMejor;

    public Registro(String jugador, int puntaje, String detalle, String juego, boolean menosEsMejor) {
        this.jugador = jugador;
        this.puntaje = puntaje;
        this.detalle = detalle;
        this.juego = juego;
        this.menosEsMejor = menosEsMejor;
    }

    @Override
    public int compara(PuntuacionComparable otra) {
        if (menosEsMejor) {
            return Integer.compare(otra.getValor(), this.puntaje);
        } else {
            return Integer.compare(this.puntaje, otra.getValor());
        }
    }

    @Override
    public String getJugador() { return jugador; }

    @Override
    public int getValor() { return puntaje; }

    @Override
    public String getDetalle() { return detalle; }

    @Override
    public String getNombreJuego() { return juego; }
}

public class RegistroEstadisticas {
    private static final String ARCHIVO = "estadisticas.txt";

    public static void guardarPuntaje(PuntuacionComparable p) {
        if (p == null) return;
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(p.getNombreJuego() + "|" + p.getJugador() + "|" + p.getValor() + "|" + p.getDetalle());
        } catch (IOException e) {
            System.out.println("No se pudo guardar la estadistica.");
        }
    }

    public static void mostrarEstadisticas() {
        System.out.println("\n=== TABLA DE CLASIFICACION (TOP 3) ===");
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            System.out.println("Aun no hay records registrados.");
            return;
        }

        List<PuntuacionComparable> coliseo = new ArrayList<>();
        List<PuntuacionComparable> rescate = new ArrayList<>();
        List<PuntuacionComparable> ahorcado = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split("\\|");
                if (datos.length >= 4) {
                    try {
                        String juego = datos[0];
                        String jugador = datos[1];
                        int puntaje = Integer.parseInt(datos[2]);
                        String detalle = datos[3];

                        if (juego.equals("ColiseoVirtual") || juego.equals("CronoRex")) {
                            coliseo.add(new Registro(jugador, puntaje, detalle, juego, false));
                        } else if (juego.equals("RescateEspacial")) {
                            rescate.add(new Registro(jugador, puntaje, detalle, juego, true));
                        } else if (juego.equals("Ahorcado")) {
                            ahorcado.add(new Registro(jugador, puntaje, detalle, juego, false));
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error leyendo las estadisticas.");
        }

        Comparator<PuntuacionComparable> comp = (a, b) -> b.compara(a);
        Collections.sort(coliseo, comp);
        Collections.sort(rescate, comp);
        Collections.sort(ahorcado, comp);

        mostrarTop3("COLISEO VIRTUAL (Mejores Gladiadores)", coliseo, "Puntos");
        mostrarTop3("RESCATE ESPACIAL (Top Rescates)", rescate, "movs");
        mostrarTop3("AHORCADO (Mejores Adivinadores)", ahorcado, "letras adivinadas");
        System.out.println("==============================");
    }

    private static void mostrarTop3(String titulo, List<PuntuacionComparable> lista, String sufijoPuntaje) {
        System.out.println("\n-- " + titulo + " --");
        if (lista.isEmpty()) {
            System.out.println("Sin registros.");
            return;
        }
        int mostrados = 0;
        for (PuntuacionComparable r : lista) {
            System.out.println((mostrados + 1) + ". " + r.getJugador() + " -> " + r.getValor() + " " + sufijoPuntaje + " (" + r.getDetalle() + ")");
            mostrados++;
            if (mostrados >= 3) break;
        }
    }
}
