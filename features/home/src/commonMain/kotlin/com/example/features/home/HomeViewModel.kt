package com.example.features.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bijan.apis.product.ProductRepository
import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.state.AsyncState
import com.bijan.libraries.core.state.Intent
import com.bijan.libraries.core.viewModel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository,private val appConfig: AppConfig) :
    ViewModel<HomeState, HomeIntent>(HomeState()) {

        private fun splash(){
            viewModelScope.launch {
                delay(2000).apply {
                    updateUiState {
                        copy(
                            splash = AsyncState.Success(true)
                        )
                    }
                }

            }
        }

    private fun getProductsLowPrice() = viewModelScope.launch {

        Pager(
            config = PagingConfig(pageSize = 5)
        ) {
            HomePaggingSources(appConfig, "")
        }.flow
            .cachedIn(viewModelScope)
            .also {
                updateUiState {
                    copy(
                        pagingData = it
                    )
                }
            }

    }
    private fun updateName(name: String) = viewModelScope.launch {
        updateUiState {
            copy(name = name)
        }
    }

    private fun getCategories() = viewModelScope.launch {
        productRepository.getCategories().stateIn(this).collectLatest {
            updateUiState {
                copy(
                    asyncCategories = it
                )
            }
        }
    }

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is HomeIntent.SetName -> {
                updateName(intent.name)
            }

            is HomeIntent.GetProductsLowPrice -> {
                getProductsLowPrice()
            }

            is HomeIntent.GetCategories -> {
                getCategories()
            }
            is HomeIntent.Splash ->{
                splash()
            }
        }
    }


    companion object {
        private const val QUERY_LOW_PRICE = "?sort=low_price&pageSize=7"
    }
}