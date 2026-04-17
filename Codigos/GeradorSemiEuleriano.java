// OBS: Peguamos como referência e adaptamos para esse trabalho o algoritmo do site: https://algs4.cs.princeton.edu/41graph/GraphGenerator.java.html

import java.util.*;

public class GeradorSemiEuleriano {

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

    // GERADOR EULERIANO BASE
    public static Graph gerarEuleriano(int V) {
        int E = V * 2;
        Graph g = new Graph(V);
        Random rand = new Random();

        // ciclo base
        for (int i = 0; i < V - 1; i++) {
            g.addEdge(i, i + 1);
        }
        g.addEdge(V - 1, 0);

        int arestas = V;

        while (arestas + 3 <= E) {
            int a = rand.nextInt(V);
            int b = rand.nextInt(V);
            int c = rand.nextInt(V);

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

    // GERADOR SEMI-EULERIANO
    public static Graph gerar(int V) {
        Graph g = gerarEuleriano(V);
        Random rand = new Random();

        // adiciona 1 aresta → cria 2 ímpares
        while (true) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);

            if (u != v && !g.hasEdge(u, v)) {
                g.addEdge(u, v);
                break;
            }
        }

        return g;
    }

    // VERIFICA SEMI-EULERIANO
    public static boolean isSemiEuleriano(Graph g) {
        int impares = 0;

        for (int i = 0; i < g.V; i++) {
            if (g.grau(i) % 2 != 0)
                impares++;
        }

        return impares == 2;
    }

    public static void main(String[] args) {

        int[] testes = { 10, 100000 };

        for (int V : testes) {

            long ini = System.currentTimeMillis();

            Graph g = gerar(V);

            long fim = System.currentTimeMillis();

            System.out.println("\n=====================");
            System.out.println("V = " + V);
            System.out.println("Semi-Euleriano: " + isSemiEuleriano(g));
            System.out.println("Tempo: " + (fim - ini) + " ms");

            if (V == 10)
                g.printGraph();
        }
    }
}