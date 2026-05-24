package ir.armin.mindnest.features.ui.screens.addNoteScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.armin.mindnest.R
import ir.armin.mindnest.features.theme.IconDark
import ir.armin.mindnest.features.theme.LightPurple
import ir.armin.mindnest.features.theme.TopBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTopBar(
    onBackClick: () -> Unit,
    onChangeBackgroundClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier.background(TopBarColor)
            .padding(horizontal = 16.dp),
        title = {
            Text(
                text = stringResource(R.string.add_edit_app_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.arow_back_ic),
                    contentDescription = stringResource(R.string.cd_back),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onChangeBackgroundClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LightPurple)
            ) {
                Icon(
                    painter = painterResource(R.drawable.change_background_ic),
                    contentDescription = stringResource(R.string.cd_change_background),
                    tint = IconDark,
                    modifier = Modifier.size(22.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = TopBarColor
        )
    )


}
