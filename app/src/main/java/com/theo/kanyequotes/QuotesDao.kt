package com.theo.kanyequotes

import androidx.room.*


@Dao
interface QuotesDao {

    @Query("SELECT * FROM quotes_table")
    fun getAll() : MutableList<quote>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(iquote : quote)

    @Delete
    suspend fun delete(Dquote : quote)

}