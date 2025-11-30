package main.java;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimuladorIntegracaoSistemaCadastroPessoa {
    private int contagemAteAcabar = 0;
    private static final String API_URL = "https://api.invertexto.com/v1/faker?token=23256%7CZDlW9jRjinmC4Wt7m4GEMFiUrtYa7vST";

    public List<Pessoa> consultarPessoas(int quantidade) {
        if (quantidade < 1 || quantidade > 1000) {
            throw new IllegalArgumentException("Quantidade deve entre 1 e 1000 pessoas.");
        }

        contagemAteAcabar++;

        if (contagemAteAcabar == 2) {
            return new ArrayList<>();
        }

        List<Pessoa> pessoas = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            try {
                String[] dados = buscarDadosAPI();
                String nome = dados[0];
                String cpf = dados[1].replaceAll("[.-]", "");
                LocalDate nascimento = LocalDate.parse(dados[2]);

                pessoas.add(new Pessoa(nome, cpf, nascimento));
            } catch (Exception e) {
                System.err.println("Erro ao buscar dados da API: " + e.getMessage());
            }
        }

        return pessoas;
    }

    private String[] buscarDadosAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        String nome = extrairValor(json, "name");
        String cpf = extrairValor(json, "cpf");
        String nascimento = extrairValor(json, "birth_date");

        return new String[]{nome, cpf, nascimento};
    }

    private String extrairValor(String json, String campo) {
        String busca = "\"" + campo + "\":\"";
        int inicio = json.indexOf(busca) + busca.length();
        int fim = json.indexOf("\"", inicio);
        return json.substring(inicio, fim);
    }
}