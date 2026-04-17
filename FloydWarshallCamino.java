import java.util.*;

public class FloydWarshallCamino {

    static final int V = 5; // puedes cambiar a 10 si quieres
    static final int INF = 99999;

    int[][] grafo = {
        {0, 3, INF, 7, INF},
        {3, 0, 1, INF, 8},
        {INF, 1, 0, 2, INF},
        {7, INF, 2, 0, 3},
        {INF, 8, INF, 3, 0}
    };

    void floydWarshall() {
        int[][] dist = new int[V][V];
        int[][] next = new int[V][V];

        // Inicialización
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = grafo[i][j];

                if (grafo[i][j] != INF && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }

        // Algoritmo de Floyd
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {

                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k]; // 🔥 clave para el camino
                    }
                }
            }
        }

        // Mostrar resultados
        imprimirCaminos(dist, next);
    }

    void imprimirCaminos(int[][] dist, int[][] next) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {

                if (i != j && dist[i][j] != INF) {
                    System.out.print("Distancia mínima " +
                            (char)('A' + i) + "→" + (char)('A' + j) +
                            " = " + dist[i][j] + ", Camino: ");

                    List<Integer> camino = reconstruirCamino(i, j, next);

                    for (int k = 0; k < camino.size(); k++) {
                        System.out.print((char)('A' + camino.get(k)));
                        if (k < camino.size() - 1) {
                            System.out.print(" → ");
                        }
                    }

                    System.out.println();
                }
            }
        }
    }

    List<Integer> reconstruirCamino(int u, int v, int[][] next) {
        if (next[u][v] == -1) return new ArrayList<>();

        List<Integer> camino = new ArrayList<>();
        camino.add(u);

        while (u != v) {
            u = next[u][v];
            camino.add(u);
        }

        return camino;
    }

    public static void main(String[] args) {
        FloydWarshallCamino f = new FloydWarshallCamino();
        f.floydWarshall();
    }
}