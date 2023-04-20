package com.hepta.funcionarios.entity;

/**
 * 
 * @author Marcos Vinicius Angeli Costa
 *
 * @apiNote interface base para entidades Desenvolvida com o intuito 
 * 			de fornecer abstrações base para o DAO poder implementar default 
 *			methods genéricos, tais como as operações de CRUD Básico.
 *			Desse modo podendo fazer o reuso de métodos genéricos para 
 *			uma quantidade ilimitada de classes
 */
public interface BaseEntity {

	public String getTableName();
	public Object getId();
	
}
