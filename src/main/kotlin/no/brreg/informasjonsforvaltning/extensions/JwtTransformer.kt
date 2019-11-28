package no.brreg.informasjonsforvaltning.extensions

import com.github.tomakehurst.wiremock.common.FileSource
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.extension.ResponseTransformer
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.http.Response
import no.brreg.informasjonsforvaltning.jwk.JwtToken.buildRead
import no.brreg.informasjonsforvaltning.jwk.JwtToken.buildWrite

class JwtReadTransformer : ResponseTransformer() {

    override fun applyGlobally(): Boolean = false;

    override fun getName(): String = "jwt-read-transform"

    override fun transform(p0: Request?, p1: Response?, p2: FileSource?, p3: Parameters?): Response {
        val parameters = if (p0 == null) Params() else Params(p0.url.split("?"))
        val token = buildRead(type = parameters.type, org = parameters.org)
        return Response.Builder.like(p1)
            .but()
            .body("{ token: $token}")
            .build()
    }
}

class JwtWriteTransformer : ResponseTransformer() {

    override fun applyGlobally(): Boolean = false;

    override fun getName(): String = "jwt-write-transform"

    override fun transform(p0: Request?, p1: Response?, p2: FileSource?, p3: Parameters?): Response {
        val parameters = if (p0 == null) Params() else Params(p0.url.split("?"))
        val token = buildWrite(type = parameters.type, org = parameters.org)
        return Response.Builder.like(p1)
            .but()
            .body("{ token: $token}")
            .build()
    }
}

data class Params(var type: String? = null, var org: String? = null) {

    constructor(paramsList: List<String>):this(){
        if(paramsList.size > 1)
            update(paramsList[1].split("&","="))
    }

    private fun update(params : List<String>){
        val typeIndex = params.indexOf("type")
        this.type = if (typeIndex > -1) {params[typeIndex + 1]} else null
        val orgIndex = params.indexOf("org")
        this.org = if (orgIndex > -1) {params[orgIndex + 1]} else null
    }
}
