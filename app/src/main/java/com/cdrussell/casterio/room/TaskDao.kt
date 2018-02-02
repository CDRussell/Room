package com.cdrussell.casterio.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task): Long

    @Insert
    fun insertAll(tasks: List<Task>): List<Long>
}