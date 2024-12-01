package com.capstone.project.hondealz.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.project.hondealz.R
import com.capstone.project.hondealz.databinding.ActivityMainBinding
import com.capstone.project.hondealz.view.ViewModelFactory
import com.capstone.project.hondealz.view.fragments.HistoryFragment
import com.capstone.project.hondealz.view.fragments.ProfileFragment
import com.capstone.project.hondealz.view.fragments.ScanFragment
import com.capstone.project.hondealz.view.fragments.home.HomeFragment
import com.capstone.project.hondealz.view.welcome.WelcomeActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSessionAndInitializeApp()
    }

    private fun checkSessionAndInitializeApp() {
        mainViewModel.getSession().observe(this) { userModel ->
            if (userModel.isLogin) {
                setupBottomNavigation()
                replaceFragment(HomeFragment())
                binding.bottomNavigation.show(1)
            } else {
                navigateToWelcome()
            }
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(1, getString(R.string.home), R.drawable.ic_home_24)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(2, getString(R.string.scan), R.drawable.ic_scan_24)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(3, getString(R.string.history), R.drawable.ic_history_24)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(4, getString(R.string.profile), R.drawable.ic_profile_24)
        )

        binding.bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> replaceFragment(HomeFragment())
                2 -> replaceFragment(ScanFragment())
                3 -> replaceFragment(HistoryFragment())
                4 -> replaceFragment(ProfileFragment())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun navigateToWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }
}