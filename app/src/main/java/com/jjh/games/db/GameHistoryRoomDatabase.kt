package com.jjh.games.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jjh.games.ui.slideshow.GameHistory

@Database(entities = [GameHistory::class],
          version = 1)
abstract class GameHistoryRoomDatabase : RoomDatabase() {

  abstract fun gameHistoryDao(): GameHistoryDao

  companion object {

    private var _SINGLETON: GameHistoryRoomDatabase? = null

    internal fun getDatabase(context: Context): GameHistoryRoomDatabase {
      if (_SINGLETON == null) {
        synchronized(GameHistoryRoomDatabase::class.java) {
          if (_SINGLETON == null) {
            _SINGLETON = Room.databaseBuilder(
              context.applicationContext,
              GameHistoryRoomDatabase::class.java,
              "games_db"
            ).build()
          }
        }
      }
      return _SINGLETON!!
    }
  }

}
