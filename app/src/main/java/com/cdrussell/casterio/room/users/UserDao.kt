package com.cdrussell.casterio.room.users

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.cdrussell.casterio.room.DatabaseDataHolder.UserTaskPair

@Dao
interface UserDao {

    @Insert
    fun insert(user: User): Long

    @Insert
    fun insertAll(users: List<User>): List<Long>

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Transaction
    @Query("SELECT Task.id as task_id, Task.completed as task_completed, Task.title as task_title, AssignedTask.user, User.* FROM User LEFT OUTER JOIN AssignedTask on User.id = AssignedTask.user LEFT OUTER JOIN Task on AssignedTask.task = Task.id")
    fun getAllWithAssignedTasks(): LiveData<List<UserTaskPair>>
}