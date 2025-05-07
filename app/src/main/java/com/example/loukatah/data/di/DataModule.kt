package com.example.loukatah.data.di

import com.example.loukatah.data.repository.AuthRepositoryImpl
import com.example.loukatah.data.repository.ItemCategoryRepository
import com.example.loukatah.data.repository.ItemRepository
import com.example.loukatah.data.repository.ItemCategoryRepositoryImpl
import com.example.loukatah.data.repository.ItemRepositoryFirebase
import com.example.loukatah.data.repository.ItemRepositoryImpl
import com.example.loukatah.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object DataModule {
//
////    @Provides
////    @Singleton
////    fun provideItemRepository(
////        firestore: FirebaseFirestore,
////        auth: FirebaseAuth
////    ): ItemRepository {
////        return ItemRepositoryFirebaseNew(firestore, auth)
////    }
//
//    @Provides
//    @Singleton
//    fun provideItemRepository(
//
//    ): ItemRepository {
//        return ItemRepositoryFirebase(
//            firebaseAuth =
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideItemCategoryRepository(): ItemCategoryRepository {
//        return ItemCategoryRepositoryImpl()
//    }
//
//}
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance().apply {
            // إعدادات خاصة بفايربيز فايرستور
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true) // تمكين التخزين المحلي
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideItemRepository(): ItemRepository {
        return ItemRepositoryFirebase()
    }

    @Provides
    @Singleton
    fun provideItemCategoryRepository(): ItemCategoryRepository {
        return ItemCategoryRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }
}