package main.java;

public class GeradorIdExibicao {
    private static int contador = 1;

    public static int gerar() {
        return contador++;
    }
}