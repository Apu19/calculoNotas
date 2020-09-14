package com.example.calculonotas.dto;

public class Nota {
    private double valor;
    private int porcentaje;

    @Override
    public String toString() {
       return valor + " " + porcentaje + "%";
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }
}
