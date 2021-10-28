package xyz.ixidi.jumi.bot.autojoin

class AutoJoinFeatureConfig {

    sealed class JoinCondition {

        object Always : JoinCondition()
        class SpecifiedSummoner(vararg val name: String) : JoinCondition()

    }

    var joinCondition: JoinCondition = JoinCondition.Always
    var joinIfAlreadyInLobby = false

}