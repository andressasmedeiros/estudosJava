package main.java;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
        Pessoas pessoa = new Pessoas();

        var option = -1;

        do {
            System.out.println("=== Escolha uma das opções ===");
            System.out.println("1- Cadastrar pessoa");
            System.out.println("2- Listar pessoas cadastradas");
            System.out.println("3- Buscar pessoa por nome ou características");
            System.out.println("4- Excluir pessoa");
            System.out.println("5- Gerar relatório");
            System.out.println("6- Sair");

            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Digite apenas números!");
                scanner.nextLine();
                continue;
            }

            switch (option) {
                case 1 -> pessoa.cadastrarPessoa();
                case 2 -> pessoa.listarPessoas();
                case 3 -> pessoa.buscarPessoas();
                case 4 -> pessoa.deletarPessoas();
                case 5 -> pessoa.relatorioPessoas();
                case 6 -> System.exit(6);
                default -> System.out.println("Opção inválida");
            }
        } while (option != 0);
    }
}
