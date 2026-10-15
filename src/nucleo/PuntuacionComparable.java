package nucleo;

public interface PuntuacionComparable {
    int compara(PuntuacionComparable otra);

    String getJugador();

    int getValor();

    String getDetalle();

    String getNombreJuego();
}
