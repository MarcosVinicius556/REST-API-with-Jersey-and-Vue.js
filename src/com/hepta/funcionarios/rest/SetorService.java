package com.hepta.funcionarios.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.hepta.funcionarios.dto.SetorDTO;
import com.hepta.funcionarios.persistence.SetorDAO;
import com.hepta.funcionarios.persistence.exception.ObjectNotFoundException;
import com.hepta.funcionarios.persistence.factory.DAOFactory;

@Path("/setores")
public class SetorService {

    private SetorDAO dao;

    public SetorService() {
        dao = DAOFactory.createSetorDAO();
    }

    /**
     * Adiciona novo Setor
     * 
     * @param SetorDTO: Novo Setor
     * @return response 200 (OK) - Conseguiu adicionar
     */
    @Path("/salvar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response setorCreate(SetorDTO setor) {
        try {
            dao.save(setor.fromDTO());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o setor!").build();
        }

        return Response.status(Status.OK).build();
    }

    /**
     * Lista todos os SetorDTO
     * 
     * @return response 200 (OK) - Conseguiu listar
     */
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response setorRead() {
        List<SetorDTO> setores = new ArrayList<>();
        try {
            setores = dao.getAll().stream()
            					  .map(setor -> new SetorDTO(setor))
            					  .sorted((setor1, setor2) -> setor1.getNome().compareTo(setor2.getNome()))
            					  .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Setores").build();
        }

        GenericEntity<List<SetorDTO>> entity = new GenericEntity<List<SetorDTO>>(setores) {
        };
        return Response.status(Status.OK).entity(entity).build();
    }
    
    /**
     * Busca um setor pelo id
     * 
     * @return response 200 (OK) - encontrou
     * @return response 404 (NOT_FOUND) - não encontrou
     */
    @Path("/buscar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response setorFindById(@PathParam("id") Integer id) {
    	SetorDTO setor = null;
        try {
            setor = new SetorDTO(dao.findById(id)); 
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return Response.status(Status.NOT_FOUND).entity("Não foi encontrado um setor com este ID " + id).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Setor").build();
        }

        GenericEntity<SetorDTO> entity = new GenericEntity<SetorDTO>(setor) {
        };
        return Response.status(Status.OK).entity(entity).build();
    }

    /**
     * Atualiza um Setor
     * 
     * @param SetorDTO: Setor atualizado
     * @return response 200 (OK) - Conseguiu atualizar
     * @return response 404 (NOT FOUND) - Não encontrou setor
     */
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public Response setorUpdate(SetorDTO setor) {
    	  try {
              dao.update(setor.fromDTO());
          } catch (ObjectNotFoundException e) {
              e.printStackTrace();
              return Response.status(Status.NOT_FOUND).entity("Não foi encontrado um setor com este ID " + setor.getId()).build();
          } catch (Exception e) {
              e.printStackTrace();
              return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar o setor!").build();
          }

          return Response.status(Status.OK).build();
    }

    /**
     * Remove um Setor
     * 
     * @param id: id do setor
     * @return response 200 (OK) - Conseguiu remover
     * @return response 404 (NOT FOUND) - Não encontrou setor
     */
    @Path("/deletar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public Response setorDelete(@PathParam("id") Integer id) {
    	  try {
              dao.delete(id);
          } catch (ObjectNotFoundException e) {
              e.printStackTrace();
              return Response.status(Status.NOT_FOUND).entity("Erro ao remover o setor!").build();
          } catch (Exception e) {
        	  e.printStackTrace();
    		  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover o setor!" + e).build();
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
        return "REST Setor uncionando.";
    }

}
