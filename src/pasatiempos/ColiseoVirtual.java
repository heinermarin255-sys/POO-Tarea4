package pasatiempos;

import java.util.Random;
import java.util.Scanner;
import nucleo.Jugable;

public class ColiseoVirtual implements Jugable {
    @Override
    public nucleo.PuntuacionComparable start(Scanner scanner) {
        System.out.println("\n=== COLISEO VIRTUAL ===");
        System.out.println("Has entrado a la arena. ¡Un imponente Golem de Obsidiana se levanta frente a ti!");
        System.out.println("Tu objetivo: Destruirlo antes de que acabe contigo.");
        System.out.println("Este juego es de estrategia por turnos, ¡piensa bien tus movimientos!");
        
        int jugadorSalud = 100;
        int golemSalud = 100;
        int pociones = 3;
        int turnos = 0;
        Random random = new Random();
        boolean jugando = true;
        
        while (jugando && jugadorSalud > 0 && golemSalud > 0) {
            turnos++;
            System.out.println("\n--- TURNO " + turnos + " ---");
            System.out.println("Tu Salud: " + jugadorSalud + " HP | Pociones: " + pociones);
            System.out.println("Salud del Golem: " + golemSalud + " HP");
            System.out.println("¿Que deseas hacer?");
            System.out.println("1. Ataque Rapido (Daño seguro pero moderado)");
            System.out.println("2. Golpe Pesado (Puede hacer mucho daño o fallar miserablemente)");
            System.out.println("3. Beber Pocion (Recupera salud)");
            System.out.println("4. Huir como un cobarde");
            System.out.print("Tu eleccion: ");
            
            String accion = scanner.nextLine();
            int danoJugador = 0;
            
            switch (accion) {
                case "1":
                    danoJugador = random.nextInt(11) + 10; // 10 a 20 de daño
                    System.out.println(">>> Atacas rapidamente y causas " + danoJugador + " de daño.");
                    golemSalud -= danoJugador;
                    break;
                case "2":
                    if (random.nextBoolean()) { // 50% de probabilidad
                        danoJugador = random.nextInt(21) + 20; // 20 a 40 de daño
                        System.out.println(">>> ¡ZAS! Conectas un golpe critico de " + danoJugador + " de daño.");
                        golemSalud -= danoJugador;
                    } else {
                        System.out.println(">>> ¡El Golem esquivo tu ataque pesado! No haces daño.");
                    }
                    break;
                case "3":
                    if (pociones > 0) {
                        int cura = random.nextInt(11) + 25; // 25 a 35 de cura
                        jugadorSalud += cura;
                        if (jugadorSalud > 100) jugadorSalud = 100; // Cap a 100
                        pociones--;
                        System.out.println(">>> Te bebes una pocion y recuperas salud. Ahora tienes " + jugadorSalud + " HP. (Te quedan " + pociones + ")");
                    } else {
                        System.out.println(">>> ¡No te quedan pociones! Pierdes tu turno buscando en tu mochila vacia.");
                    }
                    break;
                case "4":
                    System.out.println(">>> Huyes despavorido del coliseo. ¡Que verguenza!");
                    jugando = false;
                    continue;
                default:
                    System.out.println(">>> Te quedas paralizado por el miedo. Pierdes tu turno.");
            }
            
            // Turno del Golem si sigue vivo
            if (golemSalud > 0 && jugando) {
                System.out.println("\n... El Golem ruge y contraataca ...");
                int danoGolem = random.nextInt(16) + 5; // 5 a 20 de daño
                jugadorSalud -= danoGolem;
                System.out.println("<<< El Golem te aplasta causandote " + danoGolem + " de daño.");
            }
        }
        
        if (!jugando) {
            return registrarEstadistica(scanner, 0, "Abandono");
        } else if (jugadorSalud <= 0) {
            System.out.println("\n--- HAS MUERTO ---");
            System.out.println("El Golem te redujo a polvo.");
            return registrarEstadistica(scanner, 0, "Derrota (Sobrevivio " + turnos + " turnos)");
        } else {
            System.out.println("\n--- ¡VICTORIA! ---");
            System.out.println("¡El Golem cae destruido en mil pedazos de obsidiana! Has ganado.");
            return registrarEstadistica(scanner, jugadorSalud, "Victoria (HP Restante: " + jugadorSalud + ")");
        }
    }
    
    private nucleo.PuntuacionComparable registrarEstadistica(Scanner scanner, int puntaje, String detalle) {
        System.out.print("\nIngresa tu nombre para los registros del coliseo: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) nombre = "Guerrero Anonimo";
        
        final String fNombre = nombre;
        
        return new nucleo.PuntuacionComparable() {
            @Override
            public int compara(nucleo.PuntuacionComparable otra) {
                return Integer.compare(this.getValor(), otra.getValor());
            }
            @Override
            public String getJugador() { return fNombre; }
            @Override
            public int getValor() { return puntaje; }
            @Override
            public String getDetalle() { return detalle; }
            @Override
            public String getNombreJuego() { return "ColiseoVirtual"; }
        };
    }
}
