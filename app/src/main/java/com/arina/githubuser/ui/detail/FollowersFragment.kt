package com.arina.githubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arina.githubuser.R
import com.arina.githubuser.databinding.FragmentFollowBinding
import com.arina.githubuser.ui.adapter.UserAdapter

class FollowersFragment: Fragment(R.layout.fragment_follow){

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = requireNotNull(_binding)
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments
        val username = arguments?.getString(DetailUserActivity.EXTRA_USERNAME).orEmpty()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()


        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
        viewModel.setListFollowers(username)

        viewModel.getListFollowers().observe(viewLifecycleOwner) { followers ->
            followers?.let {
                adapter.setList(followers)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
