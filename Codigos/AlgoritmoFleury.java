 package Codigos;

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

    // inicio do fleury
    public static void encontrarCaminhoEuleriano(Grafo grafoOriginal, boolean usarTarjan) {
        
        String tipo = grafoOriginal.tipoEuleriano();

        //verificação inicial para saber se o grafo analisado nao é eureliano
        if (tipo.equals("Nao Euleriano")) {
            System.out.println("O grafo é Não Euleriano ou desconexo ou possui 3+ vértices ímpares.");
            return;
        }

        // cria a copia
        Grafo g = copiarGrafo(grafoOriginal);

        // escolhe o vértice inicial, vai escolher um impar se houver
        int v = 0; 
        if (tipo.equals("Semi-Euleriano")) {//verifica o retorno do tipo do grafo para escolher o vertice inicial
            for (int i = 0; i < g.V; i++) {
                if (g.grau(i) % 2 != 0) {
                    v = i;
                    break; // obriga a começar pelo vertice impar se for semi-euleriano
                }
            }
        }

        System.out.println("Encontrando caminho para grafo " + tipo);
        System.out.print("Caminho: ");
        
        int arestasRestantes = contarArestas(g);

        // enquanto houver arestas continua o processo
        while (arestasRestantes > 0) {
            
            int proximoV = -1;
            
            // pega a lista de vizinhos atuais do vértice v
            List<Integer> vizinhos = new ArrayList<>(g.adj[v]);

            // percorre os vizinhos para escolher a próxima aresta 
            for (int w : vizinhos) {
                
                // se só tem uma opção OU se a aresta não for ponte
                if (vizinhos.size() == 1 || !isPonte(g, v, w, usarTarjan)) {
                    proximoV = w;
                    break; 
                }
            }

            // Caminhar de v para w e eliminar aresta
            if (proximoV != -1) {
                // p nao imprimir a seta no fianal
                if (arestasRestantes > 1) {
                    System.out.print(v + " -> ");
                } else {
                    System.out.print(v + " -> " + proximoV);
                }
                
                // remove a aresta de ambos os lados no HashSet 
                g.adj[v].remove(proximoV);
                g.adj[proximoV].remove(v);
                
                v = proximoV; // Atualiza o vértice atual
                arestasRestantes--;
            } else {
                // prevenção de falhas 
                System.out.println("\n[ERRO] Caminho sem saída encontrado antes do fim.");
                break;
            }
        }
        System.out.println(); 
    }

   // verificação de ponte
    private static boolean isPonte(Grafo g, int u, int v, boolean usarTarjan) {
        
        List<IdentificadorPontes.Edge> pontes;
        
        // lista de pontes vindo do identificador de pontes
        if (usarTarjan) {
            pontes = IdentificadorPontes.encontrarPontesTarjan(g);
        } else {
            pontes = IdentificadorPontes.encontrarPontesNaive(g);
        }

        // verifica se  aresta está dentro dessa lista de pontes
        for (IdentificadorPontes.Edge ponte : pontes) {
            // Como o grafo é não-direcionado, a aresta pode vir como (u, v) ou (v, u)
            if ((ponte.u == u && ponte.v == v) || (ponte.u == v && ponte.v == u)) {
                return true; // Sim, é uma ponte!
            }
        }
        
        return false; // Não está na lista, é seguro atravessar.
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