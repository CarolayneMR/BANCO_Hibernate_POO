package org.exemplo.persistencia.database.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Objects;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.exemplo.persistencia.database.model.TipoTransacao;

import net.bytebuddy.asm.Advice.Local;

@javax.persistence.Entity
@Table(name = "registro_transacao")
public class RegistroTransacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "valor")
	private BigDecimal valor;
	@Column(name = "data")
	private LocalDateTime data;
	
	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;
	
	public RegistroTransacao() {
		
	}
	
	public RegistroTransacao(BigDecimal valor, LocalDateTime data) {
		this.valor = valor;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, id, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroTransacao other = (RegistroTransacao) obj;
		return Objects.equals(data, other.data) && Objects.equals(id, other.id) 
				&& Objects.equals(valor, other.valor);
	}

	@Override
	public String toString() {
		return "RegistroTransacao [id=" + id + ", valor=" + valor + ", data=" + data + "]";
	}

	

	public void setConta(int numeroConta) {
		// TODO Auto-generated method stub
		
	}

	public void setConta(Conta conta) {
		this.conta = conta;
		
	}
}