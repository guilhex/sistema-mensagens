package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/mensagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensagemResource {

    // Armazenamento em memória conforme o requisito 3.3
    private List<Mensagem> mensagens = new ArrayList<>();
    private long idCounter = 1;

    @GET
    public Response listarTodas() {
        // Retorna 200 OK com a lista (mesmo que vazia)
        return Response.ok(mensagens).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Optional<Mensagem> mensagem = mensagens.stream()
                .filter(m -> m.id.equals(id))
                .findFirst();

        if (mensagem.isPresent()) {
            return Response.ok(mensagem.get()).build(); // 200 OK
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // 404 Not Found
    }

    @POST
    public Response criar(Mensagem mensagem) {
        mensagem.id = idCounter++;
        mensagem.timestamp = LocalDateTime.now();
        mensagens.add(mensagem);

        // Retorna 201 Created com o objeto criado
        return Response.status(Response.Status.CREATED).entity(mensagem).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        boolean removido = mensagens.removeIf(m -> m.id.equals(id));

        if (removido) {
            // Pode retornar 200 OK ou 204 No Content. Usando 200 para manter a simplicidade.
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // 404 Not Found
    }
}