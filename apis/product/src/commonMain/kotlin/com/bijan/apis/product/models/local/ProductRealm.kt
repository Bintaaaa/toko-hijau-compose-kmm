package com.bijan.apis.product.models.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ProductRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var price: Double = 0.0
    var description: String = ""
    var image: String = ""
}