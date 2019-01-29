package com.ziyata.databasesiswa.db;

import   android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ziyata.databasesiswa.model.KelasModel;
import com.ziyata.databasesiswa.model.SiswaModel;

@Database(entities = {KelasModel.class, SiswaModel.class}, version = 1)
// Mewarisi Room dengan extend RoomDatabase
public abstract class SiswaDatabase extends RoomDatabase{

    public abstract KelasDao kelasDao();

    private static SiswaDatabase INSTANCE;

    // Membuat method untuk membuat database
    public static SiswaDatabase createDatbase(Context context){
        synchronized (SiswaDatabase.class){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, SiswaDatabase.class, "db_siswa").allowMainThreadQueries().build();
            }
        }return  INSTANCE;
    }
}
