package uz.gita.newmobilebanking.presentation.ui.screens.nav_draw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.databinding.ScreenAboutBinding

@AndroidEntryPoint
class AboutScreen : Fragment(R.layout.screen_about) {
    private val binding by viewBinding(ScreenAboutBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}