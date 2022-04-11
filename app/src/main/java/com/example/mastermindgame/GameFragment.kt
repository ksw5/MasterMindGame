package com.example.mastermindgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mastermindgame.databinding.FragmentGameBinding
import com.example.mastermindgame.viewmodels.GameViewModel

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
}