package no.brreg.informasjonsforvaltning.jwk

import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.util.*


object JwtToken {
    private var aud : MutableList<String> = mutableListOf<String>("a-backend-service","concept-catalogue","organization-catalogue ","fdk-admin-harvester","registration-api")
    private var authorities: String? = null

    fun addAudience(addValues: String){
        val values = addValues.split(",")
        values.forEach {
            aud.add(it)
        }
        println("[INFO]$addValues added to audience jwt field")
    }

    fun buildRead(path: String = "publisher"): String{
        authorities = getAccess(path, AccessType.READ)
        return buildToken()
    }

    fun buildWrite(path: String = "publisher"): String{
        authorities = getAccess(path, AccessType.WRITE)
        return buildToken()
    }
    fun buildRoot(path: String = "publisher"): String{
        authorities = getAccess(path, AccessType.ROOT)
        return buildToken()
    }


    private fun buildToken() : String{
        val claimset = JWTClaimsSet.Builder()
                .audience(aud)
                .expirationTime(Date(Date().time + 3600 * 3600))
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
