package com.cdrussell.casterio.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

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
}