package com.example.vktask.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vktask.R
import com.example.vktask.viewmodel.MainViewModel

@Composable
fun SearchBar(mainViewModel: MainViewModel){
    val searchRequest = remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = searchRequest.value,
        onValueChange = {
            searchRequest.value = it
        },
        placeholder = {
            Row {
                Icon(painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "",
                    tint = colorResource(id = R.color.gray_500)
                )
                Text("Введите запрос",
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.gray_500),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            trailingIconColor = colorResource(id = R.color.gray_500),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.gray_500)
        )
    )

    when (searchRequest.value){
        "" -> {
            if (mainViewModel.category.value == ""){
                mainViewModel.getProductsList()
            }
        }
        else ->{
            mainViewModel.query.value = searchRequest.value
            mainViewModel.searchByQuery()
        }
    }
}