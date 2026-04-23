
//package Codigos;

import java.util.*;

//codigo baseado no slide 10-Grafos Hamiltonianos e Eurelianos - Fleury.pdf, pagina 25
public class AlgoritmoFleury {

    // para copiar o grafo original
    public static Grafo copiarGrafo(Grafo original) {
        Grafo copia = new Grafo(original.V);
        for (int i = 0; i < original.V; i++) {
            for (int vizinho : original.adj[i]) {
                copia.addEdge(i, vizinho);
            }
        }
        return copia;
    }

    // inicio do fleury ALTEREI SOMENTE ESSA PARTE DO CÓDIGO
    public static void encontrarCaminhoEuleriano(Grafo grafoOriginal, boolean usarTarjan, boolean imprimirCaminho) {

        String tipo = grafoOriginal.tipoEuleriano();

        if (tipo.equals("Nao Euleriano")) {
            Logger.println("O grafo é Não Euleriano ou desconexo ou possui 3+ vértices ímpares.");
            return;
        }

        Grafo g = copiarGrafo(grafoOriginal);

        int v = 0;
        if (tipo.equals("Semi-Euleriano")) {
            for (int i = 0; i < g.V; i++) {
                if (g.grau(i) % 2 != 0) {
                    v = i;
                    break;
                }
            }
        }

        Logger.println("Encontrando caminho para grafo " + tipo);

        // ✔ só imprime se permitido
        if (imprimirCaminho) {
            Logger.print("Caminho: ");
        }

        int arestasRestantes = contarArestas(g);

        while (arestasRestantes > 0) {

            int proximoV = -1;

            List<Integer> vizinhos = new ArrayList<>(g.adj[v]);

            for (int w : vizinhos) {

                if (vizinhos.size() == 1 || !isPonte(g, v, w, usarTarjan)) {
                    proximoV = w;
                    break;
                }
            }

            if (proximoV != -1) {

                // ✔ só imprime se habilitado
                if (imprimirCaminho) {
                    if (arestasRestantes > 1) {
                        Logger.print(v + " -> ");
                    } else {
                        Logger.print(v + " -> " + proximoV);
                    }
                }

                g.adj[v].remove(proximoV);
                g.adj[proximoV].remove(v);

                v = proximoV;
                arestasRestantes--;

            } else {
                Logger.println("\n[ERRO] Caminho sem saída encontrado antes do fim.");
                break;
            }
        }

        if (imprimirCaminho) {
            System.out.println();
        }
    }

    // verificação de ponte
   private static boolean isPonte(Grafo g, int u, int v, boolean usarTarjan) {

        if (usarTarjan) {
            // TARJAN: É muito rápido, então podemos pegar a lista completa de pontes
            List<IdentificadorPontes.Edge> pontes = IdentificadorPontes.encontrarPontesTarjan(g);
            
            for (IdentificadorPontes.Edge ponte : pontes) {
                if ((ponte.u == u && ponte.v == v) || (ponte.u == v && ponte.v == u)) {
                    return true; 
                }
            }
            return false;
            
        } else {
            // NAÏVE: Testa SOMENTE a aresta específica (u, v), sem recalcular o grafo todo!
            int countAntes = contarAlcancaveis(g, u);

            // remove aresta temporariamente
            g.adj[u].remove(v);
            g.adj[v].remove(u);

            int countDepois = contarAlcancaveis(g, u);

            // restaura aresta
            g.addEdge(u, v);

            return countAntes > countDepois;
        }
    }

    private static int contarAlcancaveis(Grafo g, int inicio) {
        boolean[] visitado = new boolean[g.V];
        int count = 0;
        Queue<Integer> fila = new LinkedList<>();

        fila.add(inicio);
        visitado[inicio] = true;

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

    // Função para saber quantas arestas tem no total
    private static int contarArestas(Grafo g) {
        int total = 0;
        for (int i = 0; i < g.V; i++) {
            total += g.grau(i);
        }
        return total / 2;
    }
}