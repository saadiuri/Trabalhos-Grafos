package Codigos;

public class Main {

    public static void main(String[] args) {

        int[] tamanhos = { 10 };
        int grauMedio = 6; // E = (V * grauMedio) / 2    limite grauMedio = 170

        System.out.println("===== EXPERIMENTOS - IDENTIFICACAO DE PONTES =====\n");

        for (int V : tamanhos) {

            System.out.println("\n==============================");
            System.out.println("V = " + V);
            System.out.println("==============================");

            testarGrafo("Euleriano", GeradorGrafos.gerarEuleriano(V, grauMedio), V);
            testarGrafo("Semi-Euleriano", GeradorGrafos.gerarSemiEuleriano(V, grauMedio), V);
            testarGrafo("Nao Euleriano", GeradorGrafos.gerarNaoEuleriano(V, grauMedio), V);
        }

        // TESTE DO FLEURY

        System.out.println("\n\n===== TESTE DO FLEURY =====");

        int V = 10;
        int grauM = 4;

        Grafo gTeste = GeradorGrafos.gerarEuleriano(V, grauM);

        System.out.println("\nGrafo de teste:");
        gTeste.printGraph();

        // Fleury usando Tarjan (ideal)
        System.out.println("\nFleury (usando Tarjan):");
        long inicio = System.currentTimeMillis();
        AlgoritmoFleury.encontrarCaminhoEuleriano(gTeste);
        long fim = System.currentTimeMillis();

        System.out.println("Tempo Fleury: " + (fim - inicio) + " ms");
    }
    // MÉTODO AUXILIA

    public static void testarGrafo(String tipo, Grafo g, int V) {

        System.out.println("\n--- " + tipo + " ---");
        System.out.println("Tipo detectado: " + g.tipoEuleriano());

        if (V == 10) {
            g.printGraph();
        }

        long inicio, fim;

        // TARJAN (sempre roda)
        inicio = System.currentTimeMillis();
        IdentificadorPontes.encontrarPontesTarjan(g);
        fim = System.currentTimeMillis();

        System.out.println("Tarjan: " + (fim - inicio) + " ms");

        // NAIVE (somente para grafos menores)
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