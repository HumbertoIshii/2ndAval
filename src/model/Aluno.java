package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Aluno {
    String nome;
    String dataNascimento;
    float peso;
    float altura;
    String cpf;

    public Aluno(String nome, String dataNascimento, float peso, float altura, String cpf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<String> calcularIMC(){
        List<String> resultado = new ArrayList<>();
        float imc = peso / (altura*altura);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = currentDate.format(formatter);

        resultado.add("Calculo do IMC - Data: " + data);
        resultado.add("Nome: " + nome);
        resultado.add("CPF: " + cpf);
        resultado.add(String.format("IMC: %.2f", imc));
        resultado.add("De acordo com a Organização Mundial da Saúde (OMS)");
        if (imc<18.5){
            resultado.add("O aluno(a) esta abaixo do peso");
        } else if (imc>=18.5 && imc<=24.9){
            resultado.add("O aluno(a) esta com peso normal");
        } else if (imc>=25 && imc<=29.9){
            resultado.add("O aluno(a) esta com sobrepeso");
        } else {
            resultado.add("O aluno(a) esta com obesidade");
        }
        resultado.add("-----------------------------------");
        return resultado;
    }
}
