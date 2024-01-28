package com.bijan.libraries.core.local

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

abstract class LocalDataSources(vararg kClass: KClass<out RealmObject>) {
    private val config = RealmConfiguration.create(
        schema = kClass.toList().toSet()
    )
    private val realm = Realm.open(config)

    suspend fun <T: RealmObject> insertObject(data: T){
        withContext(Dispatchers.IO){
            realm.writeBlocking {
                copyToRealm(data)
            }
        }
    }

    suspend fun <T: RealmObject> removeObject(kClass: KClass<T>, id: Int) {
        withContext(Dispatchers.IO) {
            realm.writeBlocking {
                val transactionItem = query(kClass, "id == $id")
                delete(transactionItem)
            }
        }
    }

    suspend fun <T: RealmObject> getObjects(kClass: KClass<T>): Flow<List<T>> {
        return withContext(Dispatchers.IO) {
            realm.query(kClass)
                .find()
                .asFlow()
                .map {
                    it.list.asReversed()
                }
        }
    }

    suspend fun <T: RealmObject> getObjectExistById(kClass: KClass<T>, id: Int): Flow<Boolean> {
        return withContext(Dispatchers.IO) {
            realm.query(kClass, query = "id = $id")
                .find()
                .asFlow()
                .map {
                    it.list.isNotEmpty()
                }
        }
    }

    suspend fun <T: RealmObject> getObjectById(kClass: KClass<T>, id: Int): T? {
        return withContext(Dispatchers.IO) {
            realm.query(kClass, query = "id == $id")
                .find()
                .lastOrNull()
        }
    }

}