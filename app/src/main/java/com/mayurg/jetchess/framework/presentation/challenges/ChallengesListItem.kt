package com.mayurg.jetchess.framework.presentation.challenges

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.business.domain.model.Challenge

/**
 * Created On 23/11/2021
 * @author Mayur Gajra
 */

@Composable
fun ChallengesListItem(challenge: Challenge) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .weight(7f)
                        .padding(4.dp),
                    text = "${challenge.fromUsername} sent you a challenge",
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Start
                )
                Button(
                    modifier = Modifier
                        .weight(1.5f)
                        .height(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error,
                    ),
                    onClick = { }
                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Reject icon")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier
                        .weight(1.5f)
                        .height(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                    ),
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Accept icon",
                    )
                }
            }
            Divider()
        }
    }
}