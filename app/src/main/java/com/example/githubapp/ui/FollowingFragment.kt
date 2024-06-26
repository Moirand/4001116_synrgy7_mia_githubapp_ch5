package com.example.githubapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.FragmentFollowBinding
import com.example.githubapp.ui.adapter.RecyclerViewAdapter
import com.example.githubapp.ui.adapter.ViewPagerAdapter
import com.example.githubapp.ui.viewmodel.FollowViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {
    private val binding by lazy { FragmentFollowBinding.inflate(layoutInflater) }
    private val viewmodel: FollowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ViewPagerAdapter.USERNAME)
        viewmodel.getUsername(username)
        viewmodel.getFollowing()

        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            viewmodel.listFollowing.observe(viewLifecycleOwner) { listUsers ->
                adapter = listUsers?.let {
                    RecyclerViewAdapter(
                        it,
                        DetailUserFragmentDirections::actionDetailUserFragmentSelf
                    )
                }
            }
        }

        viewmodel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.list.removeAllViewsInLayout()
                binding.layoutShimmer.apply {
                    visibility = View.VISIBLE
                    startShimmer()
                }
            } else {
                binding.layoutShimmer.apply {
                    visibility = View.GONE
                    stopShimmer()
                }
            }
        }

        viewmodel.error.observe(viewLifecycleOwner) { error ->
            binding.layoutShimmer.apply {
                visibility = View.GONE
                stopShimmer()
            }
            Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
        }
    }
}