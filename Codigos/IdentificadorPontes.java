//package Codigos;

import java.util.*;

public class IdentificadorPontes {

    // MÉTODO NAIVE
    public static List<Edge> encontrarPontesNaive(Grafo g) {
        List<Edge> pontes = new ArrayList<>();

        for (int u = 0; u < g.V; u++) {

            // iterar sobre cópia 
            for (int v : new ArrayList<>(g.adj[u])) {

                if (u < v) {
                    if (isPonteNaive(g, u, v)) {
                        pontes.add(new Edge(u, v));
                    }
                }
            }
        }
        return pontes;
    }

    private static boolean isPonteNaive(Grafo g, int u, int v) {

        int countAntes = contarAlcancaveis(g, u);

        // remove aresta corretamente
        g.adj[u].remove(Integer.valueOf(v));
        g.adj[v].remove(Integer.valueOf(u));

        int countDepois = contarAlcancaveis(g, u);

        // restaura aresta
        g.addEdge(u, v);

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

    // TARJAN ITERATIVO 
    public static List<Edge> encontrarPontesTarjan(Grafo g) {

        List<Edge> pontes = new ArrayList<>();

        int V = g.V;

        int[] disc = new int[V];
        int[] low = new int[V];
        int[] parent = new int[V];
        boolean[] visitado = new boolean[V];

        Arrays.fill(parent, -1);

        int tempo = 0;

        for (int i = 0; i < V; i++) {

            if (visitado[i])
                continue;

            Stack<Integer> stack = new Stack<>();
            Stack<Iterator<Integer>> itStack = new Stack<>();

            stack.push(i);
            itStack.push(g.adj[i].iterator());

            while (!stack.isEmpty()) {

                int u = stack.peek();

                // primeira visita
                if (!visitado[u]) {
                    visitado[u] = true;
                    disc[u] = low[u] = ++tempo;
                }

                Iterator<Integer> it = itStack.peek();

                if (it.hasNext()) {

                    int v = it.next();

                    if (!visitado[v]) {

                        parent[v] = u;

                        stack.push(v);
                        itStack.push(g.adj[v].iterator());

                    } else if (v != parent[u]) {

                        low[u] = Math.min(low[u], disc[v]);
                    }

                } else {

                    // backtracking
                    stack.pop();
                    itStack.pop();

                    if (parent[u] != -1) {

                        int p = parent[u];

                        low[p] = Math.min(low[p], low[u]);

                        if (low[u] > disc[p]) {
                            pontes.add(new Edge(p, u));
                        }
                    }
                }
            }
        }

        return pontes;
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
