package com.hepta.funcionarios.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Funcionario implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_FUNCIONARIO")
	private Integer id;

	@Column(name = "NOME")
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_SETOR")
	private Setor setor;

	@Column(name = "NU_SALARIO")
	private Double salario;

	@Column(name = "DS_EMAIL")
	private String email;

	@Column(name = "NU_IDADE")
	private Integer idade;

	public Integer getId() {
		return id;
	}

	/**
	 * @apiNote Pattern Builder para facilitar na criação do objeto
	 *
	 */
	public static class Builder {
		private Integer id;
		private String nome;
		private Setor setor;
		private Double salario;
		private String email;
		private Integer idade;
		
		public Funcionario build() {
			return new Funcionario(this);
		}

		public Builder setId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder setNome(String nome) {
			this.nome = nome;
			return this;
		}

		public Builder setSetor(Setor setor) {
			this.setor = setor;
			return this;
		}

		public Builder setSalario(Double salario) {
			this.salario = salario;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setIdade(Integer idade) {
			this.idade = idade;
			return this;
		}
		
	}
	
	public Funcionario() {

	}

	public Funcionario(String nome, Setor setor, Double salario, String email, Integer idade) {
		this.nome = nome;
		this.setor = setor;
		this.salario = salario;
		this.email = email;
		this.idade = idade;
	}
	
	public Funcionario(Builder builder) {
		this.id = builder.id;
		this.nome = builder.nome;
		this.setor = builder.setor;
		this.salario = builder.salario;
		this.email = builder.email;
		this.idade = builder.idade;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	@Override
	public String getTableName() {
		return Funcionario.class.getName();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		return Objects.equals(id, other.id);
	}

}
