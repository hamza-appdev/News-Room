package com.example.news.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.news.data.dto.Article

@SuppressLint("SuspiciousIndentation")
@Composable
fun Bottomsheetcontent(
    modifier: Modifier = Modifier,
    article: Article,
    onReadfullstorybutton:()-> Unit
) {
    val color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else Color.Black
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = article.title,
                style = MaterialTheme.typography.titleMedium,
                color = color
                )
            Spacer(modifier= Modifier.height(8.dp))

             Text(text = article.description?:"",
                style = MaterialTheme.typography.bodyMedium,
                 color = color
                )
            Spacer(modifier= Modifier.height(8.dp))

           ImageHolder( imageURL = article.urlToImage?:"")
            Spacer(modifier= Modifier.height(8.dp))

            Text(text = article.content?:"",
                style = MaterialTheme.typography.bodyMedium,
                color =color
            )
            Spacer(modifier= Modifier.height(8.dp))

            Row (
                modifier= Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

            ){
                Text(text = article.author?:"",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color =color
                )
                Text(text = article.source.name?:"",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color =color
                )
            }
            Spacer(modifier= Modifier.height(8.dp))
            Button(
                onClick =  onReadfullstorybutton,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
                ) {
                Text("Read full story ",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

        }
        

}