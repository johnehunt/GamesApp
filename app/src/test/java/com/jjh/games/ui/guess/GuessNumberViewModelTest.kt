package com.jjh.games.ui.guess

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class GuessNumberViewModelTest {

    private lateinit var viewModel: GuessNumberViewModel

    @Before
    fun setUp() {
      viewModel = GuessNumberViewModel()
    }

    @Test
    fun getGuessCountInitialValue() {
        val count = viewModel.guessCount
        assertEquals("count should be zero to start",
                    0, count)
    }

    @Test
    fun getGuessCountValueAfterAGuess() {
        viewModel.checkGuess(4)
        val count = viewModel.guessCount
        assertEquals("count should be zero to start",
      1, count)
    }

    @Test
    fun notGetMaxNumberOfGuessReached() {
        val hasReachedMaxNumberOfGuesses = viewModel.maxNumberOfGuessReached
        assertEquals("Initially should nto have reached max",
            false, hasReachedMaxNumberOfGuesses)
    }
}
