package bcr.mvvm_kotlin.network

import bcr.mvvm_kotlin.data.BASE_URL
import io.restassured.RestAssured.*
import io.restassured.matcher.RestAssuredMatchers.*
import org.hamcrest.Matchers.*
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification

/**
 * Created by rumahulya on 18/04/18.
 */
internal class ServiceFactoryTest {


    lateinit var spec: RequestSpecification

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        spec    = RequestSpecBuilder().
                    setContentType(ContentType.JSON).
                    setBaseUri(BASE_URL).
                    addFilter(ResponseLoggingFilter()).
                    addFilter(RequestLoggingFilter()).build()
    }

    @org.junit.jupiter.api.Test
    fun provideApi() {
        given().
                spec(spec)
                .pathParam("username", "mojombo")
                .`when`()
                .get("users/{username}/repos")
                .then()
                .statusCode(200)
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {

    }

}