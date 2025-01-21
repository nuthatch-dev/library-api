package ru.nuthatch.libraryapi.resource;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.nuthatch.libraryapi.entity.Issue;
import ru.nuthatch.libraryapi.service.IssueService;

import java.util.Date;
import java.util.List;

@Stateless
@Path("issue")
@Produces(MediaType.APPLICATION_JSON)
public class IssueResource {

    @Inject
    private IssueService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Issue issue) {
        return service.create(issue)
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
    public Response update(Issue issue) {
        return service.update(issue)
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

    @GET
    @Path("/issues-by-reader-for-period")
    public Response findAllIssuesByReaderForPeriod(@QueryParam(value = "reader_id") long readerId,
                                                       @QueryParam(value = "start_date") Date startDate,
                                                       @QueryParam(value = "end_date") Date endDate) {
        List<Issue> issueList = service.findAllIssuesByReaderForPeriod(readerId, startDate, endDate);
        return Response
                .status(Response.Status.OK)
                .entity(issueList)
                .build();
    }

    @GET
    @Path("/count-by-reader-for-period")
    public Response findCountOfIssuesByReaderForPeriod(@QueryParam(value = "reader_id") long readerId,
                                                       @QueryParam(value = "start_date") Date startDate,
                                                       @QueryParam(value = "end_date") Date endDate) {
        int countOfIssues = service.findCountOfIssuesByReaderForPeriod(readerId, startDate, endDate);
        return Response
                .status(Response.Status.OK)
                .entity(countOfIssues)
                .build();
    }
}
