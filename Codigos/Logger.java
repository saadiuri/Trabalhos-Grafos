//package Codigos;

import java.io.*;

public class Logger {
    private static PrintWriter writer;

    // Inicializa o arquivo 
    public static void init(String filename) {
        try {
            writer = new PrintWriter(new FileWriter(filename, false)); // false para sobrescrever se já existir
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de log: " + e.getMessage());
        }
    }

    // Fecha o arquivo 
    public static void close() {
        if (writer != null) {
            writer.close();
        }
    }

    // Imprime com quebra de linha em arquivo e em terminal
    public static void println(String message) {
        System.out.println(message);
        if (writer != null) {
            writer.println(message);
            writer.flush(); 
        }
    }

    // Imprime sem quebra de linha 
    public static void print(String message) {
        System.out.print(message);
        if (writer != null) {
            writer.print(message);
            writer.flush();
        }
    }
} 
    

