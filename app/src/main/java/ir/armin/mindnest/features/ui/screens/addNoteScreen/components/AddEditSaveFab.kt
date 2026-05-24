package ir.armin.mindnest.features.ui.screens.addNoteScreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.armin.mindnest.R
import ir.armin.mindnest.features.theme.FabContainer
import ir.armin.mindnest.features.theme.FabContent


@Composable
fun AddEditSaveFab(
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    ExtendedFloatingActionButton(
        onClick = onSaveClick,
        modifier = modifier.padding(end = 16.dp, bottom = 8.dp),
        containerColor = FabContainer,
        contentColor = FabContent,

    ) {
        Icon(
            imageVector = Icons.Default.Save,
            contentDescription = stringResource(R.string.cd_save_note),
            tint = FabContent
        )
        Text(
            text = stringResource(R.string.save_note),
            style = MaterialTheme.typography.labelLarge,
            color = FabContent,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}