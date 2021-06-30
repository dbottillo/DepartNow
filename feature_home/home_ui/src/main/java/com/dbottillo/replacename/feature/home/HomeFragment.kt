package com.dbottillo.replacename.feature.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.feature.home.databinding.FragmentHomeBinding
import com.dbottillo.replacename.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_HomeDetailFragment)
        }

        setupObservers()
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.data.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is Lce.Loading -> {
                        view?.findViewById<TextView>(R.id.textview_first)?.text = "Loading"
                    }
                    is Lce.Data -> {
                        view?.findViewById<TextView>(R.id.textview_first)?.text = it.data.title
                    }
                    is Lce.Error -> {
                        view?.findViewById<TextView>(R.id.textview_first)?.text =
                            it.throwable.localizedMessage
                    }
                }
            }
        )
    }
}
