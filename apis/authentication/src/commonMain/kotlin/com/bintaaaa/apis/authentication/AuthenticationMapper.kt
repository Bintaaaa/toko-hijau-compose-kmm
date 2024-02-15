package com.bintaaaa.apis.authentication

import com.bintaaaa.apis.authentication.models.LoginEntity
import com.bintaaaa.apis.authentication.models.LoginResponseModel

object Mapper {

    fun mapResponseToLogin(loginResponseResponse: LoginResponseModel.DataResponse?): LoginEntity {
        return LoginEntity(
            token = loginResponseResponse?.token.orEmpty()
        )
    }
}