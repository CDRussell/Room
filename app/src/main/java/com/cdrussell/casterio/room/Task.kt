package com.cdrussell.casterio.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class Task(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                var title: String = "",
                var completed: Boolean = false)