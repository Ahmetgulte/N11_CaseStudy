package com.example.casestudyn11.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.casestudyn11.R
import com.example.casestudyn11.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UserListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvents()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val state by viewModel.uiState.collectAsState()
                    when (state) {
                        is UserListState.Loading -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is UserListState.Content -> UserListContent(
                            contentState = (state as UserListState.Content).contentState,
                            onSearchBarClicked = viewModel::onSearchBarClicked,
                            onUserClicked = viewModel::onUserItemClicked,
                            onFavoriteClicked = viewModel::updateFavorite
                        )
                        is UserListState.Error -> Text(text = (state as UserListState.Error).errorInfo)
                    }
                }
            }
        }
        return view
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.events.collect {
                    when(it) {
                        is UserListEvent.OnUserItemClicked -> {
                            val action =
                                UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
                                    it.userId
                                )
                            findNavController().navigate(action)
                        }

                        is UserListEvent.OnSearchBarClicked -> {
                            findNavController().navigate(
                                R.id.action_userListFragment_to_searchUserFragment
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}