package org.exemplo.persistencia.database.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column (name = "nome")
	private String nome;
	@Column (name = "cpf")
	private String cpf;
	@OneToMany (mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Conta> contas;
	
	
	public Cliente() {
		
	}
	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
		contas = new ArrayList<>();
	}
	
	public Cliente(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	@Override
	public String toString() {
		return "Cliente [ CPF:" + cpf + " | Nome: " + nome + " | Contas=" + contas + " ]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
    public void adicionarConta(Conta c) {
    	
		
    }

    public void removerConta(Conta c) {

    	
    }

    public Conta localizarContaNumero(int numero) {
    	return null;
    }	

    public double balancoEntreContas() {
    	
    	double valor = 0;
    	
		for (Conta conta: contas) {
			valor+=conta.getSaldo().doubleValue();
		}
		
		return valor;
    }
    
}