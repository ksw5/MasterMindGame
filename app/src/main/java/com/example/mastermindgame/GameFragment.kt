package com.example.mastermindgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mastermindgame.databinding.FragmentGameBinding
import com.example.mastermindgame.viewmodels.GameViewModel
import javax.security.auth.callback.Callback

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString())
            binding.guess.text = null

        }




        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

/*fun makeGuess(guess: String) {
        if (guess.length == 1) {
           if (.contains(guess))  {
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
    }*/

}