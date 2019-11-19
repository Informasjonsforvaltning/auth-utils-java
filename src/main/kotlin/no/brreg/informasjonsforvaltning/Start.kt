package no.brreg.informasjonsforvaltning

fun startServer(args: Array<String>) {

        try {
                val mockServer = when (args.size) {
                        0 -> MockServer(ServerConfig())
                        1 -> MockServer(ServerConfig(port = args[0].toInt()))
                        2 -> MockServer(ServerConfig(args[0].toInt(), args[1]))
                        else -> throw IllegalArgumentException()
        }

        mockServer.startMockServer();
        println("Authmock started on port $args[0")

        } catch (e: Exception) {
        var message = "Too many arguments found"
        if (e is NumberFormatException) {
        message = "First argument [port] must be an integer"
        }

        throw IllegalArgumentException(message)
        }

}