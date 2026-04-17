import java.util.*;

public class GrafoPonderado {

    static final int V = 10; // número de vértices
    static final int INF = Integer.MAX_VALUE;

    int[][] matriz = new int[V][V];
    List<Arista> aristas = new ArrayList<>();

    static class Arista {
        int origen, destino, peso;

        Arista(int o, int d, int p) {
            origen = o;
            destino = d;
            peso = p;
        }
    }

    // Generar grafo aleatorio
    void generarGrafo() {
        Random rand = new Random();
        int numAristas = 15 + rand.nextInt(6); // entre 15 y 20

        for (int i = 0; i < numAristas; i++) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);

            if (u != v && matriz[u][v] == 0) {
                int peso = rand.nextInt(20) + 1;

                matriz[u][v] = peso;
                matriz[v][u] = peso;

                aristas.add(new Arista(u, v, peso));
            }
        }
    }

    // Mostrar matriz
    void mostrarMatriz() {
        System.out.println("Matriz de Adyacencia:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // PRIM
    int prim() {
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

            visitado[u] = true;
            costo += key[u];

            for (int v = 0; v < V; v++) {
                if (matriz[u][v] != 0 && !visitado[v] && matriz[u][v] < key[v]) {
                    key[v] = matriz[u][v];
                }
            }
        }

        return costo;
    }

    // KRUSKAL
    int kruskal() {
        Collections.sort(aristas, Comparator.comparingInt(a -> a.peso));

        int[] padre = new int[V];
        for (int i = 0; i < V; i++) padre[i] = i;

        int costo = 0;

        for (Arista a : aristas) {
            int u = find(padre, a.origen);
            int v = find(padre, a.destino);

            if (u != v) {
                costo += a.peso;
                union(padre, u, v);
            }
        }

        return costo;
    }

    int find(int[] padre, int i) {
        if (padre[i] == i) return i;
        return padre[i] = find(padre, padre[i]);
    }

    void union(int[] padre, int x, int y) {
        padre[find(padre, x)] = find(padre, y);
    }

    public static void main(String[] args) {
        GrafoPonderado g = new GrafoPonderado();

        g.generarGrafo();
        g.mostrarMatriz();

        int costoPrim = g.prim();
        int costoKruskal = g.kruskal();

        System.out.println("\nCosto MST con Prim: " + costoPrim);
        System.out.println("Costo MST con Kruskal: " + costoKruskal);

        if (costoPrim == costoKruskal) {
            System.out.println("✔ Ambos algoritmos coinciden.");
        } else {
            System.out.println("✘ Los resultados NO coinciden.");
        }
    }
}