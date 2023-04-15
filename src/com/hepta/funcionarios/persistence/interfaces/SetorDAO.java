package com.hepta.funcionarios.persistence.interfaces;

import java.util.List;

import com.hepta.funcionarios.entity.Setor;

/**
 * 
 * @author marcos
 * @apiNote Declaração da abstração dos métodos 
 * 			que o funcionário é capaz de utilizar
 *
 */
public interface SetorDAO {
    
	public void save(Setor setor) throws Exception;
	public Setor update(Setor setor) throws Exception;
	public void delete(Integer id) throws Exception;
	public Setor find(Integer id) throws Exception;
	public List<Setor> getAll() throws Exception;

}
