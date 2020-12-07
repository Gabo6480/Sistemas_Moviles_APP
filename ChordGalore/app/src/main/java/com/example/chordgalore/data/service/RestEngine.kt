package com.example.chordgalore.data.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection


class RestEngine {
    // nos permite acceder sin instanciar el objecto
    companion object{
        fun getRestEngine(): Retrofit {
            //Creamos el interceptor
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client =  OkHttpClient.Builder().addInterceptor(interceptor)
                .hostnameVerifier(HostnameVerifier { _, session ->
                    val hv: HostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()
                    return@HostnameVerifier hv.verify("xpresshosting.com", session)
                })
            return Retrofit.Builder()
                .baseUrl("http://www.leonardosantosgrc.com/g1e2.api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()

        }
    }
}
