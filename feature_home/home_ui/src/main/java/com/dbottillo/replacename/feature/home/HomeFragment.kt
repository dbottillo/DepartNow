package com.dbottillo.replacename.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dbottillo.replacename.Lce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_HomeDetailFragment)
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.data.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is Lce.Loading -> {}
                    is Lce.Data -> {
                        view?.findViewById<TextView>(R.id.textview_first)?.text = it.data.title
                    }
                    is Lce.Error -> {
                        view?.findViewById<TextView>(R.id.textview_first)?.text = it.throwable.localizedMessage
                    }
                }
            }
        )
    }
}
