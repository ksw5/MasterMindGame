package com.example.mastermindgame.viewmodels

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermindgame.model.network.GameApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class GameViewModel: ViewModel() {

    private val _randomNumbersDisplay = MutableLiveData<String>()
    val randomNumbersDisplay: LiveData<String>
        get() = _randomNumbersDisplay
    private val secretNumbers = _randomNumbersDisplay.value.toString()
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
        getRandomNumbers()
    }



    fun getRandomNumbers(): String? {
        viewModelScope.launch {
            _randomNumbersDisplay.value = GameApiService.retrofitService.getRandomNumbers()
            displayCorrectNumbers(secretNumbers)

        }
        return _randomNumbersDisplay.value
    }

    fun displayCorrectNumbers(numbers: String) : String {
        var display = ""
        numbers.forEach {
                display += checkNumber(it.toString())
            }
        return display
    }

    private fun checkNumber(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretNumbers.contains(guess))  {
                    correctGuesses += guess
                 _randomNumbersDisplay.value = getRandomNumbers()!!
                } else {
                    _incorrectGuesses.value += "$guess "
                    _livesLeft.value = _livesLeft.value?.minus(1)
                }
        }
        if (isWon() || isLost()) _gameOver.value = true
    }

    private fun isWon() = secretNumbers.equals((randomNumbersDisplay.value.toString()), true)
    private fun isLost() = livesLeft.value ?: 0 <= 0

    fun wonLostMessage() : String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The number was ${randomNumbersDisplay.value}"
        return message
    }

}