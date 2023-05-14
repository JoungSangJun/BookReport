package kr.baekseok.bookreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.baekseok.bookreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val frameLayout: FrameLayout by lazy {
        mBinding.frameLayout
    }
    private val bottomNavigationView: BottomNavigationView by lazy {
        mBinding.bottomNavigationView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, HomeFragment()).commit()
        bottomNavigationView.selectedItemId = R.id.menu_home

        bottomNavigationView.setOnItemSelectedListener {
            setOnClickMenuItem(it)
        }
    }


    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(frameLayout.id, fragment).commit()
    }

    private fun setOnClickMenuItem(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_home -> {
                changeFragment(HomeFragment())
                true
            }
            R.id.menu_research -> {
                changeFragment(SearchFragment())
                true
            }
            else -> {
                changeFragment(AccountFragment())
                true
            }
        }
    }
}