package uz.gita.newmobilebanking.presentation.ui.screens.nav_draw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.databinding.ScreenSecurityBinding
import uz.gita.newmobilebanking.presentation.viewModel.nav_draw.interfaces.SecurityScreenVM
import uz.gita.newmobilebanking.presentation.viewModel.nav_draw.impl.SecurityScreenVMImpl
import uz.gita.newmobilebanking.utils.showToast

@AndroidEntryPoint
class SecurityScreen : Fragment(R.layout.screen_security) {
    private val binding by viewBinding(ScreenSecurityBinding::bind)
    private val viewModel: SecurityScreenVM by viewModels<SecurityScreenVMImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            viewModel.userLogout()
        }

        viewModel.clickableLogoutBtnLiveData.observe(viewLifecycleOwner, clickableLogoutBtnObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.logoutSuccessLiveData.observe(this, logoutSuccessObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
    }

    private val logoutSuccessObserver = Observer<String> {
        showToast(it)
        findNavController().popBackStack()
        findNavController().navigate(R.id.loginScreen)
    }
    private val visibilityProgressObserver = Observer<Boolean> {
        binding.progress.visibility = if (it) View.VISIBLE else View.GONE
    }
    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val clickableLogoutBtnObserver = Observer<Boolean> {
        binding.btnLogout.isEnabled = it
    }


}