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

        } catch (e: Exception) {
                if (e is NumberFormatException) {
                        throw java.lang.IllegalArgumentException("First argument [port] must be an integer")
                } else {
                        throw e
                }
        }

}