package no.brreg.informasjonsforvaltning

import no.brreg.informasjonsforvaltning.jwk.JwtToken.addAudience

fun startServer() {

        val port: Int  = (System.getenv("PORT") ?: System.getProperty("PORT") ?: "8084").toInt()
        val type : String = System.getenv("TYPE") ?: System.getProperty("TYPE") ?: "organisation"
        val aud : String? = System.getenv("AUD") ?: System.getProperty("AUD")
        if(aud!=null) {addAudience(aud)}

        val mockServer = MockServer(ServerConfig(port, type))
        mockServer.startMockServer();

}