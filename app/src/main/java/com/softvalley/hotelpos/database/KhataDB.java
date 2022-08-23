package com.softvalley.hotelpos.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.models.Party;
import com.softvalley.hotelpos.models.User;
import com.softvalley.hotelpos.utils.Converter;

@Database(entities = {User.class, Party.class, Item.class},version =1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class KhataDB extends RoomDatabase {

    private static KhataDB khataDB;
    private static final String DataBaseName= "KhataDB";

    public synchronized static KhataDB getInstance(Context context)
    {
        if (khataDB==null)
        {
            khataDB = Room.databaseBuilder(context.getApplicationContext(),KhataDB.class,DataBaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return khataDB;
    }

    public abstract Dao dao();
}
