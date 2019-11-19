package no.brreg.informasjonsforvaltning.jwk

import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.util.*


object JwtToken {
    private val exp = Date().time + 120 * 3600
    private val aud = listOf<String>("a-backend-service","concept-catalogue")
    private var authorities: String? = null

    public fun buildRead(path: String = "publisher"): String{
        authorities = getAccess(path, AccessType.READ)
        return buildToken()
    }

    public fun buildWrite(path: String = "publisher"): String{
        authorities = getAccess(path, AccessType.WRITE)
        return buildToken()
    }
    public fun buildRoot(path: String = "publisher"): String{
        authorities = getAccess(path, AccessType.ROOT)
        return buildToken()
    }


    private fun buildToken() : String{
        val claimset = JWTClaimsSet.Builder()
                .audience(aud)
                .expirationTime(Date(exp))
                .claim("user_name","1924782563")
                .claim("name", "TEST USER")
                .claim("given_name", "TEST")
                .claim("family_name", "USER")
                .claim("authorities", authorities)
                .build()

        val signed = SignedJWT(JwkStore.jwtHeader(), claimset)
        signed.sign(JwkStore.signer())

        return signed.serialize()

    }

    private fun getAccess(path : String, type : AccessType) : String{
        return when (type) {
            AccessType.READ ->  "$path${access.ORG_READ}"
            AccessType.WRITE ->  "$path${access.ORG_WRITE}"
            AccessType.ROOT -> access.ROOT
        }
    }
    private  object access{
        final val ORG_READ = ":910244132:read"
        final val ORG_WRITE = ":910244132:admin"
        final val ROOT = "system:root:admin"
    }
}

enum class AccessType{
    READ,
    WRITE,
    ROOT
}