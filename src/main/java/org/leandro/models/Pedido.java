package org.leandro.models;

public class Pedido {
    private int id;
    private int clienteId;
    private String dataEntrega;
    private String observacao;
    private String status;
    private double total;

    // Construtor padrão
    public Pedido(int i, int clienteId, String dataEntrega, String observacao, String status, double total) {}

    // Construtor com todos os atributos
    public Pedido(int clienteId, String dataEntrega, String observacao, String status, double total) {
        this.clienteId = clienteId;
        this.dataEntrega = dataEntrega;
        this.observacao = observacao;
        this.status = status;
        this.total = total;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", dataEntrega='" + dataEntrega + '\'' +
                ", observacao='" + observacao + '\'' +
                ", status='" + status + '\'' +
                ", total=" + total +
                '}';
    }
}
