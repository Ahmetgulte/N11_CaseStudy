package com.example.casestudyn11.ui.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.casestudyn11.R
import com.example.casestudyn11.databinding.FragmentUserDetailBinding
import com.example.casestudyn11.databinding.FragmentUserListBinding
import com.example.casestudyn11.ui.userlist.UserListContent
import com.example.casestudyn11.ui.userlist.UserListState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val userId = UserDetailFragmentArgs.fromBundle(it).userId
            viewModel.init(userId)
        }
        observeEvents()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val userUiModel by viewModel.userUiModel.collectAsState()
                    if (userUiModel.id != -1) {
                        UserDetailScreen(
                            user = userUiModel,
                            viewModel::onAvatarClicked,
                            viewModel::updateFavorite
                        )
                    }
                }
            }
        }
        return view
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect {
                    when (it) {
                        is UserDetailEvent.OnAvatarClicked -> {
                            val action =
                                UserDetailFragmentDirections.actionUserDetailFragmentToAvatarFragment(
                                    it.avatarUrl
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }
}