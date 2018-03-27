package com.cdrussell.casterio.room

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_task_details.*
import kotlin.concurrent.thread
import kotlinx.android.synthetic.main.activity_task_details.taskId as taskIdInput

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var taskDao: TaskDao
    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        taskDao = AppDatabase.getInstance(this).taskDao()
        val taskId = extractTaskId()
        taskDao.getTask(taskId).observe(this, Observer<Task> {
            if (it == null) {
                finish()
                return@Observer
            }

            taskTitle.text = it.title
            taskIdInput.text = it.id.toString()

            taskCompletionCheckbox.isChecked = it.completed
            task = it
        })

        taskCompletionCheckbox.setOnCheckedChangeListener { _, isChecked ->
            task?.let {

                // update the task in the DB
                it.completed = isChecked
                thread { taskDao.update(it) }
            }
        }
    }

    private fun extractTaskId(): Int {
        if (!intent.hasExtra(TASK_ID)) {
            throw IllegalArgumentException("$TASK_ID must be provided to start this Activity")
        }
        return intent.getIntExtra(TASK_ID, Int.MIN_VALUE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_delete_task -> {

                task?.let {
                    thread { taskDao.delete(it) }
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val TASK_ID = "INTENT_TASK_ID"

        fun launchIntent(context: Context, taskId: Int): Intent {
            val intent = Intent(context, TaskDetailsActivity::class.java)
            intent.putExtra(TASK_ID, taskId)
            return intent
        }
    }
}
