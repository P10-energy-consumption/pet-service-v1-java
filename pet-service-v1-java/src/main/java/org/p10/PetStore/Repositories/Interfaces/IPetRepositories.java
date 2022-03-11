package org.p10.PetStore.Repositories.Interfaces;

import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Models.PetStatus;

import java.util.List;
import java.util.UUID;

public interface IPetRepositories {

    Pet getPet(int petId);
    int insertPet(Pet pet);
    int updatePet(Pet pet);
    int insertPetPhoto(UUID photoId, int petId, String metaData, String url);
    int deletePet(int petId);
    List<Pet> getPetByStatus(PetStatus status);
}
