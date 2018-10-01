package com.cdrussell.casterio.room

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import com.cdrussell.casterio.room.users.User

class DatabaseDataHolder {

    @Entity(
        primaryKeys = ["user", "task"],
        foreignKeys = [
            ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user"], onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = Task::class, parentColumns = ["id"], childColumns = ["task"], onDelete = ForeignKey.CASCADE)
        ]
    )
    class AssignedTask {
        var task: Int = 0
        var user: Int = 0
    }

    class UserTaskPair {
        @Embedded
        lateinit var user: User

        @Embedded(prefix = "task_")
        var task: Task? = null
    }

    class TaskUserPair {
        @Embedded(prefix = "task_")
        lateinit var task: Task

        @Embedded
        var user: User? = null
    }

    data class TaskAndItsUsers(
        val task: Task,
        val users: List<User>
    )

    data class UserAndTheirTasks(
        val user: User,
        val tasks: List<Task>
    )

    companion object {

        /**
         * Accepts a collection of Task<->User? pairs,
         * a Task might appear with a null User if the task is unassigned
         * or the same Task might appear multiple times in the list if it has multiple Users assigned to it
         */
        fun groupTasks(taskAndUsers: List<TaskUserPair>): List<TaskAndItsUsers> {
            return mutableListOf<TaskAndItsUsers>().also { items ->
                taskAndUsers.groupBy(keySelector = { it.task }, valueTransform = { it.user })
                    .forEach { items.add(TaskAndItsUsers(it.key, it.value.filterNotNull())) }
            }
        }

        /**
         * Accepts a collection of User<->Task? pairs,
         * a User might appear with a null Task if the User has no tasks assigned to them
         * or the same User might appear multiple times in the list if it has multiple Tasks assigned to it
         */
        fun groupUsers(userAndTasks: List<UserTaskPair>): List<UserAndTheirTasks> {
            return mutableListOf<UserAndTheirTasks>().also { items ->
                userAndTasks
                    .groupBy(keySelector = { it.user }, valueTransform = { it.task })
                    .forEach { items.add(UserAndTheirTasks(it.key, it.value.filterNotNull())) }
            }
        }
    }
}

