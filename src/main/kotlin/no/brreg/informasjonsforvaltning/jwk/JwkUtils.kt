package no.brreg.informasjonsforvaltning.jwk

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.*
import com.nimbusds.jose.jwk.gen.*
import java.util.UUID


object JwkStore{
    private val jwk = createJwk()

    private fun createJwk(): RSAKey {
        val  created = RSAKeyGenerator(2048)
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .generate()

        return created
    }

    fun get(): String {
        val token : JwkToken = jacksonObjectMapper()
                .readValue(jwk.toJSONString())

        return token.toString()
    }

    fun jwtHeader() = (
            JWSHeader.Builder(JWSAlgorithm.RS256)
                    .keyID(jwk.keyID)
                    .build()
            )

    fun signer() = (
            RSASSASigner(jwk)
            )

}

@JsonIgnoreProperties(ignoreUnknown = true)
class JwkToken(
        private val kid : String,
        private val kty :String,
        private val use : String,
        private val n : String,
        private val e : String
){

    override fun toString(): String {
        return  "{\n"+
                " \"keys\": [\n" +
                "   {\n" +
                "     \"kid\": \"$kid\",\n" +
                "     \"kty\": \"$kty\",\n" +
                "     \"alg\": \"RS256\",\n" +
                "     \"use\": \"$use\",\n" +
                "     \"n\": \"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw52fY3bcQfa8ZxE3yreogixiA4TgD8kHP6P40BdWKw3VyOI8p8gD0quH0Eqt4MVPxAuZkn4wxaIFZkDXHpI1ihextXGUbhiysx0UJDRbYsQwNI9WwXP9640igwQ8Gglj1V5PBll8WECgfzPBJpJLWYei8EwA8T9cuJoKo6P47vtvi3B81ib2fXhmJkgYVR8eXh04O2zFB5Kn5b5QHKkjIXjtsEyL13oLGBPBom2TJfAY3HQoalWhLUsHcQwiP95lBqK4pYaFLPfNVvYJlKkdkcrj7Z6V6hPe3dv6JvZRALpgPInlEi4T55WnROFtdyYIb540UtPpXHklWsO7Wk88hwIDAQAB\",\n" +
                "     \"e\": \"$e\"\n" +
                "   }\n" +
                " ]\n" +
                "}"

    }
}