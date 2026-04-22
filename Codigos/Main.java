package Codigos;

public class Main {

    public static void main(String[] args) {

        int[] tamanhos = { 10, 100, 1000, 10000, 100000 };

        System.out.println("===== EXPERIMENTOS - IDENTIFICACAO DE PONTES =====\n");

        for (int V : tamanhos) {

            int grauMedio = Math.min(V, 10);

            System.out.println("\n==============================");
            System.out.println("V = " + V);
            System.out.println("Grau médio = " + grauMedio);
            System.out.println("==============================");

            Grafo gEuleriano = GeradorGrafos.gerarEuleriano(V, grauMedio);
            Grafo gSemiEuleriano = GeradorGrafos.gerarSemiEuleriano(V, grauMedio);
            Grafo gNaoEuleriano = GeradorGrafos.gerarNaoEuleriano(V, grauMedio);

            testarGrafo("Euleriano", gEuleriano, V);
            testarGrafo("Semi-Euleriano", gSemiEuleriano, V);
            testarGrafo("Nao Euleriano", gNaoEuleriano, V);

            // FLEURY (TODOS OS TAMANHOS)
            testarFleury(V, grauMedio);
        }
    }

    // FLEURY EXPERIMENTO
    public static void testarFleury(int V, int grauMedio) {

        System.out.println("\n--- Fleury ---");

        Grafo gTeste = GeradorGrafos.gerarEuleriano(V, grauMedio);

        long inicio, fim;

        // TARJAN
        inicio = System.currentTimeMillis();
        Grafo g1 = AlgoritmoFleury.copiarGrafo(gTeste);
        AlgoritmoFleury.encontrarCaminhoEuleriano(g1, true, V == 10);
        fim = System.currentTimeMillis();

        System.out.println("Fleury (Tarjan): " + (fim - inicio) + " ms");

        // NAIVE
        inicio = System.currentTimeMillis();
        Grafo g2 = AlgoritmoFleury.copiarGrafo(gTeste);
        AlgoritmoFleury.encontrarCaminhoEuleriano(g2, false, V == 10);
        fim = System.currentTimeMillis();

        System.out.println("Fleury (Naive): " + (fim - inicio) + " ms");
    }

    // TESTE DOS GRAFOS
    public static void testarGrafo(String tipo, Grafo g, int V) {

        System.out.println("\n--- " + tipo + " ---");
        System.out.println("Tipo detectado: " + g.tipoEuleriano());

        // VISUALIZAÇÃO SÓ EM V = 10
        if (V == 10) {
            g.printGraph();
        }

        long inicio, fim;

        // TARJAN
        inicio = System.currentTimeMillis();
        IdentificadorPontes.encontrarPontesTarjan(g);
        fim = System.currentTimeMillis();

        System.out.println("Tarjan: " + (fim - inicio) + " ms");

        // NAIVE
        System.out.println("Naive: executando...");

        inicio = System.currentTimeMillis();
        IdentificadorPontes.encontrarPontesNaive(g);
        fim = System.currentTimeMillis();

        System.out.println("Naive: " + (fim - inicio) + " ms");
    }
}