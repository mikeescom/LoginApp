package com.mikeescom.loginapp.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE userId LIKE :userId AND " +
            "password LIKE :psw LIMIT 1")
    LiveData<User> findByUserIdAndPsw(String userId, String psw);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
