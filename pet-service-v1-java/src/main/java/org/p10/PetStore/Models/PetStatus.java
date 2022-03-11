package org.p10.PetStore.Models;

import com.google.gson.annotations.SerializedName;

public enum PetStatus {
    @SerializedName("0")
    Available,
    @SerializedName("1")
    Pending,
    @SerializedName("2")
    Sold
}
