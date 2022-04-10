package com.example.mastermindgame.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermindgame.model.network.GameApiService
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    private val _randomNumbersDisplay = MutableLiveData<String?>()
    val randomNumbersDisplay: LiveData<String?>
        get() = _randomNumbersDisplay

    private var correctGuesses = ""
    private val _incorrectGuesses = MutableLiveData<String>("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses
    private val _livesLeft = MutableLiveData<Int>(10)
    val livesLeft: LiveData<Int>
        get() = _livesLeft
    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        _randomNumbersDisplay.value = getRandomNumbers()
    }

    fun getRandomNumbers() : String? {
        var display = ""
        viewModelScope.launch {
            _randomNumbersDisplay.value =
                GameApiService.retrofitService.getRandomNumbers()
            display 
        }

        return display
    }

    private fun checkNumber(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
           if (_randomNumbersDisplay.value!!.contains(guess))  {
               correctGuesses += guess
               _randomNumbersDisplay.value = getRandomNumbers()!!
           } else {
               _incorrectGuesses.value += "$guess "
               _livesLeft.value = _livesLeft.value?.minus(1)
           }
        }
        if (isWon() || isLost()) _gameOver.value = true
    }

    private fun isWon() = randomNumbersDisplay.value.equals((randomNumbersDisplay.value))
    private fun isLost() = livesLeft.value ?: 0 <= 0

    fun wonLostMessage() : String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The number was ${randomNumbersDisplay.value}"
        return message
    }

}