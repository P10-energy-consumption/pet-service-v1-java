package org.p10.PetStore.Models;

import java.io.File;

public class PetPhoto {
    private int petID;
    private String metaData;
    private File file;

    public PetPhoto() {
    }

    public PetPhoto(int petID, String metaData, File file) {
        this.petID = petID;
        this.metaData = metaData;
        this.file = file;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
