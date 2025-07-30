package com.pidygb.template.feature.template

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pidygb.template.core.common.Result
import com.pidygb.template.core.ui.CommonText
import com.pidygb.template.feature.template.viewmodel.TemplateViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

object Template

@Composable
fun TemplateSurface(
    modifier: Modifier = Modifier,
    viewModel: TemplateViewModel = koinViewModel<TemplateViewModel>(),
    onTemplateClick: () -> Unit
) {

    Surface(modifier = modifier.fillMaxSize()) {
        Box(Modifier.clickable(onClick = onTemplateClick)) {
            val result by viewModel.result.collectAsState()
            when (val r = result) {
                is Result.Error -> Text(
                    text = r.exception.message ?: "",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )

                Result.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )

                is Result.Success -> CommonText(r.data, Modifier.align(Alignment.Center))
            }
        }
    }

}

@Preview
@Composable
fun TemplateSurfacePreview() {
    MaterialTheme {
        TemplateSurface {}
    }
}
