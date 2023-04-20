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

import com.hepta.funcionarios.entity.Setor;
import com.hepta.funcionarios.persistence.factory.DAOFactory;
import com.hepta.funcionarios.persistence.interfaces.SetorDAO;

@Path("/setores")
public class SetorService {

    private SetorDAO dao;

    public SetorService() {
        dao = DAOFactory.createSetorDAO();
    }

    /**
     * Adiciona novo Setor
     * 
     * @param Setor: Novo Setor
     * @return response 200 (OK) - Conseguiu adicionar
     */
    @Path("/salvar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response setorCreate(Setor setor) {
        try {
            dao.save(setor); //Criar retorno de erro personalizado!
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(Status.OK).build();
    }

    /**
     * Lista todos os Setores
     * 
     * @return response 200 (OK) - Conseguiu listar
     */
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response setorRead() {
        List<Setor> setores = new ArrayList<>();
        try {
            setores = dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Funcionarios").build();
        }

        GenericEntity<List<Setor>> entity = new GenericEntity<List<Setor>>(setores) {
        };
        return Response.status(Status.OK).entity(entity).build();
    }

    /**
     * Busca um setor pelo id
     * 
     * @return response 200 (OK) - Conseguiu listar
     */
    @Path("/buscar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response funcionarioFindById(@PathParam("id") Integer id) {
    	Setor setor = null;
        try {
            setor = dao.find(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar o setor").build();
        }

        GenericEntity<Setor> entity = new GenericEntity<Setor>(setor) {
        };
        return Response.status(Status.OK).entity(entity).build();
    }
    
    /**
     * Atualiza um Setor
     * 
     * @param id:          id do Setor
     * @param Setor: Setor atualizado
     * @return response 200 (OK) - Conseguiu atualizar
     */
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public Response setorUpdate(Setor setor) {
    	try {
            dao.update(setor);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar o setor!").build();
        }

        return Response.status(Status.OK).build();
    }

    /**
     * Remove um Setor
     * 
     * @param id: id do Setor
     * @return response 200 (OK) - Conseguiu remover
     */
    @Path("/deletar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public Response setorDelete(@PathParam("id") Integer id) {
    	try {
            dao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover o setor!").build();
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
        return "REST Setor funcionando.";
    }

}
