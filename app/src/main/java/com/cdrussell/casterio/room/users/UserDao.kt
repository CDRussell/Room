package com.cdrussell.casterio.room.users

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.cdrussell.casterio.room.Task

@Dao
interface UserDao {

    @Insert
    fun insert(user: User): Long

    @Insert
    fun insertAll(users: List<User>): List<Long>

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Transaction
    @Query("SELECT * FROM User")
    fun getAllUsersAndTasks(): LiveData<List<UserAndTasks>>

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    class UserAndTasks {

        @Embedded
        lateinit var user: User

        @Relation(parentColumn = "id", entityColumn = "userId")
        lateinit var tasks: List<Task>

    }


}