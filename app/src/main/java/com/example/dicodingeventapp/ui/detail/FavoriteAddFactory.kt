package com.example.dicodingeventapp.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteAddFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: FavoriteAddFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteAddFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteAddFactory::class.java) {
                    INSTANCE = FavoriteAddFactory(application)
                }
            }
            return  INSTANCE as FavoriteAddFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteAddViewModel::class.java)) {
            return FavoriteAddViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}
