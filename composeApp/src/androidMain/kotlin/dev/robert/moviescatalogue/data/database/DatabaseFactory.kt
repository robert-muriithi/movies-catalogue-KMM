package dev.robert.moviescatalogue.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<SavedMoviesDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("saved_movies.db")
    return Room.databaseBuilder(
        context = appContext,
        name = dbFile.absolutePath
    )
}
