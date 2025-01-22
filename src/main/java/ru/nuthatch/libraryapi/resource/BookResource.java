package ru.nuthatch.libraryapi.resource;


import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.nuthatch.libraryapi.entity.Book;
import ru.nuthatch.libraryapi.service.BookService;

@Stateless
@Path("book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    private BookService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Book book) {
        return service.create(book)
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
    public Response update(Book book) {
        return service.update(book)
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
