package com.fetch.challenge.receiptprocessor.services

import org.springframework.stereotype.Service
import java.util.*

@Service
class IdGeneratorService {
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}
