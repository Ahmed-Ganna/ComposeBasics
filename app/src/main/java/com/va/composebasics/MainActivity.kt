package com.va.composebasics

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.va.composebasics.ui.theme.ComposeBasicsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsTheme{
                Conversation(SampleData.conversationSample)
            }
        }

    }




    @Composable
    fun Conversation(messages:List<Message>){
        LazyColumn{
            items(messages){message->
                MessageCard(message = message)
            }
        }
    }

    @Composable
    fun MessageCard(message: Message){
        Row {
           Image(painter = painterResource(id = R.drawable.profile_picture)
               , contentDescription = "Contact profile picture",
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
           )
            Spacer(modifier = Modifier.width(8.dp))

            // We keep track if the message is expanded or not in this
            // variable
            var isExpanded by remember { mutableStateOf(false) }
            // surfaceColor will be updated gradually from one color to the other
            val surfaceColor: Color by animateColorAsState(
                if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            )

            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(text = message.author , color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2)
                Spacer(modifier = Modifier.height(4.dp))
                
                Surface(
                    // surfaceColor color will be changing gradually from primary to surface
                    color = surfaceColor,
                    // animateContentSize will change the Surface size gradually
                    modifier = Modifier.animateContentSize().padding(1.dp),

                    shape = MaterialTheme.shapes.medium,

                    elevation = 1.dp
                ) {
                    Text(text = message.body , style = MaterialTheme.typography.body2 ,
                    modifier = Modifier.padding(4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1)

                }
            }
        }


    }

    @Preview(name = "Light mode")
    @Composable
    fun previewMessageCard(){
        ComposeBasicsTheme {
            Conversation(SampleData.conversationSample)
        }
    }

    @Preview("Dark mode" , uiMode = Configuration.UI_MODE_NIGHT_YES , showBackground = true)
    @Composable
    fun previewMessageCardDark(){
        ComposeBasicsTheme {
            Conversation(SampleData.conversationSample)
        }
    }

}

