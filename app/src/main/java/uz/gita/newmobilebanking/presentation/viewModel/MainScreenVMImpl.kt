package uz.gita.newmobilebanking.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.newmobilebanking.data.local.enums.BottomMenuEnum
import uz.gita.newmobilebanking.data.local.enums.NavDrawEnum
import uz.gita.newmobilebanking.domain.repository.interfaces.AuthRepository
import javax.inject.Inject

@HiltViewModel
class MainScreenVMImpl @Inject constructor(private val repo: AuthRepository) : ViewModel(),
    MainScreenVM {

    override val openSelectedPageLiveData = MutableLiveData<BottomMenuEnum>()
    override fun selectPagePos(pageName: BottomMenuEnum) {
        openSelectedPageLiveData.value = pageName
    }

    override val openDrawerLayoutLiveData = MutableLiveData<Unit>()
    override fun openDrawerLayout() {
        openDrawerLayoutLiveData.value = Unit
    }

    override val openSelectedNavDrawItemLiveData = MutableLiveData<NavDrawEnum>()
    override fun openSelectedNavDrawItem(item: NavDrawEnum) {
        openSelectedNavDrawItemLiveData.value = item
    }

    override val openAddCardScreenLiveData= MutableLiveData<Unit>()
    override fun openAddCardScreen() {
        openAddCardScreenLiveData.value = Unit
    }
}