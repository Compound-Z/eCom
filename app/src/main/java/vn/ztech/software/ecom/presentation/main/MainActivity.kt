package vn.ztech.software.ecom.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import vn.ztech.software.ecom.R
import vn.ztech.software.ecom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNav()
    }

    private fun setUpNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.homeBottomNavigation, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.cartFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.accountFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.ordersFragment -> setBottomNavVisibility(View.VISIBLE)
                //feature: enable later
//                R.id.orderSuccessFragment -> setBottomNavVisibility(View.VISIBLE)
                else -> setBottomNavVisibility(View.GONE)
            }
        }
        //later: for seller
//        val sessionManager = ShoppingAppSessionManager(this.applicationContext)
//        if (sessionManager.isUserSeller()) {
//            binding.homeBottomNavigation.menu.removeItem(R.id.cartFragment)
//        }else {
//            binding.homeBottomNavigation.menu.removeItem(R.id.ordersFragment)
//        }
    }

    private fun setBottomNavVisibility(visibility: Int) {
        binding.homeBottomNavigation.visibility = visibility
    }
}