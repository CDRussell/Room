package com.cdrussell.casterio.room

import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cdrussell.casterio.room.DatabaseDataHolder.TaskAndItsUsers
import com.cdrussell.casterio.room.TaskListAdapter.ViewHolder
import com.cdrussell.casterio.room.users.User
import kotlinx.android.synthetic.main.item_task_row.view.*


class TaskListAdapter(private val clickListener: (TaskAndItsUsers) -> Unit) :
    ListAdapter<TaskAndItsUsers, ViewHolder>(DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_task_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    companion object {

        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<TaskAndItsUsers>() {

            override fun areItemsTheSame(oldItem: TaskAndItsUsers, newItem: TaskAndItsUsers): Boolean {
                return oldItem.task.id == newItem.task.id
            }

            override fun areContentsTheSame(oldItem: TaskAndItsUsers, newItem: TaskAndItsUsers): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(taskAndUsers: TaskAndItsUsers, clickListener: (TaskAndItsUsers) -> Unit) {
            val context = itemView.context
            val task = taskAndUsers.task
            val users = taskAndUsers.users
            itemView.taskTitle.text = task.title
            itemView.assignee.text = buildAssigneeString(context, users)
            itemView.setOnClickListener { clickListener(taskAndUsers) }
        }

        private fun buildAssigneeString(context: Context, users: List<User>): String {
            if (users.isEmpty()) return context.getString(R.string.assignedLabel, context.getString(R.string.unassigned))
            val assignee = users.joinToString(separator = ", ", transform = { it.name })
            return context.getString(R.string.assignedLabel, assignee)
        }
    }
}