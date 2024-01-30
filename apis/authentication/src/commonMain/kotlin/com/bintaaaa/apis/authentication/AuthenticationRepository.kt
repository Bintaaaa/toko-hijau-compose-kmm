package com.bintaaaa.apis.authentication

import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.local.AuthenticationLocalDatasource
import com.bijan.libraries.core.repository.RepositoryReducer
import com.bijan.libraries.core.state.AsyncState
import com.bintaaaa.apis.authentication.models.LoginEntity
import com.bintaaaa.apis.authentication.models.LoginResponseModel
import kotlinx.coroutines.flow.Flow

class AuthenticationRepository(
    private val appConfig: AppConfig,
    private val localTokenDataSources: AuthenticationLocalDatasource
) : RepositoryReducer() {

    private val authenticationRemoteDataSource by lazy { AuthenticationRemoteDataSource(appConfig) }
    fun login(name: String, password: String): Flow<AsyncState<LoginEntity>> {
        return suspend {
            authenticationRemoteDataSource
                .postLogin(name, password)
        }.reduce<LoginResponseModel, LoginEntity> { response ->
            val responseData = response.data

            if (responseData == null) {
                AsyncState.Failure(Throwable("Data invalid"))
            } else {
                val data = Mapper.mapResponseToLogin(responseData)
                localTokenDataSources.saveToken(data.token)
                AsyncState.Success(data)
            }
        }
    }
}