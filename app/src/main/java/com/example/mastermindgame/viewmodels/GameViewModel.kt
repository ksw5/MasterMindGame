package com.example.mastermindgame.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermindgame.model.network.GameApiService
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {


    private val _randomNumbersDisplay = MutableLiveData<String>()
    val randomNumbersDisplay: LiveData<String>
        get() = _randomNumbersDisplay
    val secretNumbers = _randomNumbersDisplay.value?.let { listOf(it.indices) }
    private var correctGuesses = ""
    private val _incorrectGuesses = MutableLiveData("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses
    private val _livesLeft = MutableLiveData(10)
    val livesLeft: LiveData<Int>
        get() = _livesLeft

    private val _gameOver = MutableLiveData(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        _randomNumbersDisplay.value = getRandomNumbers()
    }


    fun getRandomNumbers(): String {
        viewModelScope.launch {
            _randomNumbersDisplay.value = GameApiService.retrofitService.getRandomNumbers()
            displayCorrectNumbers()
        }
        return _randomNumbersDisplay.value.toString()

    }


    fun displayCorrectNumbers(): String {
        var display = ""
        secretNumbers.toString().forEach {
            display += checkNumber(it.toString())
        }
        return display
    }

    private fun checkNumber(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {
        if (guess.length in 1..4) {
            if (secretNumbers.toString().contains(guess)) {
                correctGuesses += guess
                _randomNumbersDisplay.value = displayCorrectNumbers()
            } else {
                _incorrectGuesses.value += "$guess "
                _livesLeft.value = _livesLeft.value?.minus(1)
            }
        }
        if (isWon() == true || isLost()) _gameOver.value = true
    }

    private fun isWon() = secretNumbers?.equals((randomNumbersDisplay.value))
    private fun isLost() = livesLeft.value ?: 0 <= 0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon() == true) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The number was ${randomNumbersDisplay.value}"
        return message
    }

}

