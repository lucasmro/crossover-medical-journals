package com.crossover.medical.journals.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crossover.medical.journals.TopicDTO;
import com.crossover.medical.journals.core.Topic;

@Path("/api/topics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TopicResource {

    @GET
    @Path("/")
    public Response index() {
        final List<TopicDTO> topics = new ArrayList<>();

        for (final Topic topic : Topic.values()) {
            final TopicDTO topicDTO = new TopicDTO(topic.toString(), topic.getValue());
            topics.add(topicDTO);
        }

        return Response.ok(topics).build();
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") final Topic topic) {
        final TopicDTO topicDTO = new TopicDTO(topic.toString(), topic.getValue());

        return Response.ok(topicDTO).build();
    }
}
