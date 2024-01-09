package com.example.casestudyn11.ui.searchuser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.casestudyn11.R
import com.example.casestudyn11.ui.userlist.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchUserScreen(
    viewModel: SearchUserViewModel
) {
    val state by viewModel.state.collectAsState()
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var active by rememberSaveable {
        mutableStateOf(false)
    }
    Column {
        SearchBar(
            query = query,
            onQueryChange = {
                query = it
            },
            onSearch = { query ->
                active = false
                viewModel.searchUsers(query)
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(
                    text = "Search for users",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            },
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.search_24), contentDescription = "")
            },
            modifier = Modifier.fillMaxWidth()
        ) {}
        when (state) {
            is SearchUserState.Initial -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Start searching for users", modifier = Modifier)
                }
            }

            is SearchUserState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchUserState.Content -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items((state as SearchUserState.Content).userList) {
                        UserItem(
                            user = it,
                            onUserClicked = viewModel::onUserItemClicked,
                            shouldDisplayFavoriteIcon = false
                        )
                    }
                }
            }

            is SearchUserState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = (state as SearchUserState.Error).message)
                }
            }

            is SearchUserState.Empty -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No user found. Please try another user")
                }
            }
        }
    }
}