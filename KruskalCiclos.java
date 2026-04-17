import java.util.*;

public class KruskalCiclos {

    static final int V = 10;
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
        int numAristas = 15 + rand.nextInt(6);

        for (int i = 0; i < numAristas; i++) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);

            if (u != v) {
                int peso = rand.nextInt(20) + 1;
                aristas.add(new Arista(u, v, peso));
            }
        }
    }

    // FIND (búsqueda con compresión de caminos)
    int find(int[] padre, int i) {
        if (padre[i] != i) {
            padre[i] = find(padre, padre[i]);
        }
        return padre[i];
    }

    // UNION (retorna true si une, false si hay ciclo)
    boolean union(int[] padre, int x, int y) {
        int raizX = find(padre, x);
        int raizY = find(padre, y);

        if (raizX == raizY) {
            return false; // ciclo detectado
        }

        padre[raizX] = raizY;
        return true;
    }

    void kruskal() {
        Collections.sort(aristas, Comparator.comparingInt(a -> a.peso));

        int[] padre = new int[V];
        for (int i = 0; i < V; i++) padre[i] = i;

        int costo = 0;
        int ciclosEvitados = 0;
        int aristasUsadas = 0;

        System.out.println("Aristas del MST:");

        for (Arista a : aristas) {
            if (union(padre, a.origen, a.destino)) {
                System.out.println(a.origen + " - " + a.destino + " : " + a.peso);
                costo += a.peso;
                aristasUsadas++;
            } else {
                ciclosEvitados++; // 🔥 aquí contamos los ciclos evitados
            }

            // MST completo cuando tiene V-1 aristas
            if (aristasUsadas == V - 1) break;
        }

        System.out.println("\nCosto total del MST: " + costo);
        System.out.println("Ciclos evitados: " + ciclosEvitados);
    }

    public static void main(String[] args) {
        KruskalCiclos g = new KruskalCiclos();

        g.generarGrafo();
        g.kruskal();
    }
}