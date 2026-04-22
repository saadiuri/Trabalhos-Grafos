package Codigos;
import java.util.*;

public class IdentificadorPontes {

    // MÉTODO NAÏVE
    public static List<Edge> encontrarPontesNaive(Grafo g) {
        List<Edge> pontes = new ArrayList<>();

        // Testar cada aresta se é ponte
        for (int u = 0; u < g.V; u++) {

            // CORREÇÃO: iterar sobre uma cópia
            for (int v : new ArrayList<>(g.adj[u])) {

                if (u < v) { // Evitar duplicatas
                    if (isPonteNaive(g, u, v)) {
                        pontes.add(new Edge(u, v));
                    }
                }
            }
        }
        return pontes;
    }

    private static boolean isPonteNaive(Grafo g, int u, int v) {

        // Conta vértices alcançáveis antes da remoção
        int countAntes = contarAlcancaveis(g, u);

        // CORREÇÃO: remover por valor (não índice)
        g.adj[u].remove(Integer.valueOf(v));
        g.adj[v].remove(Integer.valueOf(u));

        // Conta após remoção
        int countDepois = contarAlcancaveis(g, u);

        // Restaura aresta
        g.addEdge(u, v);

        // Se reduziu o número de vértices alcançáveis, então é ponte
        return countAntes > countDepois;
    }

    private static int contarAlcancaveis(Grafo g, int inicio) {
        boolean[] visitado = new boolean[g.V];
        Queue<Integer> fila = new LinkedList<>();

        fila.add(inicio);
        visitado[inicio] = true;

        int count = 0;

        while (!fila.isEmpty()) {
            int atual = fila.poll();
            count++;

            for (int vizinho : g.adj[atual]) {
                if (!visitado[vizinho]) {
                    visitado[vizinho] = true;
                    fila.add(vizinho);
                }
            }
        }

        return count;
    }

    // MÉTODO DE TARJAN

    public static List<Edge> encontrarPontesTarjan(Grafo g) {
        List<Edge> pontes = new ArrayList<>();

        int[] disc = new int[g.V];
        int[] low = new int[g.V];
        boolean[] visitado = new boolean[g.V];
        int[] tempo = new int[1];

        for (int i = 0; i < g.V; i++) {
            if (!visitado[i]) {
                dfsTarjan(g, i, -1, disc, low, visitado, tempo, pontes);
            }
        }

        return pontes;
    }

    private static void dfsTarjan(Grafo g, int u, int parent,
            int[] disc, int[] low,
            boolean[] visitado,
            int[] tempo,
            List<Edge> pontes) {

        visitado[u] = true;
        disc[u] = low[u] = ++tempo[0];

        for (int v : g.adj[u]) {

            if (!visitado[v]) {

                dfsTarjan(g, v, u, disc, low, visitado, tempo, pontes);

                low[u] = Math.min(low[u], low[v]);

                // Condição de ponte
                if (low[v] > disc[u]) {
                    pontes.add(new Edge(u, v));
                }

            } else if (v != parent) {

                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    // CLASSE EDGE

    public static class Edge {
        int u, v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public String toString() {
            return "(" + u + ", " + v + ")";
        }
    }
}