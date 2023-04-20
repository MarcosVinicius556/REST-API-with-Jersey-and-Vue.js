package com.hepta.funcionarios.dto;

import java.io.Serializable;

import com.hepta.funcionarios.entity.Setor;

public class SetorDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	
	public SetorDTO() {
		
	}
	
	public SetorDTO(Setor setor) {
		this.id = setor.getId();
		this.nome = setor.getNome();
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

	public Setor fromDTO() {
		return new Setor(getId(), getNome());
	}
	
}
