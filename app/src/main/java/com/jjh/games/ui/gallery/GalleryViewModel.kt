package com.jjh.games.ui.gallery

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class GalleryViewModel : ViewModel() {

    companion object {
        private const val TAG = "GalleryViewModel"
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: MutableLiveData<String> = _text

    fun loadDriverData() {
        Log.d(TAG, "loadDriverData()")
        Observable.create<String> {
          val client = OkHttpClient()
          val request: Request = Request.Builder()
            .url("http://ergast.com/api/f1/2020/drivers.json")
            .get().build()

          val resultString = try {
            val response: Response =
              client.newCall(request).execute()
            response.body?.string() ?: ""
          } catch (exp: Throwable) {
            Log.d("GalleryFragment", exp.localizedMessage)
            "Error Generate - $exp"
          }

          it.onNext(resultString)
          it.onComplete()
        }.subscribeOn(Schedulers.newThread())  // run the observable in new thread
          .observeOn(AndroidSchedulers.mainThread())
          .doOnNext { Log.d(TAG, "returned value: $it") }
          .doOnComplete { Log.d(TAG,"It completed") }
          .doOnError { Log.d(TAG, "Error: $it") }
          .subscribe {
              text.value = it
         }
    }

}
