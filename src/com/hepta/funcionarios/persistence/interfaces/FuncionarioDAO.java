package com.hepta.funcionarios.persistence.interfaces;

import java.util.List;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.persistence.exception.ObjectNotFoundException;

/**
 * 
 * @author marcos
 * @apiNote Declaração da abstração dos métodos 
 * 			que o funcionário é capaz de utilizar
 *
 */
public interface FuncionarioDAO {
    

	public void save(Funcionario funcionario) throws Exception;
	public Funcionario update(Funcionario funcionario) throws Exception;
	public void delete(Integer id)  throws Exception;
	public Funcionario find(Integer id) throws ObjectNotFoundException;
	public List<Funcionario> getAll() throws Exception;

}
