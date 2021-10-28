package xyz.ixidi.jumi.riotapi.util

data class ProcessInfo(
    val path: String,
    val flags: Map<String, String?>
)

class ProcessNotFoundException : Exception("Process not found.")

private val processRegex = Regex("\"([^\"]*)\"")

fun fetchProcessInfo(name: String): ProcessInfo {
    val result = Runtime.getRuntime().exec("WMIC PROCESS WHERE name=\'${name}\' GET CommandLine")

    val error = result.errorStream
        .readBytes()
        .decodeToString()
        .trim()

    if (error == "No Instance(s) Available.") throw ProcessNotFoundException()

    val output = result.inputStream
        .readBytes()
        .decodeToString()
        .trim()
        .split("\n")[1]

    val matches = processRegex.findAll(output)
        .map { it.groups[1]!!.value }

    val path = matches.first()

    val flags = matches.drop(1).associate {
        val split = it.split("=")

        val flagName = split[0].drop(2)
        val flagValue = if (split.size == 1) null else split[1]

        flagName to flagValue
    }

    return ProcessInfo(path, flags)
}