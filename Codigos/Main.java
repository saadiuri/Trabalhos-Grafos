package Codigos;

public class Main {

    public static void main(String[] args) {

        int[] tamanhos = { 10, 100, 1000, 10000, 100000 };
        int grauMedio = 6;

        System.out.println("===== EXPERIMENTOS - IDENTIFICACAO DE PONTES =====\n");

        for (int V : tamanhos) {

            System.out.println("\n==============================");
            System.out.println("V = " + V);
            System.out.println("==============================");

            testarGrafo("Euleriano", GeradorGrafos.gerarEuleriano(V, grauMedio), V);
            testarGrafo("Semi-Euleriano", GeradorGrafos.gerarSemiEuleriano(V, grauMedio), V);
            testarGrafo("Nao Euleriano", GeradorGrafos.gerarNaoEuleriano(V, grauMedio), V);
        }

        // ===============================
        // TESTE DO FLEURY (APENAS V = 10)
        // ===============================

        System.out.println("\n\n===== TESTE DO FLEURY =====");

        int V = 10;
        int grauM = 4;

        Grafo gTeste = GeradorGrafos.gerarEuleriano(V, grauM);

        System.out.println("\nGrafo de teste:");
        gTeste.printGraph();

        // 🔵 Fleury com Tarjan
        System.out.println("\nFleury (Tarjan):");
        long inicio = System.currentTimeMillis();

        Grafo g1 = AlgoritmoFleury.copiarGrafo(gTeste);
        AlgoritmoFleury.encontrarCaminhoEuleriano(g1, true);

        long fim = System.currentTimeMillis();
        System.out.println("Tempo Fleury (Tarjan): " + (fim - inicio) + " ms");

        // 🔴 Fleury com Naive
        System.out.println("\nFleury (Naive):");
        inicio = System.currentTimeMillis();

        Grafo g2 = AlgoritmoFleury.copiarGrafo(gTeste);
        AlgoritmoFleury.encontrarCaminhoEuleriano(g2, false);

        fim = System.currentTimeMillis();
        System.out.println("Tempo Fleury (Naive): " + (fim - inicio) + " ms");
    }

    // ===============================
    // TESTE DOS GRAFOS
    // ===============================
    public static void testarGrafo(String tipo, Grafo g, int V) {

        System.out.println("\n--- " + tipo + " ---");
        System.out.println("Tipo detectado: " + g.tipoEuleriano());

        // 🔥 MOSTRA O GRAFO SÓ QUANDO FOR PEQUENO
        if (V == 10) {
            g.printGraph();
        }

        long inicio, fim;

        // TARJAN
        inicio = System.currentTimeMillis();
        IdentificadorPontes.encontrarPontesTarjan(g);
        fim = System.currentTimeMillis();

        System.out.println("Tarjan: " + (fim - inicio) + " ms");

        // NAIVE (evita explodir tempo)
        if (V <= 10000) {
            inicio = System.currentTimeMillis();
            IdentificadorPontes.encontrarPontesNaive(g);
            fim = System.currentTimeMillis();

            System.out.println("Naive: " + (fim - inicio) + " ms");
        } else {
            System.out.println("Naive: (ignorado - muito lento)");
        }
    }
}