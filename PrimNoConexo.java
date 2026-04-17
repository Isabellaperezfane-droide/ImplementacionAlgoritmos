import java.util.*;

public class PrimNoConexo {

    static final int V = 10;
    static final int INF = Integer.MAX_VALUE;

    int[][] matriz = new int[V][V];

    // Generar grafo NO completo (puede quedar desconectado)
    void generarGrafo() {
        Random rand = new Random();
        int numAristas = 8 + rand.nextInt(5); // menos aristas → posible desconexión

        for (int i = 0; i < numAristas; i++) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);

            if (u != v && matriz[u][v] == 0) {
                int peso = rand.nextInt(20) + 1;
                matriz[u][v] = peso;
                matriz[v][u] = peso;
            }
        }
    }

    void mostrarMatriz() {
        System.out.println("Matriz de Adyacencia:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // PRIM con verificación de conectividad
    void prim() {
        boolean[] visitado = new boolean[V];
        int[] key = new int[V];

        Arrays.fill(key, INF);
        key[0] = 0;

        int costo = 0;

        for (int i = 0; i < V; i++) {
            int u = -1;

            for (int j = 0; j < V; j++) {
                if (!visitado[j] && (u == -1 || key[j] < key[u])) {
                    u = j;
                }
            }

            // 🔴 Si no se encuentra un nodo alcanzable → NO es conexo
            if (key[u] == INF) {
                System.out.println("\nEl grafo no tiene árbol de expansión mínima");
                return;
            }

            visitado[u] = true;
            costo += key[u];

            for (int v = 0; v < V; v++) {
                if (matriz[u][v] != 0 && !visitado[v] && matriz[u][v] < key[v]) {
                    key[v] = matriz[u][v];
                }
            }
        }

        System.out.println("\nCosto del MST (Prim): " + costo);
    }

    public static void main(String[] args) {
        PrimNoConexo g = new PrimNoConexo();

        g.generarGrafo();
        g.mostrarMatriz();

        g.prim();
    }
}