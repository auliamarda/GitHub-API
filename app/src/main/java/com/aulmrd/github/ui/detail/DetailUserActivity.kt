package com.aulmrd.github.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.aulmrd.github.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME ="extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewmodel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewmodel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        viewmodel.setUserDetail(username.toString())
        viewmodel.getUserDetail().observe(this, {
            if (it !=null){
                binding.apply {
                   tvProfile.text = it.name
                   tvUsername.text = it.login
                   tvFollowers.text = "${it.followers} Followers"
                   tvFollowing.text = "${it.following} Following"
                   Glide.with(this@DetailUserActivity)
                       .load(it.avatar_url)
                       .transition(DrawableTransitionOptions.withCrossFade())
                       .centerCrop()
                       .into(ivProfile)
                }
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

    }

}