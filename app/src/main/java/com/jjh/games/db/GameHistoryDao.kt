package com.jjh.games.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jjh.games.ui.slideshow.GameHistory

@Dao
interface GameHistoryDao {
    @Insert
    fun insert(gameHistory: GameHistory): Long

    @Query("SELECT * FROM games")
    fun findAll(): List<GameHistory>
}
