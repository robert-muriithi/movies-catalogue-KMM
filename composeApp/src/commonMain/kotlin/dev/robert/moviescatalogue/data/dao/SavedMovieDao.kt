package dev.robert.moviescatalogue.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.robert.moviescatalogue.data.entity.SavedMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedMovieDao {
    @Query("SELECT * FROM saved")
    fun getAll(): Flow<List<SavedMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: SavedMovie)

    @Query("DELETE FROM saved WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM saved WHERE id = :id)")
    fun isSaved(id: Int): Flow<Boolean>
}