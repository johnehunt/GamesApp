package com.jjh.games.db

import android.content.Context
import com.jjh.games.ui.slideshow.GameHistory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class GameHistoryRepository(private val context: Context) {

    private val gameHistoryDao =
      GameHistoryRoomDatabase.getDatabase(context).gameHistoryDao()

    fun insertGameHistory(gameHistory: GameHistory): Observable<Long> {
        return Observable.create<Long> {
          it.onNext(gameHistoryDao.insert(gameHistory))
          it.onComplete()
        }.subscribeOn(Schedulers.newThread())
    }

    fun findAllGameHistory(): Observable<List<GameHistory>> {
        return Observable.create<List<GameHistory>> {
          it.onNext(gameHistoryDao.findAll())
          it.onComplete()
        }.subscribeOn(Schedulers.newThread())
    }

}
