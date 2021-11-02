package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDao {
    /**
     * When we return the LiveData, room will do a database query in a background for us
     * It will update the LiveData anytime the new data is written in the table
     */
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}

@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase : RoomDatabase() {
    abstract val videoDao: VideoDao
}

private lateinit var INSTANCE: VideosDatabase

fun getDatabase(context: Context): VideosDatabase {
    synchronized(VideosDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                VideosDatabase::class.java,
                "videos").build()
        }
    }
    return INSTANCE
}