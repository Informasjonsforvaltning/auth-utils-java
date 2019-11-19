package no.brreg.informasjonsforvaltning

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import no.brreg.informasjonsforvaltning.jwk.JwkStore
import no.brreg.informasjonsforvaltning.jwk.JwtToken

class MockServer {
    private val mockServer : WireMockServer
    private val config : ServerConfig;

     constructor(config: ServerConfig){
        mockServer = WireMockServer((config.port))
        this.config = config
    }

    fun startMockServer() {
        if (!mockServer.isRunning) {
            mockServer.stubFor(
                get(urlEqualTo("/ping"))
                    .willReturn(
                        aResponse()
                            .withStatus(200)
                    )
            )

            mockServer.stubFor(
                get(urlEqualTo("/jwk"))
                    .willReturn(okJson(JwkStore.get()))
            )

            mockServer.stubFor(
                get(urlEqualTo("/jwt/read"))
                    .willReturn(okJson("{ token: ${JwtToken.buildRead(config.type)}}"))
            )
            mockServer.stubFor(
                get(urlEqualTo("jwt//write"))
                    .willReturn(okJson("{ token: ${JwtToken.buildWrite(config.type)}}"))
            )
            mockServer.stubFor(
                get(urlEqualTo("jwt//root"))
                    .willReturn(okJson("{ token: ${JwtToken.buildRoot(config.type)}}"))
            )
            mockServer.start()
        }
    }

    fun stopMockServer() {

        if (mockServer.isRunning) mockServer.stop()

    }
}

data class ServerConfig(val port: Int = 8084, val type : String = "publisher" )