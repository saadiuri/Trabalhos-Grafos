package Codigos;

import java.util.*;

public class GeradorGrafos {

    private static Random rand = new Random();

    // EULERIANO
    public static Grafo gerarEuleriano(int V, int grauMedio) {

        Grafo g = new Grafo(V);
        int alvo = (V * grauMedio) / 2;

        // ciclo base (garante conexidade e grau par)
        for (int i = 0; i < V - 1; i++) {
            g.addEdge(i, i + 1);
        }
        g.addEdge(V - 1, 0);

        int arestas = V;

        int tentativas = 0;

        // adiciona TRIÂNGULOS (mantém grau par)
        while (arestas + 3 <= alvo && tentativas < alvo * 5) {

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

            tentativas++;
        }

        return g;
    }

    // SEMI-EURELIANO
    public static Grafo gerarSemiEuleriano(int V, int grauMedio) {

        Grafo g = gerarEuleriano(V, grauMedio);

        // adiciona UMA aresta 2 ímpares
        for (int u = 0; u < V; u++) {
            for (int v = u + 1; v < V; v++) {

                if (!g.hasEdge(u, v)) {
                    g.addEdge(u, v);
                    return g;
                }
            }
        }

        return g;
    }

    // NAO EURELIANO
    public static Grafo gerarNaoEuleriano(int V, int grauMedio) {

        Grafo g = gerarEuleriano(V, grauMedio);

        int u1 = -1, v1 = -1, u2 = -1, v2 = -1;

        // encontra primeira aresta válida
        outer1: for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (!g.hasEdge(i, j)) {
                    u1 = i;
                    v1 = j;
                    break outer1;
                }
            }
        }

        // encontra segunda aresta válida com vértices diferentes
        outer2: for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {

                if (i != u1 && i != v1 && j != u1 && j != v1) {

                    if (!g.hasEdge(i, j)) {
                        u2 = i;
                        v2 = j;
                        break outer2;
                    }
                }
            }
        }

        // adiciona com garantia
        if (u1 != -1 && v1 != -1)
            g.addEdge(u1, v1);
        if (u2 != -1 && v2 != -1)
            g.addEdge(u2, v2);

        return g;
    }
}