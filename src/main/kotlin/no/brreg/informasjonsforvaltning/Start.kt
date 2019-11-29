package no.brreg.informasjonsforvaltning

import no.brreg.informasjonsforvaltning.jwk.JwtToken.addAudience

fun startServer() {


        val aud : String? = System.getenv("aud") ?: System.getProperty("custom.aud")
        if(aud!=null) {addAudience(aud)}

        val mockServer = MockServer()
        mockServer.startMockServer();

}