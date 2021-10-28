package xyz.ixidi.jumi.bot.feature

import kotlinx.coroutines.*
import xyz.ixidi.jumi.riotapi.LeagueClientApi
import xyz.ixidi.jumi.riotapi.rest.RestClient
import kotlin.coroutines.CoroutineContext

abstract class Feature<CONFIG>(
    name: String? = null,
    val config: CONFIG,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = dispatcher + SupervisorJob()

    val name = name ?: this::class.simpleName

    var enabled = false
        private set

    fun enable(lcu: LeagueClientApi) {
        if (enabled) throw Exception("Feature $name is already enabled.")

        val creator = FeatureCreator()
        creator.create(lcu.rest)
        creator.listenerDeclarations.forEach {
            it.createAndStart(lcu, this)
        }
    }

    fun disable() {
        if (!enabled) throw Exception("Feature $name is disabled.")

        enabled = false
        cancel()
    }

    fun configure(run: CONFIG.() -> Unit) {
        run(config)
    }

    protected abstract fun FeatureCreator.create(rest: RestClient)

}