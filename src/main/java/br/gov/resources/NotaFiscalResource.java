package br.gov.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.gov.dtos.NotaFiscalDTO;
import br.gov.models.NotaFiscalModel;
import br.gov.services.NotaFiscalService;

@Path("/notas-fiscais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotaFiscalResource {

    @Inject
    NotaFiscalService notaFiscalService;

    @GET
    public Response listarNotasFiscais() {
        List<NotaFiscalDTO>  listarNotasFiscais = notaFiscalService.listarNotasFiscais();
        return Response.status(Response.Status.OK).entity(listarNotasFiscais).build();
    }

    @GET
    @Path("/{idNotaFiscal}")
    public Response consultarNotaFiscalPorId(@PathParam("idNotaFiscal") Long idNotaFiscal) {
        NotaFiscalDTO notaFiscalDTO = notaFiscalService.consultarNotaFiscalPorId(idNotaFiscal);
        return Response.status(Response.Status.OK).entity(notaFiscalDTO).build();
    }

    @POST
    public Response inserir(NotaFiscalDTO notaFiscalDTO) {
        NotaFiscalModel notaFiscalDTORetorno = notaFiscalService.inserir(notaFiscalDTO);
        return Response.status(Response.Status.CREATED).entity(notaFiscalDTORetorno).build();
    }

    @PUT
    public Response atualizar(NotaFiscalDTO notaFiscalDTO) {
        NotaFiscalModel notaFiscalDTORetorno = notaFiscalService.inserir(notaFiscalDTO);
        return Response.status(Response.Status.OK).entity(notaFiscalDTORetorno).build();
    }

    @DELETE
    @Path("/{idNotaFiscal}")
    public Response remover(@PathParam("idNotaFiscal") Long idNotaFiscal) {
        notaFiscalService.remover(idNotaFiscal);
        return Response.status(Response.Status.OK).build();
    }

}
