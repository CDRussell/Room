package com.cdrussell.casterio.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.cdrussell.casterio.room.users.User

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task): Long

    @Insert
    fun insertAll(tasks: List<Task>): List<Long>

    @Query("SELECT * FROM Task")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :taskId")
    fun getTask(taskId: Int): LiveData<Task>

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)

    @Query("SELECT Task.* FROM Task LEFT OUTER JOIN User ON Task.userId == User.id")
    fun getTasksAndUsers(): LiveData<List<Task>>

    @Query("SELECT Task.*, User.id as user_id, User.name as user_name FROM Task LEFT OUTER JOIN User ON Task.userId == User.id WHERE Task.id = :taskId")
    fun getTaskAndUser(taskId: Int): LiveData<UserTask>

    data class UserTask(@Embedded (prefix = "user_") var user: User?,
                        @Embedded var task: Task?)
}