# Juegos De Consola (Consola de Videojuegos en Java)

## Descripción
Este proyecto es una plataforma genérica de minijuegos para consola, diseñada utilizando patrones de diseño de programación orientada a objetos en Java.

La plataforma principal está compuesta por un **Centro de Mando** que permite al usuario seleccionar qué juego desea jugar y ver las estadísticas acumuladas de todos los jugadores.

## Arquitectura

El proyecto está dividido en dos paquetes principales para mantener la estructura ordenada y escalable:

### 1. `nucleo` (Core del Sistema)
Contiene las interfaces y el motor principal de la consola:
- **`CentroDeMando.java`**: El punto de entrada (`main`). Gestiona los menús de selección y la ejecución polimórfica de los juegos.
- **`Jugable.java`**: Interfaz funcional. Todo juego de la consola debe implementar esta interfaz, lo cual fuerza la creación del método `start(Scanner)` que retornará la puntuación final de la partida.
- **`PuntuacionComparable.java`**: Interfaz que representa una estadística final de un juego. Contiene el método `compara()` para que cada juego defina dinámicamente cómo se evalúan sus puntajes (ej: en carreras menos tiempo es mejor, pero en combates más vida es mejor).
- **`RegistroEstadisticas.java`**: Lógica estática para persistir los récords en `estadisticas.txt` de manera transparente y recuperar los Top 3 mejores jugadores.

### 2. `pasatiempos` (Juegos Implementados)
Contiene los juegos en sí. Cada clase aquí implementa `Jugable`.
- **`ColiseoVirtual.java`**: Combate RPG por turnos contra un jefe. Gana el que logre derrotar al jefe y el puntaje es la vida restante.
- **`RescateEspacial.java`**: Juego de navegación y exploración. El objetivo es llegar a una cápsula evadiendo asteroides en la menor cantidad de movimientos.
- **`Ahorcado.java`**: Juego clásico de adivinar palabras, integrado a la plataforma.

## Cómo Ejecutar
1. Abre una terminal en la ruta del proyecto.
2. Navega a la carpeta `src`:
   ```bash
   cd src
   ```
3. Compila los archivos:
   ```bash
   javac nucleo/*.java pasatiempos/*.java
   ```
4. Ejecuta el Centro de Mando:
   ```bash
   java nucleo.CentroDeMando
   ```
