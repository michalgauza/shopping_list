package com.example.shoppinglist.di

import android.app.Application
import androidx.room.Room
import com.example.shoppinglist.db.ShoppingListDao
import com.example.shoppinglist.db.ShoppingListDatabase
import com.example.shoppinglist.db.ShoppingListRepository
import com.example.shoppinglist.vm.ArchivedShoppingListsFragmentViewModel
import com.example.shoppinglist.vm.DetailsActivityViewModel
import com.example.shoppinglist.vm.CurrentShoppingListsFragmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val DATABASE_NAME = "shopping_list_database"

val viewModelModule = module {
    viewModel { CurrentShoppingListsFragmentViewModel(get()) }
    viewModel { ArchivedShoppingListsFragmentViewModel(get()) }
    viewModel { DetailsActivityViewModel(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): ShoppingListDatabase {
        return Room.databaseBuilder(
            application,
            ShoppingListDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    fun provideDao(database: ShoppingListDatabase): ShoppingListDao = database.shoppingListDao()

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideRepository(dao: ShoppingListDao): ShoppingListRepository =
        ShoppingListRepository(dao)

    single { provideRepository(get()) }
}

