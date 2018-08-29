package com.evgeniy.royality.DI

import com.evgeniy.restapp.DI.Modules.AndroidModule
import com.evgeniy.restapp.DI.Modules.NetModule
import com.evgeniy.restapp.DI.Modules.RepositoryModule
import com.evgeniy.royality.App
import com.evgeniy.royality.BonusFragment
import com.evgeniy.royality.LoginActivity
import com.evgeniy.royality.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class, NetModule::class, RepositoryModule::class])
interface AppComponent {
    //Injects
    fun inject(app: App)
    fun inject(app: LoginActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(bonusFragment: BonusFragment)
}