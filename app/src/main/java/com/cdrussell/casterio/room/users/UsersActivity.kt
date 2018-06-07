package com.cdrussell.casterio.room.users

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.widget.TextView
import com.cdrussell.casterio.room.AppDatabase
import com.cdrussell.casterio.room.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_users.*
import kotlin.concurrent.thread

class UsersActivity : AppCompatActivity() {

    private lateinit var userListAdapter: UserListAdapter
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        setSupportActionBar(toolbar)

        database = AppDatabase.getInstance(this)
        userDao = database.userDao()


        addUserButton.setOnClickListener {
            addUser()
            userNameInput.setText("")
        }

        userNameInput.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_DONE) {
                addUser()
                userNameInput.setText("")
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })

        userListAdapter = UserListAdapter({
            thread { userDao.delete(it.user) }
        })

        userList.layoutManager = LinearLayoutManager(this)
        userList.adapter = userListAdapter


        userDao.getAllUsersAndTasks().observe(this, Observer<List<UserDao.UserAndTasks>> {
            userListAdapter.submitList(it)
        })
    }

    private fun addUser() {
        val title = userNameInput.text.toString()

        if (title.isBlank()) {
            Snackbar.make(toolbar, "User's name is required", Snackbar.LENGTH_SHORT).show()
            return
        }

        val user = User(name = title)

        thread {
            userDao.insert(user)
        }
    }
}
