package com.arina.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arina.githubuser.data.database.FavoriteUser
import com.arina.githubuser.data.model.User
import com.arina.githubuser.databinding.ActivityFavoriteBinding
import com.arina.githubuser.ui.adapter.UserAdapter
import com.arina.githubuser.ui.detail.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter : UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite's User"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) { userList ->
            userList?.let {
                adapter.setList(mapList(it))
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users ){
            val userMapped= User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}