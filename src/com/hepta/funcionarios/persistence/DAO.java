package com.hepta.funcionarios.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.PersistentObjectException;

import com.hepta.funcionarios.entity.BaseEntity;
import com.hepta.funcionarios.persistence.exception.ObjectAlreadyAssociatedException;
import com.hepta.funcionarios.persistence.exception.ObjectNotFoundException;
import com.hepta.funcionarios.persistence.singleton.HibernateUtil;

/**
 * 
 * @author Marcos Vinicius Angeli Costa
 *
 * @apiNote Fornece a implementação de métodos CRUD básicos, 
 * 			podendo assim fazer o reuso para os demais DAO
 */
public interface DAO<Entity extends BaseEntity> {

	/**
	 * @param Entity<? extends BaseEntity> entity : Objeto para salvar
	 * @throws Exception
	 */
	default void save(Entity entity) throws Exception{
		EntityManager em = HibernateUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
	}
	
	/**
	 * @param Entity<? extends BaseEntity> entity : Objeto para salvar
	 * @throws ObjectNotFoundException se não encontar entidade com o id informado
	 * @throws Exception se ocorrer algum erro durante o update
	 */
	default Entity update(Entity entity) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		Entity entityAtualizado = null;
		try {
			if(findById(entity.getId()) == null)
				throw new ObjectNotFoundException("");
			em.getTransaction().begin();
			entityAtualizado = em.merge(entity);
			em.getTransaction().commit();
			
		} catch (ObjectNotFoundException e) {
			em.getTransaction().rollback();
			throw new ObjectNotFoundException("Não foi possível atualizar a entidade. Motivo: " + e);
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return entityAtualizado;
	}
	
	/**
	 * @param Object id da entidade a ser removida
	 * @throws ObjectNotFoundException se não encontrar a entidade para remover
	 * @throws Exception se ocorrer um erro durante a exclusão
	 */
	default void delete(Object id)  throws Exception{
		EntityManager em = HibernateUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Entity entity = findById(id);
			em.remove(em.contains(entity) ? entity : em.merge(entity));
			em.getTransaction().commit();
		} catch (ObjectNotFoundException e) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
			em.getTransaction().rollback();
			throw new ObjectNotFoundException("Não foi encontrado nenhuma entidade com este ID");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception("Ocorreu um erro ao deletar a entidade. Motivo: " + e);
		} finally {
			em.close();
		}
	}
	
	/**
	 * @param Object id da entidade para busca
	 * @throws ObjectNotFoundException se não encontrar a entidade
	 */
	public Entity findById(Object id) throws ObjectNotFoundException;
	
	/**
	 * @throws Exception se ocorrer um erro durante a exclusão
	 */
	public List<Entity> getAll() throws Exception;
	
}
