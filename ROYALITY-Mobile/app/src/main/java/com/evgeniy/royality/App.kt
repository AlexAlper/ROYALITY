package com.evgeniy.royality

import android.app.Application
import com.evgeniy.restapp.DI.Modules.AndroidModule
import com.evgeniy.restapp.DI.Modules.NetModule
import com.evgeniy.restapp.DI.Modules.RepositoryModule
import com.evgeniy.royality.DI.AppComponent
import com.evgeniy.royality.DI.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = initDaggerComponent()
        component.inject(this)
    }

    protected fun initDaggerComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .netModule(NetModule(getString(R.string.base_url)))
                .androidModule(AndroidModule(this))
                .repositoryModule(RepositoryModule())
                .build()
    }
}