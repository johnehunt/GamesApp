package com.jjh.games.ui.gallery

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jjh.games.R
import com.jjh.games.ui.guess.GuessNumberViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private val galleryViewModel by viewModels<GalleryViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
          text_gallery.text = it
        })

        getDriversButton.setOnClickListener { getDrivers() }
    }

    private fun getDrivers() {
        galleryViewModel.loadDriverData()
        // GetRequestAsyncTask().execute()
    }

//    inner class GetRequestAsyncTask: AsyncTask<Unit, Unit, String>() {
//
//        override fun doInBackground(vararg params: Unit?): String {
//            val client = OkHttpClient()
//            val request: Request = Request.Builder()
//              .url("http://ergast.com/api/f1/2020/drivers.json")
//              .get()
//              .build()
//
//            return try {
//              val response: Response =
//                client
//                    .newCall(request)
//                    .execute()
//              response.body?.string() ?: ""
//            } catch (exp: Throwable) {
//              Log.d("GalleryFragment", exp.localizedMessage)
//              "Error Generate - $exp"
//            }
//        }
//
//        override fun onPostExecute(result: String) {
//            super.onPostExecute(result)
//           galleryViewModel.text.value =result
//        }
//
//    }


  }
