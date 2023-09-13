package com.example.model;

import java.math.BigDecimal;

public class Veiculo {

    private Long id;
    // inteiro longo (faixa maior de valores)
    private String marca;
    private String modelo;
    private Integer ano;
    private BigDecimal valor;
    // valores monetarios
    
    public Veiculo(Long id, String marca, String modelo, Integer ano, BigDecimal valor) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.valor = valor;
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

    
}
