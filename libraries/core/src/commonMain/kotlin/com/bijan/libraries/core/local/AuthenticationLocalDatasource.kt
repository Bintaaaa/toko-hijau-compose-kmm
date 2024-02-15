package com.bijan.libraries.core.local

import androidx.compose.runtime.compositionLocalOf
import com.bijan.libraries.core.network.TokenDatasource

class AuthenticationLocalDatasource(
    private  val  valueDataSources: ValueDataSources
): TokenDatasource {
    override val getToken: String
        get() = valueDataSources.getString("token")

    fun saveToken(token: String){
        valueDataSources.setString("token", token)
    }

    fun deleteToken(){
        valueDataSources.removeValue("token")
    }
}

val LocalAuthenticationLocalDatasource = compositionLocalOf<AuthenticationLocalDatasource> {
    error("Local token data sources not provided")
}