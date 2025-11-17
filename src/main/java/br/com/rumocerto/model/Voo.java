package br.com.rumocerto.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Voo {
    private String origem;
    private String destino;
    private LocalDate data;
    private LocalTime horario;
    private double preco;
    private Duration duracao;

    public Voo() {
    }

    public Voo(String origem, String destino, LocalDate data, LocalTime horario, double preco, Duration duracao) {
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.horario = horario;
        this.preco = preco;
        this.duracao = duracao;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return "Voo{" +
                "origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", data=" + data +
                ", horario=" + horario +
                ", preco=" + preco +
                ", duracao=" + duracao +
                '}';
    }
}

