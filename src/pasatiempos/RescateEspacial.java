package pasatiempos;

import java.util.Random;
import java.util.Scanner;
import nucleo.Jugable;

public class RescateEspacial implements Jugable {
    @Override
    public nucleo.PuntuacionComparable start(Scanner scanner) {
        System.out.println("\n=== RESCATE ESPACIAL ===");
        System.out.println("Eres la nave 'X'. Encuentra la capsula de escape 'O' en el campo de asteroides '*'.");
        System.out.println("Controles: [W]=Arriba, [S]=Abajo, [A]=Izquierda, [D]=Derecha | [Q]=Salir");
        
        int size = 8;
        int pX = 0;
        int pY = 0;
        
        Random r = new Random();
        int metaX = r.nextInt(size - 2) + 2; 
        int metaY = r.nextInt(size - 2) + 2;
        
        boolean jugando = true;
        int movimientos = 0;
        
        while (jugando) {
            // Dibujar mapa
            System.out.println("\nSector Espacial " + (movimientos + 1) + ":");
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (x == pX && y == pY) {
                        System.out.print("[X]");
                    } else if (x == metaX && y == metaY) {
                        System.out.print("[O]");
                    } else {
                        // Generar asteroides estaticos basados en una formula matematica con las coordenadas
                        if ((x * y + 3) % 7 == 0) {
                            System.out.print(" * ");
                        } else {
                            System.out.print(" . ");
                        }
                    }
                }
                System.out.println();
            }
            
            if (pX == metaX && pY == metaY) {
                System.out.println("\n¡MISION CUMPLIDA! Rescataste la capsula en " + movimientos + " movimientos.");
                jugando = false;
                return registrar(scanner, movimientos, "Victoria");
            }
            
            System.out.print("Mover (W/A/S/D) o [Q] para huir: ");
            String mov = scanner.nextLine().toUpperCase();
            
            if (mov.equals("Q")) {
                System.out.println("Mision abortada. Volviendo al centro de mando...");
                return registrar(scanner, movimientos, "Abandono");
            }
            
            int prevX = pX;
            int prevY = pY;
            
            if (mov.equals("W") && pY > 0) pY--;
            if (mov.equals("S") && pY < size - 1) pY++;
            if (mov.equals("A") && pX > 0) pX--;
            if (mov.equals("D") && pX < size - 1) pX++;
            
            // Verificar si caímos en un asteroide
            if ((pX * pY + 3) % 7 == 0 && (pX != metaX || pY != metaY)) {
                System.out.println("\n¡BOOM! Has chocado contra un asteroide. Nave destruida.");
                System.out.println("--- FIN DEL JUEGO ---");
                return registrar(scanner, 9999, "Destruido (" + movimientos + " movs)");
            } else if (prevX != pX || prevY != pY) {
                movimientos++;
            } else {
                System.out.println("Movimiento bloqueado o no valido.");
            }
        }
        return null;
    }

    private nucleo.PuntuacionComparable registrar(Scanner scanner, int movimientos, String detalle) {
        System.out.print("Ingresa tu nombre para el record: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) nombre = "Anonimo";
        
        final String fNombre = nombre;
        return new nucleo.PuntuacionComparable() {
            @Override
            public int compara(nucleo.PuntuacionComparable otra) {
                return Integer.compare(otra.getValor(), this.getValor()); // Menos es mejor
            }
            @Override
            public String getJugador() { return fNombre; }
            @Override
            public int getValor() { return movimientos; }
            @Override
            public String getDetalle() { return detalle; }
            @Override
            public String getNombreJuego() { return "RescateEspacial"; }
        };
    }
}
