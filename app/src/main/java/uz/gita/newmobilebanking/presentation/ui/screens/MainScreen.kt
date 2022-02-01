package uz.gita.newmobilebanking.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.local.enums.BottomMenuEnum
import uz.gita.newmobilebanking.data.local.enums.NavDrawEnum
import uz.gita.newmobilebanking.databinding.MainNavBinding
import uz.gita.newmobilebanking.presentation.ui.adapters.MainPageAdapter
import uz.gita.newmobilebanking.presentation.ui.pages.MainPage
import uz.gita.newmobilebanking.presentation.viewModel.MainScreenVM
import uz.gita.newmobilebanking.presentation.viewModel.MainScreenVMImpl

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.main_nav), NavigationView.OnNavigationItemSelectedListener {
    private val bindingNav by viewBinding(MainNavBinding::bind)
    private val viewModel: MainScreenVM by viewModels<MainScreenVMImpl>()
    private lateinit var pagerAdapter: MainPageAdapter

    override fun onResume() {
        super.onResume()
        bindingNav.navDrawer.closeDrawer(GravityCompat.START, false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = MainPageAdapter(childFragmentManager, lifecycle)

        bindingNav.navView.setNavigationItemSelectedListener(this)
        bindingNav.layoutMain.viewPager2.adapter = pagerAdapter
        bindingNav.layoutMain.viewPager2.isUserInputEnabled = false
        bindingNav.layoutMain.menuBtn.setOnClickListener { viewModel.openDrawerLayout() }
        bindingNav.layoutMain.addCard.setOnClickListener { viewModel.openAddCardScreen() }

        bindingNav.layoutMain.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottomMain -> viewModel.selectPagePos(BottomMenuEnum.MAIN)
                R.id.bottomTransfer -> viewModel.selectPagePos(BottomMenuEnum.TRANSFER)
                R.id.bottomHistory -> viewModel.selectPagePos(BottomMenuEnum.HISTORY)
                R.id.bottomProfile -> viewModel.selectPagePos(BottomMenuEnum.PROFILE)
            }
            return@setOnItemSelectedListener true
        }


        viewModel.openSelectedNavDrawItemLiveData.observe(this, { openSelectedNavDrawItemObserver })
        viewModel.openSelectedPageLiveData.observe(viewLifecycleOwner, openSelectedPageObserver)
        viewModel.openDrawerLayoutLiveData.observe(viewLifecycleOwner, openDrawerLayoutObserver)
        viewModel.openAddCardScreenLiveData.observe(this, openAddCardScreenObserver)
    }

    private val openAddCardScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_mainScreen_to_addCardScreen)
    }

    private val openSelectedNavDrawItemObserver = Observer<NavDrawEnum> {
        when (it) {
            NavDrawEnum.SECURITY -> findNavController().navigate(R.id.action_mainScreen_to_securityScreen)
            NavDrawEnum.ABOUT -> findNavController().navigate(R.id.action_mainScreen_to_aboutScreen)
            else -> activity?.finish()
        }
    }

    private val openSelectedPageObserver = Observer<BottomMenuEnum> {
        bindingNav.layoutMain.viewPager2.currentItem = it.pos
    }

    private val openDrawerLayoutObserver = Observer<Unit> {
        bindingNav.navDrawer.openDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.security -> {
                viewModel.openSelectedNavDrawItem(NavDrawEnum.SECURITY)
            }
            R.id.about -> {
                viewModel.openSelectedNavDrawItem(NavDrawEnum.ABOUT)
            }
            R.id.exit -> {
                viewModel.openSelectedNavDrawItem(NavDrawEnum.EXIT)
            }
        }
        return true
    }
}