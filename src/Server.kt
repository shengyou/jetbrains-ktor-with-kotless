package io.kraftsman

import com.fasterxml.jackson.databind.SerializationFeature
import io.kotless.dsl.ktor.Kotless
import io.kraftsman.services.ContactService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.time.ExperimentalTime

@Suppress("unused")
class Server : Kotless() {

    @ExperimentalTime
    override fun prepare(app: Application) {

        app.install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }

        app.routing {

            get("/") {
                call.respondText("Hello, Ktor on Kotless!", contentType = ContentType.Text.Plain)
            }

            get("/contacts") {
                val param = call.request.queryParameters["amount"]
                val amount = param?.toIntOrNull() ?: 10

                val contacts = ContactService().generate(amount)

                call.respond(mapOf("data" to contacts))
            }
        }
    }
}