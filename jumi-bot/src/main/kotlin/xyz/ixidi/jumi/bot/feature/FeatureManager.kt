package xyz.ixidi.jumi.bot.feature

import xyz.ixidi.jumi.riotapi.LeagueClientApi
import kotlin.reflect.KClass

class FeatureManager(
    private val lcu: LeagueClientApi
) {

    private val features = ArrayList<Feature<*>>()

    fun <C> add(feature: Feature<C>, config: C.() -> Unit = {}) {
        feature.configure(config)
        features.add(feature)
    }

    fun <C> addAndEnable(feature: Feature<C>, config: C.() -> Unit = {}) {
        add(feature.apply { enable(lcu) }, config)
    }

    fun <F : Feature<*>> get(featureClazz: KClass<F>) =
        features.filterIsInstance(featureClazz::class.java).firstOrNull()

    inline fun <reified F : Feature<*>> get() = get(F::class)

}