// package CodigoCorreto;

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
    public static void encontrarCaminhoEuleriano(Grafo grafoOriginal) {
        
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
                if (vizinhos.size() == 1 || !isPonteTemporario(g, v, w)) {
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

    // MÉTODO TEMPORÁRIO DE PONTE (Método Naïve simples), substuit qndo ponte estiver pronto
    private static boolean isPonteTemporario(Grafo g, int u, int v) {
        int countAntes = contarAlcancaveis(g, u);
        
        // remove temporariamente
        g.adj[u].remove(v);
        g.adj[v].remove(u);
        
        int countDepois = contarAlcancaveis(g, u);
        
        // devolve a aresta
        g.addEdge(u, v);
        
        return countAntes > countDepois;
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