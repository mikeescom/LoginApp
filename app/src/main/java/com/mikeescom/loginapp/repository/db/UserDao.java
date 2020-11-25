package com.mikeescom.loginapp.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE userId LIKE :userId AND " +
            "password LIKE :psw LIMIT 1")
    LiveData<User> findByUserIdAndPsw(String userId, String psw);

    @Query("SELECT * FROM user WHERE id LIKE :id LIMIT 1")
    LiveData<User> findByUserId(int id);

    @Insert
    long insert(User user);
}
