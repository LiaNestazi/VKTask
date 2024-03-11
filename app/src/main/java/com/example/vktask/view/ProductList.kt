package com.example.vktask.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vktask.R
import com.example.vktask.view.SearchBar
import com.example.vktask.viewmodel.MainViewModel

@Composable
fun ProductList(navController: NavHostController, mainViewModel: MainViewModel){
    val productList = mainViewModel.productsList.value
    val categoriesList = mainViewModel.categoriesList.value
    val expanded = remember {
        mutableStateOf(false)
    }
    if (mainViewModel.isLoading.value){
        Box(modifier = Modifier
            .fillMaxSize()
        ){
            CircularProgressIndicator(
                modifier = Modifier
                    .width(60.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.onSecondary
            )
        }
    } else{
        Box(modifier = Modifier.fillMaxSize()){
            Column() {
                SearchBar(mainViewModel = mainViewModel)
                Box(modifier = Modifier.padding(8.dp).align(Alignment.Start)){
                    Row(
                        modifier = Modifier
                            .clickable {
                                expanded.value = !expanded.value
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Сортировать",
                            modifier = Modifier,
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24), contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            mainViewModel.category.value = ""
                            mainViewModel.skip.value = 0
                            expanded.value = false
                        }) {
                            Text(text = "по умолчанию",
                                fontSize = 16.sp)
                        }
                        categoriesList.forEach{ item ->
                            DropdownMenuItem(onClick = {
                                mainViewModel.category.value = item
                                mainViewModel.getProductsByCategory()
                                expanded.value = false
                            }) {
                                Text(text = item,
                                    fontSize = 16.sp)
                            }
                        }
                    }
                }

                if (mainViewModel._productsList.value.isEmpty()){
                    Text(
                        text = "Ничего не найдено",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                } else{
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(productList) { item ->
                            ProductCard(navController = navController, item = item)
                        }
                    }
                }
            }
            if (productList.size >= mainViewModel.limit){
                Button(
                    modifier = Modifier.align(Alignment.BottomStart).padding(start = 8.dp),
                    onClick = { mainViewModel.skip.value -= 20 },
                    enabled = mainViewModel.skip.value != 0,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = ""
                    )
                }
                Button(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(end = 8.dp),
                    onClick = { mainViewModel.skip.value += 20 },
                    enabled = mainViewModel.skip.value+20 < mainViewModel.total.value,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = ""
                    )
                }
            }

        }

    }
}