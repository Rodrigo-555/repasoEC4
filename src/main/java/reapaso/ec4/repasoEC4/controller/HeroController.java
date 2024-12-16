package reapaso.ec4.repasoEC4.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reapaso.ec4.repasoEC4.entity.Hero;
import reapaso.ec4.repasoEC4.repository.HeroRepository;

import java.util.List;

@Service
@Path("/heroes")
public class HeroController {

    @Autowired
    private HeroRepository heroRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHeroes() {
        try {
            // Obtener todas las películas del repositorio
            List<Hero> heroes = heroRepository.findAll();
            String json = objectMapper.writeValueAsString(heroes);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al convertir a JSON")
                    .build();
        }
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHeroByName(@PathParam("name") String name) {
        Hero hero = heroRepository.findByName(name);
        if (hero != null) {
            try {
                String json = objectMapper.writeValueAsString(hero);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } catch (JsonProcessingException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al convertir a JSON")
                        .build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\":\"Héroe no encontrado\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addHero(String json) {
        try {
            Hero newHero = objectMapper.readValue(json, Hero.class);
            heroRepository.save(newHero);
            String createdJson = objectMapper.writeValueAsString(newHero);
            return Response.status(Response.Status.CREATED)
                    .entity(createdJson)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al convertir a JSON")
                    .build();
        }
    }

    @PUT
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHero(@PathParam("name") String name, String json) {
        try {
            Hero updateHero = objectMapper.readValue(json, Hero.class);
            Hero existingHero = heroRepository.findByName(name);
            if (existingHero != null) {
                existingHero.setName(updateHero.getName());
                existingHero.setType(updateHero.getType());
                existingHero.setRoles(updateHero.getRoles());
                existingHero.setAttack_type(updateHero.getAttack_type());
                heroRepository.save(existingHero);
                return Response.ok(existingHero, MediaType.APPLICATION_JSON).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Héroe no encontrado\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al convertir a JSON")
                    .build();
        }
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHero(@PathParam("name") String name) {
        Hero hero = heroRepository.findByName(name);
        if (hero != null) {
            heroRepository.delete(hero);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\":\"Héroe no encontrado\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}


