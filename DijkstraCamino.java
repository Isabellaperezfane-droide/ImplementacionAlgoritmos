import java.util.*;

public class DijkstraCamino {

    static final int V = 10;
    static final int INF = Integer.MAX_VALUE;

    int[][] matriz = new int[V][V];

    // Generar grafo aleatorio
    void generarGrafo() {
        Random rand = new Random();
        int numAristas = 12 + rand.nextInt(6);

        for (int i = 0; i < numAristas; i++) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);

            if (u != v && matriz[u][v] == 0) {
                int peso = rand.nextInt(10) + 1;
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

    void dijkstra(int origen, int destino) {
        int[] dist = new int[V];
        boolean[] visitado = new boolean[V];
        int[] parent = new int[V];

        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);

        dist[origen] = 0;

        for (int i = 0; i < V; i++) {
            int u = -1;

            for (int j = 0; j < V; j++) {
                if (!visitado[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }

            if (dist[u] == INF) break;

            visitado[u] = true;

            for (int v = 0; v < V; v++) {
                if (matriz[u][v] != 0 && !visitado[v]) {
                    int nuevaDist = dist[u] + matriz[u][v];

                    if (nuevaDist < dist[v]) {
                        dist[v] = nuevaDist;
                        parent[v] = u; // 🔥 guardamos el camino
                    }
                }
            }
        }

        // Si no hay camino
        if (dist[destino] == INF) {
            System.out.println("\nNo hay ruta posible entre los vértices");
            return;
        }

        // Reconstruir camino
        List<Integer> camino = new ArrayList<>();
        for (int v = destino; v != -1; v = parent[v]) {
            camino.add(v);
        }
        Collections.reverse(camino);

        // Mostrar como A → B → C
        System.out.print("\nCamino más corto: ");
        for (int i = 0; i < camino.size(); i++) {
            char nodo = (char) ('A' + camino.get(i));
            System.out.print(nodo);
            if (i < camino.size() - 1) {
                System.out.print(" → ");
            }
        }

        System.out.println(" (costo " + dist[destino] + ")");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DijkstraCamino g = new DijkstraCamino();

        g.generarGrafo();
        g.mostrarMatriz();

        System.out.print("\nIngrese vértice origen (0-9): ");
        int origen = sc.nextInt();

        System.out.print("Ingrese vértice destino (0-9): ");
        int destino = sc.nextInt();

        g.dijkstra(origen, destino);
    }
}