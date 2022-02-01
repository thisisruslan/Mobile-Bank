package uz.gita.newmobilebanking.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.newmobilebanking.presentation.ui.pages.HistoryPage
import uz.gita.newmobilebanking.presentation.ui.pages.MainPage
import uz.gita.newmobilebanking.presentation.ui.pages.ProfilePage
import uz.gita.newmobilebanking.presentation.ui.pages.TransferPage

class MainPageAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> MainPage()
            1 -> TransferPage()
            2 -> HistoryPage()
            else -> ProfilePage()
        }


    }
}