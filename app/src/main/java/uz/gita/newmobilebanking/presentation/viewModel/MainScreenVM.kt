package uz.gita.newmobilebanking.presentation.viewModel

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.local.enums.BottomMenuEnum
import uz.gita.newmobilebanking.data.local.enums.NavDrawEnum

interface MainScreenVM {

    val openSelectedPageLiveData : LiveData<BottomMenuEnum>
    fun selectPagePos(pageName : BottomMenuEnum)

    val openDrawerLayoutLiveData : LiveData<Unit>
    fun openDrawerLayout()

    val openSelectedNavDrawItemLiveData : LiveData<NavDrawEnum>
    fun openSelectedNavDrawItem(item: NavDrawEnum)

    val openAddCardScreenLiveData: LiveData<Unit>
    fun openAddCardScreen()
}