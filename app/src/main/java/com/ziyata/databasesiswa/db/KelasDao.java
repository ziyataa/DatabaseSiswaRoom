package com.ziyata.databasesiswa.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ziyata.databasesiswa.model.KelasModel;
import com.ziyata.databasesiswa.model.SiswaModel;

import java.util.List;

@Dao
public interface KelasDao {

    // Mengambil data
    @Query("SELECT * FROM KELAS ORDER BY nama_kelas ASC")
    List<KelasModel> select();

    // Memasukkan data
    @Insert
    void insert(KelasModel kelasModels);

    // Menghapus data
    @Delete
    void delete(KelasModel kelasModel);

    // Mengupdate data
    @Update
    void update(KelasModel kelasModel);

    // Mengambil data siswa
    @Query("SELECT * FROM TB_SISWA WHERE id_kelas = :id_kelas ORDER BY nama_siswa ASC")
    List<SiswaModel> selectSiswa(int id_kelas);

    @Insert
    void insertSiswa(SiswaModel siswaModel);

    @Delete
    void deleteSiswa(SiswaModel siswaModel);

    @Update
    void updateSiswa(SiswaModel siswaModel);
}
