package xyz.ixidi.jumi.bot.feature

import xyz.ixidi.jumi.riotapi.gateway.event.GatewayEvent
import kotlin.reflect.KClass

class FeatureCreator {

    private val _listenerDeclarations = ArrayList<ListenerDeclaration<*>>()
    val listenerDeclarations: List<ListenerDeclaration<*>>
        get() = _listenerDeclarations.toList()

    fun <E : GatewayEvent> on(clazz: KClass<E>, handler: suspend E.() -> Unit) {
        _listenerDeclarations.add(ListenerDeclaration(clazz, handler))
    }

    inline fun <reified E : GatewayEvent> on(noinline handler: suspend E.() -> Unit) = on(E::class, handler)

}