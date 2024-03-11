package com.example.vktask.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktask.model.HttpCall
import com.example.vktask.model.Product
import com.example.vktask.retrofit.Common
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    @SuppressLint("MutableCollectionMutableState")
    var _productsList = mutableStateOf<MutableList<Product>>(mutableListOf())
    val productsList: State<MutableList<Product>> = _productsList


    var errorMessage = mutableStateOf("")

    var isLoading = mutableStateOf(true)

    //Параметры запросов
    var skip = mutableStateOf(0)
    val limit = 20
    var total = mutableStateOf(0)
    var query = mutableStateOf("")
    var category = mutableStateOf("")

    //Список категорий
    @SuppressLint("MutableCollectionMutableState")
    var _categoriesList = mutableStateOf<MutableList<String>>(mutableListOf())
    val categoriesList: State<MutableList<String>> = _categoriesList

    var currentProduct = mutableStateOf(Product())

    fun getProductsList(){
        viewModelScope.launch {
            val retrofitService = Common.retrofitService
            retrofitService.getProductsList(skip.value, limit).enqueue(object : Callback<HttpCall> {
                override fun onFailure(call: Call<HttpCall>, t: Throwable) {
                    errorMessage.value = t.message.toString()
                }
                override fun onResponse(call: Call<HttpCall>, response: Response<HttpCall>) {
                    isLoading.value = false
                    try {
                        total.value = response.body()?.total as Int
                        _productsList.value = response.body()?.products as MutableList<Product>
                        errorMessage.value = ""
                    } catch (e: Exception){
                        errorMessage.value = e.message.toString()
                    }
                }
            })
        }
    }
    fun getProduct(id: Int){
        isLoading.value = true
        viewModelScope.launch {
            val retrofitService = Common.retrofitService
            retrofitService.getProductById(id).enqueue(object : Callback<Product>{
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    isLoading.value = false
                    try {
                        currentProduct.value = response.body() as Product
                        errorMessage.value = ""
                    } catch (e: Exception){
                        errorMessage.value = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    errorMessage.value = t.message.toString()
                }

            })
        }
    }

    fun searchByQuery(){
        viewModelScope.launch {
            val retrofitService = Common.retrofitService
            retrofitService.searchByQuery(query.value).enqueue(object : Callback<HttpCall> {
                override fun onResponse(call: Call<HttpCall>, response: Response<HttpCall>) {
                    try {
                        _productsList.value = response.body()?.products as MutableList<Product>
                        errorMessage.value = ""
                    } catch (e: Exception){
                        errorMessage.value = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<HttpCall>, t: Throwable) {
                    errorMessage.value = t.message.toString()
                }

            })
        }
    }

    fun getAllCategories(){
        viewModelScope.launch {
            val retrofitService = Common.retrofitService
            retrofitService.getAllCategories().enqueue(object : Callback<MutableList<String>> {
                override fun onResponse(
                    call: Call<MutableList<String>>,
                    response: Response<MutableList<String>>
                ) {
                    try {
                        _categoriesList.value = response.body() as MutableList<String>
                        errorMessage.value = ""
                    } catch (e: Exception){
                        errorMessage.value = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                    errorMessage.value = t.message.toString()
                }

            })
        }
    }

    fun getProductsByCategory(){
        viewModelScope.launch {
            val retrofitService = Common.retrofitService
            retrofitService.getProductsByCategory(category.value).enqueue(object : Callback<HttpCall>{
                override fun onResponse(call: Call<HttpCall>, response: Response<HttpCall>) {
                    try {
                        _productsList.value = response.body()?.products as MutableList<Product>
                        errorMessage.value = ""
                    } catch (e: Exception){
                        errorMessage.value = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<HttpCall>, t: Throwable) {
                    errorMessage.value = t.message.toString()
                }

            })
        }
    }

}