package com.cdrussell.casterio.room.users

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface UserDao {

    @Insert
    fun insert(user: User): Long

    @Insert
    fun insertAll(users: List<User>): List<Long>

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE id = :userId")
    fun getUser(userId: Int): LiveData<User>

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

}