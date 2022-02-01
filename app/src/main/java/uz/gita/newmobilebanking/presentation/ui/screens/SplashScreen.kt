package uz.gita.newmobilebanking.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.presentation.viewModel.SplashVM
import uz.gita.newmobilebanking.presentation.viewModel.SplashVMImpl

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val viewModel: SplashVM by viewModels<SplashVMImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openMainScreenLiveData.observe(viewLifecycleOwner, openMainScreenObserver)
        viewModel.openLoginScreenLiveData.observe(viewLifecycleOwner, openLoginScreenObserver)
    }

    private val openMainScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashScreen_to_pinCodeScreen)
    }

    private val openLoginScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashScreen_to_loginScreen)
    }
}