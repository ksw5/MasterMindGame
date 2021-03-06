package com.example.mastermindgame.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mastermindgame.databinding.FragmentGameBinding
import com.example.mastermindgame.utils.StringUtils
import com.example.mastermindgame.viewmodels.GameViewModel

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel
    private val stringUtils = StringUtils()

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

        viewModel.gameOver.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(
                        viewModel.wonLostMessage()
                    )
                view.findNavController().navigate(action)
            }
        })


        viewModel.randomNumbersDisplay.observe(viewLifecycleOwner){
            binding.guessButton.setOnClickListener {
                val guess = binding.guess.text.toString()
                viewModel.makeGuess(guess)
                if (guess.length < 4) {
                    Toast.makeText(context, "Please enter four numbers", Toast.LENGTH_SHORT).show()
                }
                for (i in 0 until guess.length) {
                    if (viewModel.randomNumbersDisplay.value?.get(i) == guess[i]) {
                        Toast.makeText(context, "One of your numbers is correct and in the correct position", Toast.LENGTH_SHORT).show()
                    } else if (viewModel.randomNumbersDisplay.value?.contains(guess) == true) {
                        Toast.makeText(context, "One of your numbers is correct but in the wrong position", Toast.LENGTH_SHORT).show()
                    } else if (!viewModel.randomNumbersDisplay.value?.contains(guess)!!) {
                        Toast.makeText(context, "None of your numbers are correct", Toast.LENGTH_SHORT).show()
                    }
                }

                binding.guess.text = null
            }
        }


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}