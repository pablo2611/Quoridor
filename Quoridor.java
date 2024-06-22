
// Clase Posicion
public class Posicion {
    private int x;
    private int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

// Clase Jugador
public class Jugador {
    private String nombre;
    private Posicion posicion;
    private int murosRestantes;

    public Jugador(String nombre, Posicion posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.murosRestantes = 10;
    }

    public String getNombre() {
        return nombre;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getMurosRestantes() {
        return murosRestantes;
    }

    public void usarMuro() {
        if (murosRestantes > 0) {
            murosRestantes--;
        }
    }

    @Override
    public String toString() {
        return nombre + " en " + posicion.toString();
    }
}

// Clase Muro
public class Muro {
    private Posicion posicionInicio;
    private Posicion posicionFin;

    public Muro(Posicion inicio, Posicion fin) {
        this.posicionInicio = inicio;
        this.posicionFin = fin;
    }

    public Posicion getPosicionInicio() {
        return posicionInicio;
    }

    public Posicion getPosicionFin() {
        return posicionFin;
    }
}

// Clase Tablero
import java.util.ArrayList;

public class Tablero {
    private int tamaño;
    private Jugador jugadorBlanco;
    private Jugador jugadorRojo;
    private ArrayList<Muro> muros;

    public Tablero(int tamaño) {
        this.tamaño = tamaño;
        this.jugadorBlanco = new Jugador("Blanco", new Posicion(tamaño / 2, tamaño / 2));
        this.jugadorRojo = new Jugador("Rojo", new Posicion(tamaño / 2, tamaño / 2));
        this.muros = new ArrayList<>();
    }

    public Jugador getJugadorBlanco() {
        return jugadorBlanco;
    }

    public Jugador getJugadorRojo() {
        return jugadorRojo;
    }

    public void moverJugador(Jugador jugador, Posicion nuevaPosicion) {
        jugador.setPosicion(nuevaPosicion);
    }

    public void colocarMuro(Jugador jugador, Muro muro) {
        if (jugador.getMurosRestantes() > 0) {
            muros.add(muro);
            jugador.usarMuro();
        }
    }

    public void imprimirTablero() {
        char[][] matriz = new char[tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j] = '.';
            }
        }

        matriz[jugadorBlanco.getPosicion().getX()][jugadorBlanco.getPosicion().getY()] = 'B';
        matriz[jugadorRojo.getPosicion().getX()][jugadorRojo.getPosicion().getY()] = 'R';

        for (Muro muro : muros) {
            matriz[muro.getPosicionInicio().getX()][muro.getPosicionInicio().getY()] = 'M';
            matriz[muro.getPosicionFin().getX()][muro.getPosicionFin().getY()] = 'M';
        }

        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}

// Clase Quoridor
import java.util.Scanner;

public class Quoridor {
    private Tablero tablero;
    private Scanner scanner;

    public Quoridor(int tamaño) {
        this.tablero = new Tablero(tamaño);
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Bienvenido a Quoridor!");
        boolean enJuego = true;
        Jugador jugadorActual = tablero.getJugadorBlanco();
        
        while (enJuego) {
            System.out.println("Turno de " + jugadorActual.getNombre());
            tablero.imprimirTablero();
            System.out.println("Moverse (M) o Colocar Muro (C) o Salir (EXIT): ");
            String accion = scanner.nextLine();

            if (accion.equalsIgnoreCase("EXIT")) {
                System.out.println("Partida interrumpida.");
                enJuego = false;
            } else if (accion.equalsIgnoreCase("M")) {
                System.out.println("Ingrese las coordenadas de destino (x y): ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                scanner.nextLine();
                tablero.moverJugador(jugadorActual, new Posicion(x, y));
            } else if (accion.equalsIgnoreCase("C")) {
                System.out.println("Ingrese las coordenadas del muro (x1 y1 x2 y2): ");
                int x1 = scanner.nextInt();
                int y1 = scanner.nextInt();
                int x2 = scanner.nextInt();
                int y2 = scanner.nextInt();
                scanner.nextLine();
                tablero.colocarMuro(jugadorActual, new Muro(new Posicion(x1, y1), new Posicion(x2, y2)));
            }

            jugadorActual = (jugadorActual == tablero.getJugadorBlanco()) ? tablero.getJugadorRojo() : tablero.getJugadorBlanco();
        }
    }

    public static void main(String[] args) {
        Quoridor juego = new Quoridor(9);
        juego.iniciar();
    }
}
