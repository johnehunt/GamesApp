package com.jjh.games.ui.guess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.provider.AlarmClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.jjh.games.R
import com.jjh.games.service.music.MusicPlayerService

import kotlinx.android.synthetic.main.fragment_guess_number.*

class GuessNumberFragment : Fragment() {

    private lateinit var invalidMessage: String
    private val viewModel by viewModels<GuessNumberViewModel>()
    private lateinit var service: MusicPlayerService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guess_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = titleTextView.text.toString() + GuessNumberViewModel.MAX_NUMBER
        invalidMessage =
            "${getString(R.string.INVALID_MSG_PROMPT)} ${GuessNumberViewModel.MAX_NUMBER})"

        guessButton.setOnClickListener {
            onGuessButtonClick()
        }

        openAlarmButton.setOnClickListener {
        onOpenAlarmButtonClick()
      }

        playMusicButton.setOnClickListener { onPlayMusicButtonClick() }
        stopMusicButton.setOnClickListener { stopMusicButtonClick() }
        stopMusicButton.isEnabled = false

        // Launch the bound service
        val intent = Intent(context, MusicPlayerService::class.java)
        activity?.bindService(intent,
            ServiceConnectionHandler(),
            Context.BIND_AUTO_CREATE)
    }

    private fun onPlayMusicButtonClick() {
        service.start()
        playMusicButton.isEnabled = false
        stopMusicButton.isEnabled = true
    }

    private fun stopMusicButtonClick() {
        service.stop()
        playMusicButton.isEnabled = true
        stopMusicButton.isEnabled = false
    }

    private fun onOpenAlarmButtonClick() {
    val intent = Intent(AlarmClock.ACTION_SET_ALARM)
    startActivity(intent)
  }

    private fun onGuessButtonClick() {
        val userInput = userGuessEditText.text.toString()
        try {
            val guessInput = userInput.toInt()
            // Check for cheat mode input
            if (guessInput == 0) {
                displayCheatToast()
            } else {
                when (viewModel.checkGuess(guessInput)) {
                    GuessNumberViewModel.CORRECT_GUESS -> {
                        messageTextView.text = getString(R.string.CORRECT_MSG) +
                                " ${viewModel.numberToGuess} ${getString(R.string.NEW_NUMBER_MSG)}"
                        resetGame()
                    }
                    GuessNumberViewModel.INVALID_GUESS -> displayInvalidUserInputMessage()
                    GuessNumberViewModel.GUESS_HIGHER -> displayHintMessage(GuessNumberViewModel.GUESS_HIGHER)
                    GuessNumberViewModel.GUESS_LOWER -> displayHintMessage(GuessNumberViewModel.GUESS_LOWER)
                }

                // Check for max number of guesses
                if (viewModel.maxNumberOfGuessReached) {
                    messageTextView.text = "${getString(R.string.MAX_MESSAGE_LIMIT_MSG)} " +
                            "${viewModel.numberToGuess} ${getString(R.string.NEW_NUMBER_MSG)}"
                    resetGame()
                }

                guessesLeftTextView.text = "${getString(R.string.GUESSES_LEFT_MSG)} " +
                        "${viewModel.remainingGuesses}"

            }
        } catch (e: NumberFormatException) {
            displayInvalidUserInputMessage()
        }
    }

    private fun displayInvalidUserInputMessage() {
        messageTextView.text = invalidMessage
    }


    private fun resetGame() {
        userGuessEditText.setText("")
        viewModel.resetGameData()
    }

    private fun displayHintMessage(status: Int) {
        val hint = if (status == GuessNumberViewModel.GUESS_HIGHER) {
            getString(R.string.LOWER_MSG)
        } else {
            getString(R.string.HIGHER_MSG)
        }
        messageTextView.text = hint
    }

    private fun displayCheatToast() {
        val message = "${getString(R.string.HINT_MSG)}  ${viewModel.numberToGuess}"
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

  // Service Connection Listener
  private inner class ServiceConnectionHandler : ServiceConnection {
    override fun onServiceConnected(
      className: ComponentName,
      binder: IBinder) {

      // We've bound to LocalService, cast the IBinder and get LocalService instance
      val demoBinder = binder as MusicPlayerService.MusicServiceBinder
      service = demoBinder.service
      Log.d("ServiceConnection", "MusicPlayerService bound")
    }

    override fun onServiceDisconnected(name: ComponentName?) {
      Log.d("ServiceConnection", "onServiceDisconnected")
    }

  }

}
