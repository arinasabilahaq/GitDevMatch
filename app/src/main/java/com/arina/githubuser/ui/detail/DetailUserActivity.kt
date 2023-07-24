package com.arina.githubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arina.githubuser.databinding.ActivityDetailUserBinding
import com.arina.githubuser.ui.adapter.SectionPagerAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val profileImg = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle().apply {
            putString(EXTRA_USERNAME, username)
        }


        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        showLoading(true)

        viewModel.setUserDetail(username.toString())

        viewModel.getUserDetail().observe(this) { user ->
            user?.let {
                binding.apply {
                    tvUsersDetail.text = it.name
                    tvUsernameDetail.text = it.login
                    tvFollowersDetail.text = "${it.followers} Followers"
                    tvFollowingDetail.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(user.avatar_url)
                        .centerCrop()
                        .into(profileImageDetail)
                }
            }
        }

        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                count?.let {
                    binding.toggleFavorite.isChecked = it > 0
                    checked = it > 0
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            checked = !checked
            if (checked) {
                username?.let { name ->
                    profileImg?.let { avatar ->
                        viewModel.addToFavorite(name, id, avatar)
                    }
                }
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = checked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        viewModel.getUserDetail().observe(this) { userDetail ->
            userDetail?.let {
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}