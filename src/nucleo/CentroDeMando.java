package nucleo;

import pasatiempos.ColiseoVirtual;
import pasatiempos.RescateEspacial;
import pasatiempos.Ahorcado;
import java.util.Scanner;

public class CentroDeMando {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean enEjecucion = true;

        while (enEjecucion) {
            System.out.println("\n===============================");
            System.out.println("    CENTRO DE MANDO ARCADE     ");
            System.out.println("===============================");
            System.out.println("1. Lanzar Juego");
            System.out.println("2. Ver Estadisticas");
            System.out.println("3. Salir");
            System.out.println("===============================");
            System.out.print("Selecciona una opcion: ");
            
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    menuJuegos(scanner);
                    break;
                case "2":
                    RegistroEstadisticas.mostrarEstadisticas();
                    break;
                case "3":
                    System.out.println("Saliendo del Centro de Mando... ¡Hasta pronto!");
                    enEjecucion = false;
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta de nuevo.");
            }
        }
        scanner.close();
    }

    private static void menuJuegos(Scanner scanner) {
        boolean enMenuJuegos = true;
        while (enMenuJuegos) {
            System.out.println("\n--- CATALOGO DE ENTRETENIMIENTO ---");
            System.out.println("1. Coliseo Virtual (Combate RPG por turnos)");
            System.out.println("2. Rescate Espacial (Exploracion y Supervivencia)");
            System.out.println("3. Ahorcado (Adivina la palabra)");
            System.out.println("4. Volver al menu principal");
            System.out.print("Elige un juego: ");
            
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    Jugable coliseo = new ColiseoVirtual();
                    PuntuacionComparable p1 = coliseo.start(scanner);
                    RegistroEstadisticas.guardarPuntaje(p1);
                    break;
                case "2":
                    Jugable espacial = new RescateEspacial();
                    PuntuacionComparable p2 = espacial.start(scanner);
                    RegistroEstadisticas.guardarPuntaje(p2);
                    break;
                case "3":
                    Jugable ahorcado = new Ahorcado();
                    PuntuacionComparable p3 = ahorcado.start(scanner);
                    RegistroEstadisticas.guardarPuntaje(p3);
                    break;
                case "4":
                    enMenuJuegos = false;
                    break;
                default:
                    System.out.println("Juego no reconocido.");
            }
        }
    }
}
