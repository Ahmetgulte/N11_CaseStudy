package com.example.casestudyn11.ui.userdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.casestudyn11.R
import com.example.casestudyn11.ui.model.UserUiModel
import com.example.casestudyn11.ui.userlist.FavoriteButton

@Composable
fun UserDetailScreen(user: UserUiModel, onAvatarClicked: (String) -> Unit, onFavoriteClicked: (Int, Boolean) -> Unit) {
    Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp).clickable { onAvatarClicked(user.avatarUrl) }
        )
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
            Text(text = user.login!!, modifier = Modifier.weight(1f))
            FavoriteButton(isFav = user.isFavorite!!, onClick = {
                onFavoriteClicked(user.id, it)
            })
        }
    }
}