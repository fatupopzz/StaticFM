package com.fatupopz.staticfm.data.api

import android.util.Base64
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String? = null,
    val scope: String
)

interface SpotifyTokenService {

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getToken(
        @Header("Authorization") authorization: String,
        @Field("grant_type")    grantType: String,
        @Field("code")          code: String,
        @Field("redirect_uri")  redirectUri: String
    ): TokenResponse

    @FormUrlEncoded
    @POST("api/token")
    suspend fun refreshToken(
        @Header("Authorization") authorization: String,
        @Field("grant_type")    grantType: String = "refresh_token",
        @Field("refresh_token") refreshToken: String
    ): TokenResponse

    companion object {
        private const val BASE_URL = "https://accounts.spotify.com/"

        fun create(): SpotifyTokenService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SpotifyTokenService::class.java)
        }

        fun basicAuthHeader(clientId: String, clientSecret: String): String {
            val auth = "$clientId:$clientSecret"
            return "Basic ${Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)}"
        }
    }
}