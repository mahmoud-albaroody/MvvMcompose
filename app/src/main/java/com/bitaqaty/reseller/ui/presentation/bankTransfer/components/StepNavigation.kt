package com.bitaqaty.reseller.ui.presentation.bankTransfer.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R

@Composable
fun StepNavigation(
    onClickBack: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 1.dp),
            contentPadding = PaddingValues(8.dp),
            border = BorderStroke(width = 1.dp, color = Color(0xff1D3893)),
            shape = RoundedCornerShape(8.dp),
            onClick = { onClickBack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        ) {
            Text(
                text = stringResource(id = R.string.back),
                color = Color(0xff1D3893)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            modifier = Modifier
                .defaultMinSize(minHeight = 1.dp)
                .weight(1f),
            contentPadding = PaddingValues(8.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {  },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1D3893)),
        ) {
            Text(
                text = stringResource(id = R.string.next),
                color = Color.White
            )
        }
    }
}