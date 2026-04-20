//package Tp1.CodigoCorreto;

public class Main {

    public static void main(String[] args) {

        int[] tamanhos = { 10, 100, 1000, 10000, 100000 };
        int grauMedio = 170; // E = (V * grauMedio) / 2

        System.out.println("\nV = 10 Somente para vizualização fácil");

        for (int V : tamanhos) {

            System.out.println("\n==== V = " + V + " ====");

            long inicio, fim;
            Grafo g;

            // Euleriano
            inicio = System.currentTimeMillis();
            g = GeradorGrafos.gerarEuleriano(V, grauMedio);
            fim = System.currentTimeMillis();

            System.out.println("Euleriano: " + (fim - inicio) + " ms | " + g.tipoEuleriano());

            if (V == 10) {
                g.printGraph();
            }

            // Semi
            inicio = System.currentTimeMillis();
            g = GeradorGrafos.gerarSemiEuleriano(V, grauMedio);
            fim = System.currentTimeMillis();

            System.out.println("Semi: " + (fim - inicio) + " ms | " + g.tipoEuleriano());

            if (V == 10) {
                g.printGraph();
            }

            // Não
            inicio = System.currentTimeMillis();
            g = GeradorGrafos.gerarNaoEuleriano(V, grauMedio);
            fim = System.currentTimeMillis();

            System.out.println("Nao: " + (fim - inicio) + " ms | " + g.tipoEuleriano());

            if (V == 10) {
                g.printGraph();
            }
        }
    }
}