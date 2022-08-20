package com.smartestmedia.task.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.smartestmedia.task.R
import com.smartestmedia.task.databinding.FragmentDetailsBinding
import com.smartestmedia.task.ui.DetailsFragmentArgs.Companion.fromBundle
import com.smartestmedia.task.utils.getNavOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val repoDetails by lazy {
        fromBundle(requireArguments()).details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        setupView()
    }

    private fun setupView() {

        binding.apply {
            titlePostDetailsDescriptionTv.text = repoDetails.name.toString()
            backSpaceImg.setOnClickListener {
                findNavController().popBackStack()
                findNavController().navigate(
                    R.id.home,
                    null,
                    getNavOptions
                )
            }
            createdPostDetailsDescriptionTv.text = repoDetails.createdAt.toString()
            stargazersPostDetailsDescriptionTv.text = repoDetails.stargazersCount.toString()
            val imageLink = repoDetails.owner?.avatarUrl
            ownerImg.load(imageLink) {
                crossfade(true)
                crossfade(1000)
            }
        }


    }
}