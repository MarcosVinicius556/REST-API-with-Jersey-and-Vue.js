package com.hepta.funcionarios.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.persistence.exception.ObjectNotFoundException;
import com.hepta.funcionarios.persistence.factory.DAOFactory;
import com.hepta.funcionarios.persistence.interfaces.FuncionarioDAO;

@Path("/funcionarios")
public class FuncionarioService {

    private FuncionarioDAO dao;

    public FuncionarioService() {
        dao = DAOFactory.createFuncionarioDAO();
    }

    /**
     * Adiciona novo Funcionario
     * 
     * @param Funcionario: Novo Funcionario
     * @return response 200 (OK) - Conseguiu adicionar
     */
    @Path("/salvar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response funcionarioCreate(Funcionario funcionario) {
        try {
            dao.save(funcionario);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o funcionário!").build();
        }

        return Response.status(Status.OK).build();
    }

    /**
     * Lista todos os Funcionarios
     * 
     * @return response 200 (OK) - Conseguiu listar
     */
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response funcionarioRead() {
        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            funcionarios = dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Funcionarios").build();
        }

        GenericEntity<List<Funcionario>> entity = new GenericEntity<List<Funcionario>>(funcionarios) {
        };
        return Response.status(Status.OK).entity(entity).build();
    }
    
    /**
     * Busca um funcionário pelo id
     * 
     * @return response 200 (OK) - Conseguiu listar
     */
    @Path("/buscar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response funcionarioFindById(@PathParam("id") Integer id) {
    	Funcionario funcionario = null;
        try {
            funcionario = dao.find(id);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return Response.status(Status.NOT_FOUND).entity("Não foi encontrado um funcionário com este ID " + id).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Funcionarios").build();
        }

        GenericEntity<Funcionario> entity = new GenericEntity<Funcionario>(funcionario) {
        };
        return Response.status(Status.OK).entity(entity).build();
    }

    /**
     * Atualiza um Funcionario
     * 
     * @param id:          id do Funcionario
     * @param Funcionario: Funcionario atualizado
     * @return response 200 (OK) - Conseguiu atualizar
     */
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public Response funcionarioUpdate(Funcionario funcionario) {
    	  try {
              dao.update(funcionario);
          } catch (ObjectNotFoundException e) {
              e.printStackTrace();
              return Response.status(Status.NOT_FOUND).entity("Não foi encontrado um funcionário com este ID " + funcionario.getId()).build();
          } catch (Exception e) {
              e.printStackTrace();
              return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar o funcionário!").build();
          }

          return Response.status(Status.OK).build();
    }

    /**delete
     * Remove um Funcionario
     * 
     * @param id: id do funcionario
     * @return response 200 (OK) - Conseguiu remover
     */
    @Path("/deletar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public Response FuncionarioDelete(@PathParam("id") Integer id) {
    	  try {
              dao.delete(id);
          } catch (ObjectNotFoundException e) {
              e.printStackTrace();
              return Response.status(Status.NOT_FOUND).entity("Erro ao remover o funcionário!").build();
          } catch (Exception e) {
              e.printStackTrace();
              return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover o funcionário!").build();
          }

          return Response.status(Status.OK).build();
    }

    /**
     * Métodos simples apenas para testar o REST
     * 
     * @return
     */
    @Path("/teste")
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String testeJersey() {
        return "REST Funcionario funcionando.";
    }

}
