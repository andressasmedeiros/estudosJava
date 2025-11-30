import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Pessoa {
    private UUID id;
    private String name;
    private String cpf;
    private LocalDate nascimento;
    private int idExibicao;

    public Pessoa(String name, String cpf, LocalDate nascimento) {
        this.id = UUID.randomUUID();
        this.idExibicao = GeradorIdExibicao.gerar();
        this.name = name;
        this.cpf = cpf;
        this.nascimento = nascimento;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public UUID getId() {
        return id;
    }

    public int getIdExibicao() {
        return idExibicao;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Pessoa{" +
                "\n id = '" + idExibicao + '\'' +
                "\n name='" + name + '\'' +
                ",\n cpf='" + cpf + '\'' +
                ",\n nascimento='" + formatter.format(nascimento) + '\'' +
                ",\n UUID ='" + id + '\'' +
                "\n}";
    }
}
