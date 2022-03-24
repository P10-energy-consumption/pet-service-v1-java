package org.p10.PetStore.Repositories;

import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Models.PetCategory;
import org.p10.PetStore.Models.PetStatus;
import org.p10.PetStore.Repositories.Interfaces.IPetRepositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PetRepository extends Repository implements IPetRepositories {

    @Override
    public Pet getPet(int petId) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM pets.pet WHERE id=?");
            stmt.setInt(1, petId);
            ResultSet rs = stmt.executeQuery();

            Pet pet = null;
            while (rs.next()) {
                pet = createPetFromResultSet(rs);
            }
            stmt.close();
            connection.close();

            return pet;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    @Override
    public int insertPet(Pet pet) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into pets.pet (id, name, category, status, tags, created, createdby) " +
                            "values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, 'PetStore.Pet.Api');"
            );
            stmt.setInt(1, pet.getId());
            stmt.setString(2, pet.getName());
            stmt.setInt(3, pet.getCategory().ordinal());
            stmt.setInt(4, pet.getStatus().ordinal());
            stmt.setString(5, pet.getTags());
            int affectedRows = stmt.executeUpdate();

            stmt.close();
            connection.close();

            return affectedRows;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    @Override
    public int updatePet(Pet pet) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "update pets.pet set Name = ?, Status = ?, Tags = ?, " +
                            "Category = ?, Modified = current_timestamp, " +
                            "ModifiedBy = 'PetStore.Pet.Api' " +
                            "where Id = ?"
            );
            stmt.setString(1, pet.getName());
            stmt.setInt(2, pet.getStatus().ordinal());
            stmt.setString(3, pet.getTags());
            stmt.setInt(4, pet.getCategory().ordinal());
            stmt.setInt(5, pet.getId());
            int affectedRows = stmt.executeUpdate();

            stmt.close();
            connection.close();

            return affectedRows;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    @Override
    public int insertPetPhoto(UUID photoId, int petId, String metaData, String url) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into pets.photos (id, petid, url, metadata, created, createdby) " +
                            "values (?, ?, ?, ?, current_timestamp, 'PetStore.Pet.Api')"
            );
            stmt.setObject(1, photoId);
            stmt.setInt(2, petId);
            stmt.setString(3, url);
            stmt.setString(4, metaData);
            stmt.executeUpdate();

            stmt.close();
            connection.close();

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    @Override
    public int deletePet(int petId) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM pets.pet where id=?"
            );
            stmt.setInt(1, petId);
            int affectedRows = stmt.executeUpdate();

            stmt.close();
            connection.close();

            return affectedRows;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    @Override
    public List<Pet> getPetByStatus(PetStatus status) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "select p.id, p.Name, p.Category, p.Status, p.Tags " +
                            "from pets.pet p where p.IsDelete = FALSE and p.status = ?"
            );
            stmt.setInt(1, status.ordinal());
            ResultSet rs = stmt.executeQuery();

            List<Pet> petList = new ArrayList<>();
            while (rs.next()) {
                petList.add(createPetFromResultSet(rs));
            }

            stmt.close();
            connection.close();

            return petList;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    private Pet createPetFromResultSet(ResultSet rs) {
        Pet pet = new Pet();
        try {
            pet.setId(rs.getInt("id"));
            pet.setCategory(PetCategory.values()[rs.getInt("category")]);
            pet.setName(rs.getString("name"));
            pet.setPhotoUrls(null);
            pet.setTags(rs.getString("tags"));
            pet.setStatus(PetStatus.values()[rs.getInt("status")]);
            return pet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
