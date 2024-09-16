package com.ymaniz09.exposeddropdownmenubox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.ymaniz09.exposeddropdownmenubox.ui.theme.ExposedDropdownMenuBoxIssueTheme


private val mods = listOf(
    Mod(1, "Mod 1"),
    Mod(2, "Mod 2"),
    Mod(3, "Mod 3"),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExposedDropdownMenuBoxIssueTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                    ) {
                        Text("Mods")

                        Spacer(modifier = Modifier.height(16.dp))

                        RedLineLocationDropDown(
                            mods = mods + mods + mods,
                            selectedMod = mods.first(),
                            onSelectedModChange = {},
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RedLineLocationDropDown(
    modifier: Modifier = Modifier,
    mods: List<Mod>,
    selectedMod: Mod?,
    onSelectedModChange: (Mod) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        modifier = modifier
            .fillMaxWidth(),
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            enabled = mods.isNotEmpty(),
            value = selectedMod?.name.orEmpty(),
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            properties = PopupProperties(
                focusable = true,
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
            ),
            modifier = Modifier.exposedDropdownSize(),
        ) {
            mods.forEach { mod ->
                DropdownMenuItem(
                    text = { Text(text = mod.name) },
                    onClick = {
                        isExpanded = false
                        focusManager.clearFocus()
                        onSelectedModChange(mod)
                    },
                )
            }
        }
    }
}

data class Mod(
    val id: Long,
    val name: String,
)
