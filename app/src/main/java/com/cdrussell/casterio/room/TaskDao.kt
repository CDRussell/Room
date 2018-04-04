package com.cdrussell.casterio.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

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

    @Query("SELECT task.* FROM Task LEFT OUTER JOIN User ON Task.userId == User.id")
    fun getTasksAndUsers(): LiveData<List<Task>>
}