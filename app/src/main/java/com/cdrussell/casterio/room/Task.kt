package com.cdrussell.casterio.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var completed: Boolean,
    var userId: Int?
) {

    @Ignore
    constructor(
        title: String = "",
        completed: Boolean = false,
        userId: Int? = null
    ) : this(0, title, completed, userId)
}