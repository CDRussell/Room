package com.cdrussell.casterio.room

import android.arch.persistence.room.*
import java.util.*

@TypeConverters(DateTypeConverter::class)
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var completed: Boolean,
    var creationDate: Date
) {

    @Ignore
    constructor(
        title: String = "",
        completed: Boolean = false,
        creationDate: Date = Date(System.currentTimeMillis())
    ) : this(0, title, completed, creationDate)
}

class DateTypeConverter {

    @TypeConverter
    fun convertFromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun convertToDate(timestamp: Long): Date {
        return Date(timestamp)
    }

}