package com.jjh.games.ui.slideshow

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="games")
data class GameHistory(
  @PrimaryKey val id: Int,
  val name: String,
  val score: Int
)
