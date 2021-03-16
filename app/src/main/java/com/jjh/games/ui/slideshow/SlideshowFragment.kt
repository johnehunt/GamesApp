package com.jjh.games.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jjh.games.R
import com.jjh.games.db.GameHistoryRepository

import kotlinx.android.synthetic.main.fragment_slideshow.*

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProvider(this).get(SlideshowViewModel::class.java)

        slideshowViewModel.setup(requireContext())

        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      button.setOnClickListener { addGameHistory() }
  }

  private fun addGameHistory() {
    val gameId = idEditText.text.toString().toInt()
    val gameName = gameNameEditText.text.toString()
    val gameScore = scoreEditText.text.toString().toInt()
    val gameHistory = GameHistory(gameId, gameName, gameScore)
    slideshowViewModel.addGameHistory(gameHistory)
  }

}
