package org.leandro.models;

public class Item {
    private int id;
    private int pedidoId;
    private String descricao;
    private int quantidade;
    private double preco;

    // Construtor padrão
    public Item(int i, String tipo, double preco) {}

    // Construtor com todos os atributos
    public Item(int id, int pedidoId, String descricao, int quantidade, double preco) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Construtor sem o id (para quando você está criando um novo item)
    public Item(int pedidoId, String descricao, int quantidade, double preco) {
        this.pedidoId = pedidoId;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", pedidoId=" + pedidoId +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                '}';
    }
}

