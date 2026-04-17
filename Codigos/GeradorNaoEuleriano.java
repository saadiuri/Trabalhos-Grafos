// OBS: Peguamos como referência e adaptamos para esse trabalho o algoritmo do site: https://algs4.cs.princeton.edu/41graph/GraphGenerator.java.html

import java.util.*;

public class GeradorNaoEuleriano {

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

    // GERADOR NÃO EULERIANO
    public static Graph gerar(int V) {

        Graph g = new Graph(V);

        // 1. cria ciclo base (conectado)
        for (int i = 0; i < V - 1; i++) {
            g.addEdge(i, i + 1);
        }
        g.addEdge(V - 1, 0);

        Random rand = new Random();

        // 2. quebra a paridade (cria vários graus ímpares)
        int add = V / 2;

        for (int i = 0; i < add; i++) {

            int u = rand.nextInt(V);
            int v = rand.nextInt(V);

            if (u != v) {
                g.addEdge(u, v);
            }
        }

        return g;
    }

    // VERIFICA NÃO EULERIANO
    public static boolean isNaoEuleriano(Graph g) {

        int impares = 0;

        for (int i = 0; i < g.V; i++) {
            if (g.grau(i) % 2 != 0)
                impares++;
        }

        return impares > 2;
    }

    // MAIN
    public static void main(String[] args) {

        int[] testes = { 10, 100000 };

        for (int V : testes) {

            long ini = System.currentTimeMillis();

            Graph g = gerar(V);

            long fim = System.currentTimeMillis();

            System.out.println("\n=====================");
            System.out.println("V = " + V);
            System.out.println("Nao Euleriano: " + isNaoEuleriano(g));
            System.out.println("Tempo: " + (fim - ini) + " ms");

            if (V == 10)
                g.printGraph();
        }
    }
}