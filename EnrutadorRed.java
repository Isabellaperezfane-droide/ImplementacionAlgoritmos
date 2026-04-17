import java.util.*;

public class EnrutadorRed {

    static final int V = 6;
    static final int INF = 0;

    int[][] red = {
        {0, 10, 5, 0, 0, 0},
        {10, 0, 2, 1, 0, 0},
        {5, 2, 0, 9, 2, 0},
        {0, 1, 9, 0, 4, 6},
        {0, 0, 2, 4, 0, 8},
        {0, 0, 0, 6, 8, 0}
    };

    void rutaMasRapida(int origen, int destino) {
        int[] capacidad = new int[V];
        boolean[] visitado = new boolean[V];
        int[] parent = new int[V];

        Arrays.fill(capacidad, -1);
        Arrays.fill(parent, -1);

        capacidad[origen] = Integer.MAX_VALUE;

        for (int i = 0; i < V; i++) {
            int u = -1;

            // Elegir el nodo con mayor capacidad
            for (int j = 0; j < V; j++) {
                if (!visitado[j] && (u == -1 || capacidad[j] > capacidad[u])) {
                    u = j;
                }
            }

            if (capacidad[u] == -1) break;

            visitado[u] = true;

            for (int v = 0; v < V; v++) {
                if (red[u][v] > 0 && !visitado[v]) {
                    int nuevaCap = Math.min(capacidad[u], red[u][v]);

                    if (nuevaCap > capacidad[v]) {
                        capacidad[v] = nuevaCap;
                        parent[v] = u;
                    }
                }
            }
        }

        // Si no hay ruta
        if (capacidad[destino] == -1) {
            System.out.println("No hay ruta posible entre los vértices");
            return;
        }

        // Reconstruir camino
        List<Integer> camino = new ArrayList<>();
        for (int v = destino; v != -1; v = parent[v]) {
            camino.add(v);
        }
        Collections.reverse(camino);

        // Mostrar resultado
        System.out.print("Ruta más rápida: ");
        for (int i = 0; i < camino.size(); i++) {
            char nodo = (char)('A' + camino.get(i));
            System.out.print(nodo);
            if (i < camino.size() - 1) {
                System.out.print(" → ");
            }
        }

        System.out.println(" (ancho de banda: " + capacidad[destino] + ")");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EnrutadorRed r = new EnrutadorRed();

        System.out.print("Ingrese nodo origen (0-5): ");
        int origen = sc.nextInt();

        System.out.print("Ingrese nodo destino (0-5): ");
        int destino = sc.nextInt();

        r.rutaMasRapida(origen, destino);
    }
}