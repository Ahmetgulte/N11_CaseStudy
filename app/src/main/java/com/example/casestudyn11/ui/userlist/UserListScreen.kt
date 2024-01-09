package com.example.casestudyn11.ui.userlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.casestudyn11.R
import com.example.casestudyn11.ui.model.UserUiModel

@Composable
fun UserListContent(
    users: List<UserUiModel>,
    onUserClicked: (Int) -> Unit,
    onSearchBarClicked: () -> Unit,
    onFavoriteClicked: (Int, Boolean) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(24.dp)),
            onSearchBarClicked
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) {
                UserItem(it, onUserClicked, onFavoriteClicked)
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    onSearchBarClicked: () -> Unit
) {
    Box(modifier = modifier.clickable {
        onSearchBarClicked()
    }) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.search_24), contentDescription = "")
            Text(text = "Click for searching users", modifier = Modifier.padding(start = 8.dp))
        }
    }
}


@Composable
fun UserItem(
    user: UserUiModel,
    onUserClicked: (Int) -> Unit,
    onFavoriteClicked: ((Int, Boolean) -> Unit)? = null,
    shouldDisplayFavoriteIcon: Boolean = true
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = Color.LightGray, shape = RoundedCornerShape(12.dp)
            )
    ) {
        Row(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onUserClicked(user.id) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(72.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .height(72.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = user.login)
            }
            if (shouldDisplayFavoriteIcon) {
                FavoriteButton(user.isFavorite, onClick = {
                    onFavoriteClicked?.invoke(user.id, it)
                })
            }
        }
    }
}

@Composable
fun FavoriteButton(isFav: Boolean, onClick: (Boolean) -> Unit) {
    var isFavorite by remember {
        mutableStateOf(isFav)
    }
    val painterResource = if (isFavorite) {
        painterResource(id = R.drawable.favorite_24)
    } else {
        painterResource(id = R.drawable.favorite_border)
    }
    Image(
        painter = painterResource,
        contentDescription = "",
        modifier = Modifier
            .size(24.dp)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = null) {
                onClick(!isFavorite)
                isFavorite = !isFavorite
            }
    )
}
