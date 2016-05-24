package com.crossover.medical.journals.resource;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.crossover.medical.journals.auth.AuthenticationManager;
import com.crossover.medical.journals.auth.RestrictedTo;
import com.crossover.medical.journals.core.User;
import com.crossover.medical.journals.core.UserRole;
import com.crossover.medical.journals.dao.UserDAO;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;
    private final AuthenticationManager authenticatorManager;

    public UserResource(UserDAO userDAO, AuthenticationManager authenticatorManager) {
        this.userDAO = userDAO;
        this.authenticatorManager = authenticatorManager;
    }

    @POST
    @Path("/authenticate")
    @UnitOfWork
    public Response authenticate(final User userCredential) {
        final User user = authenticatorManager.findOneByCredentials(userCredential.getEmail(),
                userCredential.getPassword());

        return Response.ok(user).build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response show(@RestrictedTo({ UserRole.PUBLISHER, UserRole.SUBSCRIBER }) User authenticatedUser,
            @PathParam("id") final Long id) {

        final Optional<User> user = userDAO.findOneById(id);

        if (!user.isPresent()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(user).build();
    }

    @GET
    @Path("/")
    @UnitOfWork
    public Response index(@RestrictedTo({ UserRole.PUBLISHER }) User authenticatedUser) {
        final List<User> users = userDAO.findAll();

        return Response.ok(users).build();
    }

    @POST
    @Path("/")
    @UnitOfWork
    public Response create(@Valid final User user) {
        authenticatorManager.createUserAndGenerateToken(user);

        return Response.created(UriBuilder.fromResource(UserResource.class).path("{id}").build(user.getId()))
                .entity(user).build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@RestrictedTo({ UserRole.PUBLISHER, UserRole.SUBSCRIBER }) User authenticatedUser,
            @PathParam("id") final Long id, @Valid final User newUser) {

        final Optional<User> optionalUser = userDAO.findOneById(id);

        if (!optionalUser.isPresent()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        final User user = optionalUser.get();

        user.setName(newUser.getName());
        user.setRole(newUser.getRole());
        user.setPassword(newUser.getPassword());

        authenticatorManager.updateUserAndEncodePasswordAndGenerateNewToken(user);

        return Response.ok(user).build();
    }

    // @DELETE
    // @Path("/{id}")
    // @UnitOfWork
    // public Response delete(@PathParam("id") final Long id) {
    // final Optional<User> user = userDAO.findOneById(id);
    //
    // if (!user.isPresent()) {
    // return Response.status(Status.NOT_FOUND).build();
    // }
    //
    // userDAO.delete(user.get());
    //
    // return Response.noContent().build();
    // }
}
