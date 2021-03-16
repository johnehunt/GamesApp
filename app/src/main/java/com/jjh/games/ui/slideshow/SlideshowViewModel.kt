package com.jjh.games.ui.slideshow

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjh.games.db.GameHistoryRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SlideshowViewModel : ViewModel() {

    private lateinit var repository: GameHistoryRepository

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: MutableLiveData<String> = _text

    fun setup(context: Context) {
        repository = GameHistoryRepository(context)
    }

    fun addGameHistory(gameHistory: GameHistory) {
        repository
          .insertGameHistory(gameHistory)
          .observeOn(Schedulers.newThread())
          .subscribe { updateTextLiveData() }
    }

    private fun updateTextLiveData() {
        repository
          .findAllGameHistory()
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe{
            text.value = it.toString()
          }
    }

}
