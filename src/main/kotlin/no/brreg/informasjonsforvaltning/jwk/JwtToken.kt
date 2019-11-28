package no.brreg.informasjonsforvaltning.jwk

import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.util.*


object JwtToken {
    private var aud : MutableList<String> = mutableListOf<String>("a-backend-service","concept-catalogue","organization-catalogue ","fdk-admin-harvester","registration-api")
    private var orgNumber : String = "910244132"

    fun addAudience(addValues: String){
        val values = addValues.split(",")
        values.forEach {
            aud.add(it)
        }
        println("[INFO]$addValues added to audience jwt field")
    }

    fun buildRead(type: String?,org: String?): String{
        val auth = getAccess(type ?: "organization", org ?: orgNumber,Priveliges.READ)
        return buildToken(auth)
    }

    fun buildWrite(type: String?,org: String?): String{
        val auth = getAccess(type ?: "organization", org ?: orgNumber,Priveliges.WRITE)
        return buildToken(auth)
    }
    fun buildRoot(path: String = "organization"): String{
        val auth = getAccess(type = path, priveliges = Priveliges.ROOT)
        return buildToken(auth)
    }


    private fun buildToken(auth : String) : String{
        val claimset = JWTClaimsSet.Builder()
                .audience(aud)
                .expirationTime(Date(Date().time + 3600 * 3600))
                .claim("user_name","1924782563")
                .claim("name", "TEST USER")
                .claim("given_name", "TEST")
                .claim("family_name", "USER")
                .claim("authorities", auth)
                .build()

        val signed = SignedJWT(JwkStore.jwtHeader(), claimset)
        signed.sign(JwkStore.signer())

        return signed.serialize()

    }

    private fun getAccess(type : String, org: String? = orgNumber, priveliges : Priveliges) : String{
        return when (priveliges) {
            Priveliges.READ ->  "$type:$org:${access.ORG_READ}"
            Priveliges.WRITE ->  "$type:$org:${access.ORG_WRITE}"
            Priveliges.ROOT -> access.ROOT
        }
    }
    private  object access{
        val ORG_READ = "read"
        val ORG_WRITE = "admin"
        val ROOT = "system:root:admin"
    }
}

enum class Priveliges{
    READ,
    WRITE,
    ROOT
}
