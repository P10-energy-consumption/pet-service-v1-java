package org.p10.PetStore.Controllers;

import com.google.gson.Gson;
import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Models.PetCategory;
import org.p10.PetStore.Models.PetStatus;
import org.p10.PetStore.Models.Pojo.PetPhotoPojo;
import org.p10.PetStore.Models.Pojo.PetPojo;
import org.p10.PetStore.Repositories.PetRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/v1")
public class PetController {

    private final PetRepository petRepository;
    private final Gson gson;

    public PetController() {
        this.gson = new Gson();
        this.petRepository = new PetRepository();
    }

    @GET
    @Path("/pet/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPet(@PathParam("id") int petId) {
        Pet pet = petRepository.getPet(petId);
        return Response.ok(gson.toJson(pet)).build();
    }

    @POST
    @Path("/pet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPet(PetPojo petPojo) {
        Pet pet = new Pet(petPojo.getId(), PetCategory.values()[petPojo.getCategory()],
                petPojo.getName(), petPojo.getPhotoUrls(),
                petPojo.getTags(), PetStatus.values()[petPojo.getStatus()]);
        int rowId = petRepository.insertPet(pet);
        if (rowId > 0) {
            return Response.ok(rowId).build();
        } else {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/pet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePet(PetPojo petPojo) {
        Pet pet = new Pet(petPojo.getId(), PetCategory.values()[petPojo.getCategory()],
                petPojo.getName(), petPojo.getPhotoUrls(),
                petPojo.getTags(), PetStatus.values()[petPojo.getStatus()]);
        int affectedRows = petRepository.updatePet(pet);
        if (affectedRows > 0) {
            return Response.ok(affectedRows).build();
        } else {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/pet/{petId}/uploadImage")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPetPhoto(PetPhotoPojo petPhotoPojo) {
        UUID photoId = UUID.randomUUID();
        String fileUrl = "/some/url/" + photoId;
        int rowId = petRepository.insertPetPhoto(photoId, petPhotoPojo.getPetID(),
                petPhotoPojo.getMetaData(), fileUrl);
        if (rowId > 0) {
            return Response.ok(rowId).build();
        } else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/pet/{petId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePet(@PathParam("petId") int petId) {
        int affectedRows = petRepository.deletePet(petId);
        if (affectedRows > 0) {
            return Response.ok(affectedRows).build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/pet/findByStatus")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPetByStatus(@QueryParam("petStatus") int petStatus) {
        List<Pet> petList = petRepository.getPetByStatus(PetStatus.values()[petStatus]);
        return Response.ok(gson.toJson(petList)).build();
    }
}