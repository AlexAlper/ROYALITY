package com.evgeniy.restapp.DI.Modules

import android.content.Context
import com.evgeniy.royality.Data.Repository
import com.evgeniy.royality.Net.Api
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepository(api: Api, context: Context): Repository {
        return Repository(api, context)
    }
}