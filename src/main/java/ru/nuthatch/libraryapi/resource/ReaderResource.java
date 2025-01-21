package ru.nuthatch.libraryapi.resource;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.nuthatch.libraryapi.entity.Reader;
import ru.nuthatch.libraryapi.service.ReaderService;

@Stateless
@Path("reader")
@Produces(MediaType.APPLICATION_JSON)
public class ReaderResource {

    @Inject
    private ReaderService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Reader reader) {
        return service.create(reader)
                .map(value -> Response
                        .status(Response.Status.CREATED)
                        .entity(value)
                        .build())
                .orElseGet(() -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @GET
    public Response findById(@QueryParam(value = "id") long id) {
        return service.findById(id)
                .map(value -> Response
                        .status(Response.Status.OK)
                        .entity(value)
                        .build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/all")
    public Response findAll() {
        return Response
                .status(Response.Status.OK)
                .entity(service.findAll())
                .build();
    }

    @PUT
    public Response update(Reader reader) {
        return service.update(reader)
                .map(value -> Response
                        .status(Response.Status.OK)
                        .entity(value)
                        .build())
                .orElseGet(() -> Response.status(Response.Status.NO_CONTENT).build());
    }

    @DELETE
    public Response deleteById(@QueryParam(value = "id") long id) {
        return service.deleteById(id) ?
                Response.status(Response.Status.OK).build() :
                Response.status(Response.Status.NO_CONTENT).build();
    }
}
