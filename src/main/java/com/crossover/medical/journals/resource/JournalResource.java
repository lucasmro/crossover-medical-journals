package com.crossover.medical.journals.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crossover.medical.journals.auth.RestrictedTo;
import com.crossover.medical.journals.core.Journal;
import com.crossover.medical.journals.core.User;
import com.crossover.medical.journals.core.UserRole;
import com.crossover.medical.journals.dao.JournalDAO;
import com.google.common.base.Optional;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/api/journals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JournalResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalResource.class);

    private final JournalDAO journalDAO;
    private final String uploadDirectory;

    public JournalResource(JournalDAO journalDAO, String uploadDirectory) {
        this.journalDAO = journalDAO;
        this.uploadDirectory = uploadDirectory;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response show(@PathParam("id") final Long id) {
        final Optional<Journal> journal = journalDAO.findOneById(id);

        if (!journal.isPresent()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(journal).build();
    }

    @GET
    @Path("/")
    @UnitOfWork
    public Response index() {
        final List<Journal> customers = journalDAO.findAll();

        return Response.ok(customers).build();
    }

    @POST
    @Path("/")
    @UnitOfWork
    public Response create(@RestrictedTo({ UserRole.PUBLISHER }) User authenticatedUser, @Valid final Journal journal) {
        journalDAO.save(journal);

        return Response.created(UriBuilder.fromResource(JournalResource.class).path("{id}").build(journal.getId()))
                .entity(journal).build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@RestrictedTo({ UserRole.PUBLISHER }) User authenticatedUser, @PathParam("id") final Long id,
            @Valid final Journal newJournal) {

        final Optional<Journal> optionalJournal = journalDAO.findOneById(id);

        if (!optionalJournal.isPresent()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        final Journal journal = optionalJournal.get();

        journal.setSubject(newJournal.getSubject());
        journal.setAuthor(newJournal.getAuthor());
        journal.setTopic(newJournal.getTopic());
        journal.setYear(newJournal.getYear());
        journal.setFilename(newJournal.getFilename());

        journalDAO.save(journal);

        return Response.ok(journal).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@RestrictedTo({ UserRole.PUBLISHER }) User authenticatedUser,
            @PathParam("id") final Long id) {

        final Optional<Journal> journal = journalDAO.findOneById(id);

        if (!journal.isPresent()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        journalDAO.delete(journal.get());

        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/upload")
    @UnitOfWork
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@PathParam("id") final Long id, @FormDataParam("file") final InputStream fileInputStream,
            @FormDataParam("file") final FormDataContentDisposition contentDispositionHeader) {

        final java.nio.file.Path outputPath = FileSystems.getDefault().getPath(uploadDirectory,
                contentDispositionHeader.getFileName());

        try {
            Files.copy(fileInputStream, outputPath);
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        return Response.status(201).entity(outputPath).build();
    }
}
