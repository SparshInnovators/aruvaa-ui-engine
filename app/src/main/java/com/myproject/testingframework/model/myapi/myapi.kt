package com.myproject.testingframework.model.myapi

import android.util.Log
import com.myproject.testingframework.model.DataManager.DataManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.JsonElement
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Inject
import javax.inject.Singleton

interface MyApi {
    @GET("b/67d2c4a78561e97a50eb22a1?meta=false")
    @Headers("X-Master-Key: \$2a\$10\$dcPieZr2JzN59X9IdV29Meo8/So3TwO3japqEE7Bkghp0UVNh7JSy")
    suspend fun getOrganizationData(): Response<Map<String, Any>>
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val _baseUrl = "https://api.jsonbin.io/v3/"


    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(_baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getMyApi(retrofit: Retrofit): MyApi {
        return retrofit.create(MyApi::class.java)
    }

}

class MyRepository @Inject constructor(private val myApi: MyApi) {

    private val _organizationList = MutableStateFlow<List<Map<String, String>>>(emptyList())
    val organizationList = _organizationList

    suspend fun fetchOrganizationData() {
        val response = myApi.getOrganizationData()
        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            val dataList =
                responseBody["organizations"] as? List<Map<String, String>> ?: emptyList()

            _organizationList.emit(dataList)
            DataManager.organizationDataList = dataList
        } else {
            Log.e("Ankit Raj", "Error: ${response.errorBody()?.string()}")
        }
    }

}