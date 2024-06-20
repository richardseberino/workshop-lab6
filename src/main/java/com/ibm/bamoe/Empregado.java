package com.ibm.bamoe;

import java.io.Serializable;

public class Empregado implements Serializable {

    static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label("Matricula do Funcionário")
	private java.lang.String matricula;
	@org.kie.api.definition.type.Label("Nome Completo do funcionário")
	private java.lang.String nome;
	@org.kie.api.definition.type.Label("e-mail do funcionário")
	private java.lang.String email;
	@org.kie.api.definition.type.Label("Cargo / função do funcionário")
	private java.lang.String cargo;
    @org.kie.api.definition.type.Label("Salário do funcionário")
	private double salario;
    
    public java.lang.String getMatricula() {
        return matricula;
    }
    public void setMatricula(java.lang.String matricula) {
        this.matricula = matricula;
    }
    public java.lang.String getNome() {
        return nome;
    }
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }
    public java.lang.String getEmail() {
        return email;
    }
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    public java.lang.String getCargo() {
        return cargo;
    }
    public void setCargo(java.lang.String cargo) {
        this.cargo = cargo;
    }
    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }

    


    
}
