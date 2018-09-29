package com.cdrussell.casterio.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var completed: Boolean
) {

    @Ignore
    constructor(
        title: String = "",
        completed: Boolean = false
    ) : this(0, title, completed)
}