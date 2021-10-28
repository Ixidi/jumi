package xyz.ixidi.jumi.bot.feature

import kotlinx.coroutines.CoroutineScope
import xyz.ixidi.jumi.riotapi.LeagueClientApi
import xyz.ixidi.jumi.riotapi.gateway.event.GatewayEvent
import kotlin.reflect.KClass

class ListenerDeclaration<E : GatewayEvent>(
    val clazz: KClass<E>,
    val listener: suspend E.() -> Unit
) {

    fun createAndStart(lcu: LeagueClientApi, coroutineScope: CoroutineScope) {
        lcu.on(clazz, coroutineScope, listener)
    }

}