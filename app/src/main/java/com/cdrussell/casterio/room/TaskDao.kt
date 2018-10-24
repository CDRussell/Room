package com.cdrussell.casterio.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.cdrussell.casterio.room.DatabaseDataHolder.AssignedTask
import com.cdrussell.casterio.room.DatabaseDataHolder.TaskUserPair

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task): Long

    @Insert
    fun insertAll(tasks: List<Task>): List<Long>

    @Query("SELECT * FROM Task")
    fun getAll(): LiveData<List<Task>>

    @Transaction
    @Query("SELECT Task.id as task_id, Task.completed as task_completed, Task.title as task_title, Task.creationDate as task_creationDate, Task.notes as task_notes, AssignedTask.user, User.* FROM Task LEFT OUTER JOIN AssignedTask on Task.id = AssignedTask.task LEFT OUTER JOIN User on AssignedTask.user = User.id")
    fun getAllWithAssignedUsers(): LiveData<List<TaskUserPair>>

    @Query("SELECT Task.id as task_id, Task.completed as task_completed, Task.title as task_title, Task.creationDate as task_creationDate, Task.notes as task_notes, AssignedTask.user, User.* FROM Task LEFT OUTER JOIN AssignedTask on Task.id = AssignedTask.task LEFT OUTER JOIN User on AssignedTask.user = User.id WHERE Task.id = :taskId")
    fun getAssignedUsers(taskId: Int): LiveData<List<TaskUserPair>>

    @Query("SELECT * FROM Task WHERE id = :taskId")
    fun getTask(taskId: Int): LiveData<Task>

    @Delete()
    fun delete(task: Task)

    @Update
    fun update(task: Task)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAssignedTask(assignedTask: AssignedTask)

    @Query("DELETE FROM AssignedTask WHERE AssignedTask.task = :taskId")
    fun removeAllAssignedUsers(taskId: Int)

}