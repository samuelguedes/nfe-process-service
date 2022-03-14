package br.gov.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.gov.dtos.NotaFiscalDTO;
import br.gov.services.NotaFiscalService;

@Path("/notas-fiscais")
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

    @GET
    @Path("/numero/{chave}")
    public Response consultarNotaFiscalPelaChave(@PathParam("chave") String chave) {
        NotaFiscalDTO notaFiscalDTO = notaFiscalService.consultarNotaFiscalPelaChave(chave);
        return Response.status(Response.Status.OK).entity(notaFiscalDTO).build();
    }

    @DELETE
    @Path("/{idNotaFiscal}")
    public Response deletarProduto(@PathParam("idNotaFiscal") Long idNotaFiscal) {
        notaFiscalService.remover(idNotaFiscal);
        return Response.status(Response.Status.ACCEPTED).build();
    }

}
