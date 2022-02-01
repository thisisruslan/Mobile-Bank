package uz.gita.newmobilebanking.presentation.ui.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.databinding.PageHistoryBinding
import uz.gita.newmobilebanking.databinding.PageProfileBinding
import uz.gita.newmobilebanking.presentation.ui.adapters.HistoryAdapter
import uz.gita.newmobilebanking.presentation.ui.adapters.PassengersLoadStateAdapter
import uz.gita.newmobilebanking.presentation.viewModel.pages.history.HistoryPageVM
import uz.gita.newmobilebanking.presentation.viewModel.pages.history.HistoryPageVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.pages.profile.ProfilePageVM
import uz.gita.newmobilebanking.presentation.viewModel.pages.profile.ProfilePageVMImpl
import uz.gita.newmobilebanking.utils.scope
import uz.gita.newmobilebanking.utils.timber

@AndroidEntryPoint
class HistoryPage : Fragment(R.layout.page_history) {
    private val binding by viewBinding(PageHistoryBinding::bind)
    private val viewModel: HistoryPageVM by viewModels<HistoryPageVMImpl>()
    private val adapter by lazy { HistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        refresh.isRefreshing = true

        refresh.setOnRefreshListener {
            viewModel.getHistoryPagingData().onEach {
                adapter.submitData(it)
            }.launchIn(lifecycleScope)
            refresh.isRefreshing = true
        }

        historyList.adapter = adapter
        adapter.withLoadStateHeaderAndFooter(
            PassengersLoadStateAdapter { adapter.retry() },
            PassengersLoadStateAdapter { adapter.retry() }
        )

        historyList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getHistoryPagingData().onEach {
            adapter.submitData(it)
            binding.refresh.isRefreshing = false
        }.launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHistoryPagingData().onEach {
            timber("entered viewModel in page", "NNNN")
            adapter.submitData(it)
            binding.refresh.isRefreshing = false
        }.launchIn(lifecycleScope)
    }
}