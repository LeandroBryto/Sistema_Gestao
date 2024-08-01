package org.leandro.models;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;
    private String dataNascimento;
    private boolean whatsapp;

    // Construtor padrão
    public Cliente(int i, String nome, String cpf, String endereco, String telefone, String email) {
    }

    // Construtor com todos os atributos
    public Cliente(int id, String nome, String cpf, String endereco, String telefone, String email, String dataNascimento, boolean whatsapp) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.whatsapp = whatsapp;
    }

    // Construtor sem o id (para quando você está criando um novo cliente)
    public Cliente(String nome, String cpf, String endereco, String telefone, boolean whatsapp, String email, String dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.whatsapp = whatsapp;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(boolean whatsapp) {
        this.whatsapp = whatsapp;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", whatsapp=" + whatsapp +
                '}';
    }
}
