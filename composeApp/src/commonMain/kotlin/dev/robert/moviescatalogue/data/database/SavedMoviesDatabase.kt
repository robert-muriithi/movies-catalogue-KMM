package dev.robert.moviescatalogue.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.robert.moviescatalogue.data.dao.SavedMovieDao
import dev.robert.moviescatalogue.data.entity.SavedMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [SavedMovie::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class SavedMoviesDatabase : RoomDatabase() {
    abstract fun savedMovieDao(): SavedMovieDao
}


@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<SavedMoviesDatabase> {
    override fun initialize(): SavedMoviesDatabase
}


fun getRoomDatabase(
    builder: RoomDatabase.Builder<SavedMoviesDatabase>
): SavedMoviesDatabase {
    return builder
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
