package com.example.vktask.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.vktask.R
import com.example.vktask.viewmodel.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductInfo(navController: NavHostController, mainViewModel: MainViewModel, itemId: Int?){
    if (itemId == null){
        InternalErrorDialog(
            onReloadButtonClick = {navController.popBackStack()},
            errorMsg = "Страница не найдена"
        )
    } else{
        mainViewModel.getProduct(itemId)
        val currentProduct = mainViewModel.currentProduct.value
        val pagerState = rememberPagerState()
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .height(250.dp)
                        .background(colorResource(id = R.color.gray_100))
                ){
                    when (currentProduct.images){
                        null -> {

                        }
                        else -> {
                            HorizontalPager(
                                pageCount = currentProduct.images?.size!!,
                                state = pagerState,
                                key = { currentProduct.images!![it]}
                            ) {index ->
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = currentProduct.images!![index],
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.BottomCenter)
                        .background(Color.White.copy(alpha = 0.5F))){
                        Text(
                            text = if (currentProduct.images?.size != null) "${pagerState.currentPage+1}/${currentProduct.images?.size}" else "${pagerState.currentPage+1}/1",
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            textAlign = TextAlign.Start,
                            fontSize = 16.sp
                        )
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                    Row(horizontalArrangement = Arrangement.Start) {
                        Text(
                            "${currentProduct.price} $",
                            modifier = Modifier,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp
                        )
                        Text(
                            text = " (-${currentProduct.discountPercentage}%)",
                            modifier = Modifier,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp,
                            color = Color.Green
                        )
                    }

                    Row(horizontalArrangement = Arrangement.End) {
                        Image(painter = painterResource(id = R.drawable.baseline_star_rate_24), contentDescription = "")
                        Text(
                            text = " ${currentProduct.rating}",
                            modifier = Modifier,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp
                        )
                    }

                }
                Text(
                    text = "В наличии: ${currentProduct.stock}",
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .size(1.dp)
                    .padding(horizontal = 8.dp))
                Text(
                    "${currentProduct.category}",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp
                )
                Text(
                    "${currentProduct.brand} \u00B7 ${currentProduct.title}",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp
                )
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .size(1.dp)
                    .padding(horizontal = 8.dp))
                Text(
                    text = "${currentProduct.description}",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

            }

        }
    }
}