package org.leandro.models;

import java.util.Date;

public class Pagamento {
    private int id;
    private int pedidoId;
    private String metodoPagamento;
    private double valor;
    private String dataPagamento;

    // Construtor padrão
    public Pagamento() {}

    // Construtor com todos os atributos
    public Pagamento(int id, int pedidoId, String metodoPagamento, double valor, String dataPagamento) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.metodoPagamento = metodoPagamento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
    }

    // Construtor sem o id (para quando você está criando um novo pagamento)
    public Pagamento(int pedidoId, String metodoPagamento, double valor, String dataPagamento) {
        this.pedidoId = pedidoId;
        this.metodoPagamento = metodoPagamento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
    }

    public Pagamento(int id, int pedidoId, String metodo, double valor, Date date) {
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

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", pedidoId=" + pedidoId +
                ", metodoPagamento='" + metodoPagamento + '\'' +
                ", valor=" + valor +
                ", dataPagamento='" + dataPagamento + '\'' +
                '}';
    }
}
