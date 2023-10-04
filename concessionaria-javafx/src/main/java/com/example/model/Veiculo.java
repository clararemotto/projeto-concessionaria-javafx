package com.example.model;

import java.math.BigDecimal;

public class Veiculo {
    
    private Long id;
    private String marca;
    private String modelo;
    private Integer ano;
    private BigDecimal valor;
    private Cliente cliente;

    public Veiculo(Long id, String marca, String modelo, Integer ano, BigDecimal valor, Cliente cliente) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.valor = valor;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Veiculo modelo(String modelo){
        this.setModelo(modelo);
        return this;
    }

    public Veiculo marca(String marca) {
        this.marca = marca;
        return this;
    }

    public Veiculo ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public Veiculo valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}