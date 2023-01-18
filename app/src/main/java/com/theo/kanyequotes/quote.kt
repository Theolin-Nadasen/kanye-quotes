package com.theo.kanyequotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
data class quote(
    @PrimaryKey val quote: String
)