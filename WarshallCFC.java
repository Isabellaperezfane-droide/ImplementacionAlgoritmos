import java.util.*;

public class WarshallCFC {

    static final int V = 5; // puedes cambiar a 10

    int[][] grafo = {
        {0,1,0,0,0},
        {0,0,1,0,0},
        {1,0,0,0,0},
        {0,0,0,0,1},
        {0,0,0,1,0}
    };

    void warshall() {
        int[][] alcance = new int[V][V];

        // Copiar matriz original
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                alcance[i][j] = grafo[i][j];
            }
        }

        // Algoritmo de Warshall
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    alcance[i][j] = alcance[i][j] | (alcance[i][k] & alcance[k][j]);
                }
            }
        }

        System.out.println("Matriz de alcanzabilidad:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(alcance[i][j] + " ");
            }
            System.out.println();
        }

        encontrarCFC(alcance);
    }

    // Encontrar componentes fuertemente conexos
    void encontrarCFC(int[][] alcance) {
        boolean[] visitado = new boolean[V];

        System.out.println("\nComponentes fuertemente conexos:");

        for (int i = 0; i < V; i++) {
            if (!visitado[i]) {
                List<Character> componente = new ArrayList<>();

                for (int j = 0; j < V; j++) {
                    // Si i llega a j y j llega a i → mismo componente
                    if (alcance[i][j] == 1 && alcance[j][i] == 1) {
                        componente.add((char)('A' + j));
                        visitado[j] = true;
                    }
                }

                if (!componente.isEmpty()) {
                    System.out.println(componente);
                }
            }
        }
    }

    public static void main(String[] args) {
        WarshallCFC w = new WarshallCFC();
        w.warshall();
    }
}