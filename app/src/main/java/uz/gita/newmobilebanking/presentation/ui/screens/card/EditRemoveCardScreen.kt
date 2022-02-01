package uz.gita.newmobilebanking.presentation.ui.screens.card

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.request.card.DeleteCardRequest
import uz.gita.newmobilebanking.databinding.ScreenEditRemoveCardBinding
import uz.gita.newmobilebanking.presentation.viewModel.card.impl.EditRemoveCardScreenVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.EditRemoveCardScreenVM
import uz.gita.newmobilebanking.utils.showToast

@AndroidEntryPoint
class EditRemoveCardScreen : Fragment(R.layout.screen_edit_remove_card) {
    private val binding by viewBinding(ScreenEditRemoveCardBinding::bind)
    private val viewModel: EditRemoveCardScreenVM by viewModels<EditRemoveCardScreenVMImpl>()
    private val args by navArgs<EditRemoveCardScreenArgs>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.selectedCard.balance.text = args.cardData.balance.toString()
        binding.selectedCard.cardName.text = args.cardData.cardName
        binding.selectedCard.owner.text = args.cardData.owner
        binding.selectedCard.exp.text = args.cardData.exp
        binding.selectedCard.pan.text = args.cardData.pan

        binding.cardDelete.setOnClickListener {
            viewModel.userDeleteCard(DeleteCardRequest(args.cardData.pan))
        }

        binding.cardEdit.setOnClickListener {
            viewModel.gotoEditCardScreen(args.cardData.pan)
        }

        viewModel.ableEditCardButtonLiveData.observe(viewLifecycleOwner, ableEditCardButtonObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
        viewModel.errorDeleteCardMessageLiveData.observe(
            viewLifecycleOwner,
            errorDeleteCardMessageObserver
        )
        viewModel.successDeleteCardLiveData.observe(this, successDeleteCardObserver)
        viewModel.gotoEditCardScreenLiveData.observe(this, gotoEditCardScreenObserver)

    }

    private val gotoEditCardScreenObserver = Observer<String> {
        findNavController().navigate(
            EditRemoveCardScreenDirections.actionEditRemoveCardScreenToEditCardScreen(
                it
            )
        )
    }

    private val ableEditCardButtonObserver = Observer<Boolean> {
        binding.cardEdit.isEnabled = it
    }

    private val visibilityProgressObserver = Observer<Boolean> {
        binding.progress.visibility = if (it) View.VISIBLE else View.GONE
    }

    private val errorDeleteCardMessageObserver = Observer<String> {
        showToast(it)
    }

    private val successDeleteCardObserver = Observer<String> {
        findNavController().popBackStack()
        showToast(it)
    }
}