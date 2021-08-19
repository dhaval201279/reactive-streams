package com.its.reactivestreams.controller;

import com.its.reactivestreams.entity.Foo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Random;

@ExtendWith(SpringExtension.class)
//@WebFluxTest(controllers = RxStreamControllerTest.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RxStreamControllerTest {

    @Autowired
    WebTestClient webClient;

    @Test
    void testGetStreamOfFoos()
    {
        /*List<Foo> foos = webClient
                            .get()
                            .uri("/foostream")
                            //.header(HttpHeaders.ACCEPT, "text/event-stream")
                            .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                            .exchange()
                            .expectStatus().isOk()
                            .returnResult(Foo.class)
                            .getResponseBody()
                            .take(3)
                            .collectList()
                            .block();

        foos
            .forEach(aFoo -> System.out.println(aFoo));

        Assertions
            .assertEquals(3, foos.size());*/

        Flux<Foo> foos = webClient
                .get()
                .uri("/foostream")
                //.header(HttpHeaders.ACCEPT, "text/event-stream")
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .exchange()
                .expectStatus().isOk()
                .returnResult(Foo.class)
                .getResponseBody()
                /*.take(3)
                .collectList()
                .block()*/;

        StepVerifier
            .create(foos)
            .expectNextMatches(aFoo -> aFoo.getName().equals("abc"))
            .expectNextMatches(aFoo -> aFoo.getName().equals("abc"))
            .expectNextMatches(aFoo -> aFoo.getName().equals("abc"))
            .thenCancel()
            .verify();

    }
}
