// OBS: Peguamos como referência e adaptamos para esse trabalho o algoritmo do site: https://algs4.cs.princeton.edu/41graph/GraphGenerator.java.html

import java.util.*;

public class GeradorEuleriano {

    static class Graph {
        int V;
        HashSet<Integer>[] adj;

        @SuppressWarnings("unchecked")
        public Graph(int V) {
            this.V = V;
            adj = new HashSet[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new HashSet<>();
            }
        }

        public void addEdge(int u, int v) {
            if (u != v && !adj[u].contains(v)) {
                adj[u].add(v);
                adj[v].add(u);
            }
        }

        public boolean hasEdge(int u, int v) {
            return adj[u].contains(v);
        }

        public int grau(int v) {
            return adj[v].size();
        }

        public void printGraph() {
            for (int i = 0; i < V; i++) {
                System.out.print(i + ": ");
                for (int x : adj[i]) {
                    System.out.print(x + " ");
                }
                System.out.println();
            }
        }
    }

    // GERADOR EULERIANO
    public static Graph gerar(int V) {
        int E = V * 2;
        Graph g = new Graph(V);
        Random rand = new Random();

        // 1. ciclo base (todos grau 2)
        for (int i = 0; i < V - 1; i++) {
            g.addEdge(i, i + 1);
        }
        g.addEdge(V - 1, 0);

        int arestas = V;

        // 2. adiciona CICLOS pequenos (mantém grau par)
        while (arestas + 2 <= E) {

            int a = rand.nextInt(V);
            int b = rand.nextInt(V);
            int c = rand.nextInt(V);

            // precisa ser ciclo válido
            if (a != b && b != c && a != c &&
                    !g.hasEdge(a, b) &&
                    !g.hasEdge(b, c) &&
                    !g.hasEdge(c, a)) {

                g.addEdge(a, b);
                g.addEdge(b, c);
                g.addEdge(c, a);

                arestas += 3;
            }
        }

        return g;
    }

    // VERIFICAÇÃO
    public static boolean isEuleriano(Graph g) {

        // 1. todos graus pares
        for (int i = 0; i < g.V; i++) {
            if (g.grau(i) % 2 != 0)
                return false;
        }

        // 2. conexo
        boolean[] vis = new boolean[g.V];
        Queue<Integer> fila = new LinkedList<>();

        fila.add(0);
        vis[0] = true;

        while (!fila.isEmpty()) {
            int u = fila.poll();
            for (int v : g.adj[u]) {
                if (!vis[v]) {
                    vis[v] = true;
                    fila.add(v);
                }
            }
        }

        for (int i = 0; i < g.V; i++) {
            if (!vis[i])
                return false;
        }

        return true;
    }

    public static void main(String[] args) {

        int[] testes = { 10, 100000 };

        for (int V : testes) {
            long ini = System.currentTimeMillis();

            Graph g = gerar(V);

            long fim = System.currentTimeMillis();

            System.out.println("\n=====================");
            System.out.println("V = " + V);
            System.out.println("Euleriano: " + isEuleriano(g));
            System.out.println("Tempo: " + (fim - ini) + " ms");

            if (V == 10)
                g.printGraph();
        }
    }
}