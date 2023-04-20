package com.hepta.funcionarios.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.persistence.HibernateUtil;
import com.hepta.funcionarios.persistence.exception.ObjectNotFoundException;
import com.hepta.funcionarios.persistence.interfaces.FuncionarioDAO;

/**
 * 
 * @author marcos
 * @apiNote Implementações dos métodos
 * 			definidos na interface DAO do objeto
 *
 */
public class FuncionarioDAOImpl implements FuncionarioDAO {

	public void save(Funcionario funcionario) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(funcionario);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
	}

	public Funcionario update(Funcionario funcionario) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		Funcionario funcionarioAtualizado = null;
		try {
			em.getTransaction().begin();
			funcionarioAtualizado = find(funcionario.getId());
			updateFuncionario(funcionarioAtualizado, funcionario);
			funcionarioAtualizado = em.merge(funcionarioAtualizado);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return funcionarioAtualizado;
	}
	
	private void updateFuncionario(Funcionario newFuncionario, Funcionario funcionario) {
		newFuncionario.setNome(funcionario.getNome());
		newFuncionario.setSetor(funcionario.getSetor());
		newFuncionario.setSalario(funcionario.getSalario());
		newFuncionario.setEmail(funcionario.getEmail());
		newFuncionario.setIdade(funcionario.getIdade());
	}

	public void delete(Integer id) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Funcionario funcionario = em.find(Funcionario.class, id);
			if(funcionario == null)
				throw new ObjectNotFoundException("Funcionário não encontrado com o ID " + id);
			em.remove(funcionario);
			em.getTransaction().commit();
		} catch (ObjectNotFoundException e) {
			em.getTransaction().rollback();
			throw new ObjectNotFoundException("Não foi encontrado nenhum funcionário com este ID");
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception("Ocorreu um erro ao deletar um funcionário. Motivo: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public Funcionario find(Integer id) throws ObjectNotFoundException {
		EntityManager em = HibernateUtil.getEntityManager();
		Funcionario Funcionario = null;
		try {
			Funcionario = em.find(Funcionario.class, id);
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ObjectNotFoundException("Não foi encontrado nenhum funcionário com este ID");
		} finally {
			em.close();
		}
		return Funcionario;
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> getAll() throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		List<Funcionario> Funcionarios = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Funcionario f join fetch f.setor ");
			Funcionarios = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return Funcionarios;
	}
	
}
