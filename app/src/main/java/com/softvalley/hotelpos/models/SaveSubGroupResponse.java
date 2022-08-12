package com.softvalley.hotelpos.models;

import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.response.ServerResponse;

public class SaveSubGroupResponse extends ServerResponse {

    @SerializedName("Data")
    private SubGroup subGroup;

    public SubGroup getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(SubGroup subGroup) {
        this.subGroup = subGroup;
    }
}
