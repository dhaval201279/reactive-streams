package com.its.reactivestreams.controller;

import com.its.reactivestreams.entity.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

@RestController
@Slf4j
public class RxStreamController {

    @GetMapping(value = "rxstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> fetchStreamOfData () {
        log.info("Entering RxStreamController : fetchStreamOfData");

        Flux<String> responseStream = Flux
                                        .fromStream(
                                            Stream
                                                .generate(() -> new String("rx-stream-" + new Date() + "-" +
                                                    Math.abs(new Random().nextLong())))
                                                .peek( msg -> log.info("Peeked string : {} ", msg ))
                                        )
                                        .delayElements(Duration.ofSeconds(1));

        log.info("RxStreamController : fetchStreamOfData in non blocking way !!!!!!!  ");
        return responseStream;
    }

    @GetMapping(value = "foostream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Foo> fetchStreamOfFoo () {
        log.info("Entering RxStreamController : fetchStreamOfFoo");

        Flux<Foo> fooResponseStream = Flux
                                        .fromStream(
                                            Stream
                                                .generate(() -> new Foo(Math.abs(new Random().nextLong()), "abc"))
                                                .peek(msg -> log.info("Peeked string : {} ", msg))
                                        )
                                        .delayElements(Duration.ofSeconds(1));

        log.info("Leaving RxStreamController : fetchStreamOfFoo in non blocking way !!!!!!!  ");
        return fooResponseStream;
    }
}
