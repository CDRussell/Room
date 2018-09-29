package com.cdrussell.casterio.room.users

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cdrussell.casterio.room.DatabaseDataHolder.UserAndTheirTasks
import com.cdrussell.casterio.room.R
import kotlinx.android.synthetic.main.item_user_row.view.*


class UserListAdapter(private val deleteListener: (UserAndTheirTasks) -> Unit) :
    ListAdapter<UserAndTheirTasks, UserListAdapter.ViewHolder>(DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_user_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), deleteListener)
    }

    companion object {

        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<UserAndTheirTasks>() {

            override fun areItemsTheSame(oldItem: UserAndTheirTasks?, newItem: UserAndTheirTasks?): Boolean {
                return oldItem?.user?.id == newItem?.user?.id
            }

            override fun areContentsTheSame(oldItem: UserAndTheirTasks?, newItem: UserAndTheirTasks?): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userTask: UserAndTheirTasks, deleteListener: (UserAndTheirTasks) -> Unit) {
            itemView.userName.text = userTask.user.name
            itemView.numTasksAssigned.text = itemView.context.getString(R.string.numTasksAssigned, userTask.tasks.size)
            itemView.deleteUser.setOnClickListener { deleteListener(userTask) }
        }
    }
}