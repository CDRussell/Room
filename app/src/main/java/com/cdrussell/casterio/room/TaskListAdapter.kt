package com.cdrussell.casterio.room

import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cdrussell.casterio.room.TaskListAdapter.ViewHolder
import kotlinx.android.synthetic.main.item_task_row.view.*


class TaskListAdapter(private val clickListener: (Task) -> Unit) :
    ListAdapter<Task, ViewHolder>(DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_task_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    companion object {

        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Task>() {

            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: Task, clickListener: (Task) -> Unit) {
            val context = itemView.context
            itemView.taskTitle.text = task.title
            itemView.assignee.text = buildAssigneeString(context, task)
            itemView.setOnClickListener { clickListener(task) }
        }

        private fun buildAssigneeString(context: Context, task: Task): String {
            val assignee= task.userId?.toString() ?: context.getString(R.string.unassigned)
            return context.getString(R.string.assignedLabel, assignee)
        }

    }
}