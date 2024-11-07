package dev.robert.moviescatalogue.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun FilledTextFilled(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    maxLine: Int,
    label: String?,
    isPassword: Boolean = false,
    isLoading: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val togglePassword by rememberSaveable {
        mutableStateOf(true)
    }
    var textFieldFocusState by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    TextField(
        enabled = !isLoading && enabled,
        modifier = modifier
            .onFocusChanged {
                textFieldFocusState = it.isFocused
            }
            .focusRequester(focusRequester),
        value = value,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        onValueChange = onValueChange,
        shape = RoundedCornerShape(30.dp),
        singleLine = maxLine == 1,
        readOnly = readOnly,
        trailingIcon = {
            trailingIcon?.invoke()
        },
        keyboardOptions = keyboardOptions,
        maxLines = maxLine,
        placeholder = {
            label?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.outline,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            }
        },
        leadingIcon = leadingIcon,
        visualTransformation = if (isPassword)
            if (togglePassword) PasswordVisualTransformation()
            else VisualTransformation.None
        else VisualTransformation.None,
    )
}
