package com.sage.tddo.projectservice.application.service

import org.springframework.stereotype.Component
import java.util.*

@Component
class UUIDProjectIdGenerator : ProjectIdGenerator {
    override fun generateId(): String {
        return UUID.randomUUID().toString()
    }

}
