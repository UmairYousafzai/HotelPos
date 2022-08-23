package com.softvalley.hotelpos.database;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.models.Party;
import com.softvalley.hotelpos.models.User;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertUser(User user);

    @Query("Delete from User")
    void deleteUsers( );

    @Query("Select *from user")
    LiveData<List<User>> getUsers();

    @Insert
    void insertParties(List<Party> partyList);

    @Insert
    void insertParty(Party party);

    @Query("Delete from Party")
    void deleteParties( );

    @Query("Delete from Party where partyCode=:partyCode")
    void deleteParty( String partyCode);

    @Query("Select *from Party  where partyType=:type order by partyName asc  ")
    LiveData<List<Party>> getParties(String type);

    @Insert
    void insertItem(List<Item> items);

    @Insert
    void insertItem(Item item);

    @Query("Delete from item")
    void deleteItem( );

}
