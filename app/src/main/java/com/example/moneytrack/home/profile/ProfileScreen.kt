package com.example.moneytrack.home.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.auth.AuthRepository
import com.example.data.model.AppUser
import com.example.moneytrack.core.components.AppUserIcon
import com.example.moneytrack.ui.theme.LocalDimens

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    when (val auth = authState) {
        is AuthRepository.AuthState.LoggedIn -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileUserInfo(appUser = auth.appUser)
            }
        }

        AuthRepository.AuthState.LoggedOut -> {}
    }

}

@Composable
fun ProfileUserInfo(
    modifier: Modifier = Modifier,
    appUser: AppUser
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = LocalDimens.current.dimen24),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppUserIcon(
            modifier = Modifier.size(LocalDimens.current.dimen80),
            user = appUser
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen4))
        Text(
            text = appUser.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}