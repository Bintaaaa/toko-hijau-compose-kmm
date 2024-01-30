package com.bijan.libraries.core.network

interface TokenDatasource{
    val getToken: String

    companion object {
        val Default = object : TokenDatasource {
            override val getToken: String
                get() = ""
        }
    }
}
