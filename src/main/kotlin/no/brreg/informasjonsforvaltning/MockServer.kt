package no.brreg.informasjonsforvaltning

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import no.brreg.informasjonsforvaltning.extensions.JwtReadTransformer
import no.brreg.informasjonsforvaltning.extensions.JwtWriteTransformer
import no.brreg.informasjonsforvaltning.jwk.AccessStringParts
import no.brreg.informasjonsforvaltning.jwk.JwkStore
import no.brreg.informasjonsforvaltning.jwk.JwtToken
import no.brreg.informasjonsforvaltning.jwk.JwtToken.buildRead
import no.brreg.informasjonsforvaltning.jwk.JwtToken.buildRoot
import no.brreg.informasjonsforvaltning.jwk.JwtToken.buildWrite

class MockServer {
    private val mockServer : WireMockServer

     constructor(){
         val port: Int  = (System.getenv("port") ?: System.getProperty("custom.port") ?: "8084").toInt()

         mockServer = WireMockServer(wireMockConfig()
            .extensions(JwtReadTransformer::class.java,JwtWriteTransformer::class.java)
            .port(port))
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
                get(urlMatching("/jwt/read[a-z0-9\\?\\=\\&]*"))
                    .willReturn(aResponse().withTransformers("jwt-read-transform"))
            )

            mockServer.stubFor(
                get(urlMatching("/jwt/write[a-z0-9\\?\\=\\&]*"))
                    .willReturn(aResponse().withTransformers("jwt-write-transform"))
            )

            mockServer.stubFor(
                get(urlMatching("/jwt/admin[a-z\\?\\=]*"))
                    .willReturn(okJson("{ token: ${JwtToken.buildRoot()}}"))
            )
            mockServer.start()
            val infoString = JwtToken.config()
            println("Auth server is listening on port ${mockServer.port()}")
            println("AccessString values are ${infoString}")
            println("\n---  READ TOKEN -----")
            println(buildRead())
            println("\n---  WRITE TOKEN -----")
            println(buildWrite())
            println("\n---  ROOT TOKEN -----")
            println(buildRoot())

        }
    }

    fun stopMockServer() {

        if (mockServer.isRunning) mockServer.stop()

    }
}
