package com.example.vktask.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.vktask.R
import com.example.vktask.model.Product

@Composable
fun ProductCard(navController: NavHostController, item: Product) {
    Card(
        backgroundColor = colorResource(id = R.color.gray_100),
        modifier = Modifier
            .height(260.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                navController.navigate("ProductInfo/" + item.id)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .height(175.dp)){
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .height(175.dp),
                    model = item.thumbnail,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .align(Alignment.BottomStart)
                    .background(Color.White.copy(alpha = 0.5F))){
                    Text(
                        text = "${item.price.toString()} $",
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp
                    )
                }
            }
            Text(
                text = item.title.toString(),
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.description.toString(),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}