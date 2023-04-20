package com.hepta.funcionarios.dto;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.entity.Setor;

/**
 * 
 * @author Marcos Vinicius Angeli Costa
 * 
 * @apiNote Classe DTO para garantir uma 
 * 			maior segurança e flexibilidade 
 * 			na hora de trafegar os dados pela 
 * 			rede
 *
 */
public class FuncionarioDTO {

	private Integer id;
	private String nome;
	private Setor setor;
	private Double salario;
	private String email;
	private Integer idade;
	
	public FuncionarioDTO(Funcionario funcionario) {
		this.id = funcionario.getId();
		this.nome = funcionario.getNome();
		this.setor = funcionario.getSetor();
		this.salario = funcionario.getSalario();
		this.email = funcionario.getEmail();
		this.idade = funcionario.getIdade();
	}
	
	
	public Integer getId() {
		return id;
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
	
	public Funcionario fromDTO() {
		return new Funcionario.Builder().setId(getId())
										.setNome(getNome())
										.setSetor(getSetor())
										.setSalario(getSalario())
										.setEmail(getEmail())
										.setIdade(getIdade())
										.build();
	}
	
}
