package bcr.mvvm_kotlin.network

import bcr.mvvm_kotlin.data.BASE_URL
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by rumahulya on 20/04/18.
 */
class ServiceFactoryTest {

    private var spec: RequestSpecification = RequestSpecBuilder().
            setContentType(ContentType.JSON).
            setBaseUri(BASE_URL).
            addFilter(ResponseLoggingFilter()).
            addFilter(RequestLoggingFilter()).build()

    @Test
    fun provideApi(){
            given().
                    spec(spec)
                    .pathParam("username", "mojombo")
                    .`when`()
                    .get("users/{username}/repos")
                    .then()
                    .statusCode(200)
    }

    @After
    fun tearDown() {
    }

}