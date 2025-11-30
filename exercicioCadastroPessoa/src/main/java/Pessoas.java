package main.java;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Pessoas {

    List<Pessoa> listaDePessoas = new ArrayList<>();
    private final static Scanner scanner = new Scanner(System.in);

    public void cadastrarPessoa() {
        var name = "";
        var cpf = "";
        LocalDate nascimento = null;

        while (name == null || name.isEmpty()) {
            System.out.println("Informe o nome:");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("O nome não pode ser vazio!");
            }
        }
        while (true) {
            System.out.println("Informe o CPF (somente números):");
            cpf = scanner.nextLine().trim();

            if (cpf.isEmpty()) {
                System.out.println("O CPF não pode ser vazio!");
                continue;
            }

            if (!cpf.matches("\\d+")) {
                System.out.println("O CPF deve conter apenas números!");
                continue;
            }

            if (cpf.length() != 11) {
                System.out.println("O CPF deve ter exatamente 11 dígitos!");
                continue;
            }

            break;
        }
        while (nascimento == null) {
            System.out.println("Informe a data de nascimento:");
            String dataInput = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                nascimento = LocalDate.parse(dataInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use DD/MM/AAAA.");
            }
        }
        Pessoa pessoa = new Pessoa(name, cpf, nascimento);
        listaDePessoas.add(pessoa);
        System.out.println("Pessoa cadastrada com sucesso!!");
        System.out.println(pessoa);
    }

    private void imprimirPessoasConsole(List<Pessoa> pessoas, String mensagemConsole) {
        pessoas.sort(Comparator.comparing(Pessoa::getName));
        System.out.println(mensagemConsole);
        for (Pessoa p : pessoas) {
            System.out.println(p);
        }
    }

    private int lerNumero(String mensagem) {
        while (true) {
            System.out.println(mensagem);
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.nextLine();
            }
        }
    }


    public void listarPessoas() {
        if (listaDePessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa encontrada.");
        } else {
            imprimirPessoasConsole(listaDePessoas,"=== Resultados da Busca ===" );
        }
    }

    public void buscarPessoas() {

        System.out.println("=== Deseja buscar por: ===");
        System.out.println("1 - Nome");
        System.out.println("2 - CPF");
        System.out.println("3 - Data de Nascimento");
        System.out.println("4 - ID");

        int option = lerNumero("Escolha uma opção:");

        List<Pessoa> resultados = new ArrayList<>();
        String termoBusca = "";
        switch (option) {
            case 1:
                System.out.print("Qual nome deseja buscar? \n");
                termoBusca = scanner.next();
                for (Pessoa p : listaDePessoas) {
                    if (p.getName().toLowerCase().contains(termoBusca.toLowerCase())) {
                        resultados.add(p);
                    }
                }
                break;
            case 2:
                System.out.print("Qual CPF deseja buscar? \n");
                termoBusca = scanner.next();
                for (Pessoa p : listaDePessoas) {
                    if (p.getCpf().equals(termoBusca)) {
                        resultados.add(p);
                    }
                }
                break;
            case 3:
                System.out.print("Qual data de nascimento deseja buscar (formato dd/MM/aaaa)? \n");
                termoBusca = scanner.next();
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataBusca = LocalDate.parse(termoBusca, formatter);

                    for (Pessoa p : listaDePessoas) {
                        if (p.getNascimento().equals(dataBusca)) {
                            resultados.add(p);
                        }
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de data inválido. Use DD/MM/AAAA.");
                }
                break;
            case 4:
                System.out.print("Qual ID deseja buscar? \n");
                int idBusca;
                try {
                    idBusca = Integer.parseInt(scanner.next());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida para ID. Digite apenas números.");
                    return;
                }

                for (Pessoa p : listaDePessoas) {
                    if (p.getIdExibicao() == idBusca) {
                        resultados.add(p);
                    }
                }
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }
        if (resultados.isEmpty()) {
            System.out.println("Nenhuma pessoa encontrada.");
        } else {
            imprimirPessoasConsole(resultados,"=== Resultados da Busca ===" );
        }
    }

    public void deletarPessoas() {
        System.out.println("=== Deseja buscar por: ===");
        System.out.println("1 - CPF");
        System.out.println("2 - ID");

        int option = lerNumero("Escolha uma opção:");

        List<Pessoa> resultados = new ArrayList<>();
        String termoBusca = "";
        int confirmarExclusao;
        boolean encontrou = false;
        boolean excluiu = false;

        switch (option) {
            case 1:
                System.out.print("Qual CPF deseja excluir? \n");
                termoBusca = scanner.next();
                for (Pessoa p : listaDePessoas) {
                    if (p.getCpf().equals(termoBusca)) {
                        encontrou = true;
                        confirmarExclusao = lerNumero("Deseja mesmo excluir?\n1 - SIM\n2 - NÃO");
                        if (confirmarExclusao == 1) {
                            resultados.add(p);
                            excluiu = true;
                        } else {
                            System.out.println("Pessoa não excluída");
                        }
                    }
                }
                break;
            case 2:
                System.out.print("Qual ID deseja excluir? \n");
                try {
                    termoBusca = scanner.next();
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida para ID. Digite apenas números.");
                    return;
                }

                for (Pessoa p : listaDePessoas) {
                    if (p.getIdExibicao() == Integer.parseInt(termoBusca)) {
                        encontrou = true;
                        confirmarExclusao = lerNumero("Deseja mesmo excluir?\n1 - SIM\n2 - NÃO");
                        if (confirmarExclusao == 1) {
                            resultados.add(p);
                            excluiu = true;
                        } else {
                            System.out.println("Pessoa não excluída!");
                        }
                    }
                }
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        for (Pessoa p : resultados) {
            listaDePessoas.remove(p);
        }

        if (!encontrou) {
            System.out.println("Nenhuma pessoa encontrada.");
        } else if (excluiu){
            System.out.println("Pessoa excluída com sucesso!!");
        }
    }

    public void relatorioPessoas() {
        String nomeArquivo = "relatórioPessoas.txt";
        Path caminho = Paths.get(nomeArquivo);
        String conteudo = "";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        conteudo += String.format("%-5s %-20s %-18s %-12s\n",
                "ID", "Nome", "CPF", "Nascimento");
        conteudo += "---------------------------------------------------------------\n";

        for (Pessoa p : listaDePessoas) {
            conteudo += String.format("%-5d %-20s %-18s %-12s\n",
                    p.getIdExibicao(),
                    p.getName(),
                    p.getCpf(),
                    p.getNascimento().format(df)
            );
        }

        try {
            Files.writeString(caminho, conteudo, StandardCharsets.UTF_8);
            System.out.println("Conteúdo gravado com sucesso em " + nomeArquivo);

            String textoDoArquivo = Files.readString(caminho, StandardCharsets.UTF_8);
            System.out.println("\n=== Conteúdo do arquivo ===");
            System.out.println(textoDoArquivo);

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao escrever no arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
