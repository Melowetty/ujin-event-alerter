package ru.melowetty.ujineventalerter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class UjinEventAlerterApplication

fun main(args: Array<String>) {
    runApplication<UjinEventAlerterApplication>(*args)
}
