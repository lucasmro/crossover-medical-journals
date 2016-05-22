package com.crossover.medical.journals.exception;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.eclipse.jetty.http.HttpStatus;

public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(final WebApplicationException e) {
        final int status = (e.getResponse() == null) ? 500 : e.getResponse().getStatus();
        final String msg = (e.getMessage() == null) ? HttpStatus.getMessage(status) : e.getMessage();

        final Map<String, String> error = new HashMap<>();
        error.put("code", Integer.toString(status));
        error.put("message", msg);

        return Response.status(status).type(MediaType.APPLICATION_JSON_TYPE).entity(error).build();
    }
}
