// package Tp1.CodigoCorreto;

import java.util.*;

public class Grafo {

    int V;
    HashSet<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Grafo(int V) {
        this.V = V;
        adj = new HashSet[V];

        for (int i = 0; i < V; i++) {
            adj[i] = new HashSet<>();
        }
    }

    public boolean addEdge(int u, int v) {
        if (u == v || adj[u].contains(v))
            return false;

        adj[u].add(v);
        adj[v].add(u);
        return true;
    }

    public boolean hasEdge(int u, int v) {
        return adj[u].contains(v);
    }

    public int grau(int v) {
        return adj[v].size();
    }

    // Verifica se é conexo
    private boolean isConexo() {
        boolean[] vis = new boolean[V];
        Queue<Integer> fila = new LinkedList<>();

        fila.add(0);
        vis[0] = true;

        while (!fila.isEmpty()) {
            int u = fila.poll();

            for (int v : adj[u]) {
                if (!vis[v]) {
                    vis[v] = true;
                    fila.add(v);
                }
            }
        }

        for (boolean v : vis) {
            if (!v)
                return false;
        }

        return true;
    }

    // Classificação Euleriana
    public String tipoEuleriano() {

        if (!isConexo())
            return "Nao Euleriano";

        int impares = 0;

        for (int i = 0; i < V; i++) {
            if (grau(i) % 2 != 0)
                impares++;
        }

        if (impares == 0)
            return "Euleriano";
        if (impares == 2)
            return "Semi-Euleriano";

        return "Nao Euleriano";
    }

    public void printGraph() {
        for (int i = 0; i < V; i++) {
            System.out.print(i + ": ");
            for (int v : adj[i]) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }
}