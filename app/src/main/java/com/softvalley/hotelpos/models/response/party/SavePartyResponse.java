package com.softvalley.hotelpos.models.response.party;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.Party;
import com.softvalley.hotelpos.models.response.ServerResponse;

public class SavePartyResponse extends ServerResponse {

    @SerializedName("Data")
    @Expose
    private Party party;

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
