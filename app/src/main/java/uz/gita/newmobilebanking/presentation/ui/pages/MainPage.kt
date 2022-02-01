package uz.gita.newmobilebanking.presentation.ui.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.AppNavDirections
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.response.card.CardData
import uz.gita.newmobilebanking.databinding.PageMainBinding
import uz.gita.newmobilebanking.presentation.ui.adapters.CardAdapter
import uz.gita.newmobilebanking.presentation.viewModel.pages.main.MainPageVM
import uz.gita.newmobilebanking.presentation.viewModel.pages.main.MainPageVMImpl
import uz.gita.newmobilebanking.utils.showToast

@AndroidEntryPoint
class MainPage : Fragment(R.layout.page_main) {
    private val binding by viewBinding(PageMainBinding::bind)
    private val viewModel: MainPageVM by viewModels<MainPageVMImpl>()
    private val adapter by lazy { CardAdapter() }
    private var cardClickListener: ((CardData) -> Unit)? = null


    override fun onResume() {
        super.onResume()
        viewModel.userGetAllCards()
        viewModel.successGetAllCardsLiveData.observe(viewLifecycleOwner, successGetAllCardsObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.cardRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapter.setCardListener {
            findNavController().navigate(AppNavDirections.actionGlobalEditRemoveCardScreen(it))
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.successGetAllCardsLiveData.observe(viewLifecycleOwner, successGetAllCardsObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)

    }



    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }

    private val successGetAllCardsObserver = Observer<List<CardData>> {
        if (it.isEmpty()) {
            binding.addCard.visibility = View.VISIBLE
        } else {
            binding.addCard.visibility = View.GONE
            adapter.submitList(it)
        }
    }

    private val visibilityProgressObserver = Observer<Boolean> {
        binding.progress.visibility = if (it) View.VISIBLE else View.GONE

    }


}