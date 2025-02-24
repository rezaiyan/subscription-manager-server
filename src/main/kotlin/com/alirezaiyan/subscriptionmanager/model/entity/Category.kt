package com.alirezaiyan.subscriptionmanager.model.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Category(@JsonValue val value: String) {
    Streaming("Streaming"),
    Music("Music"),
    Audiobooks("Audiobooks"),
    Productivity("Productivity"),
    CloudStorage("Cloud Storage"),
    Software("Software"),
    Education("Education"),
    Gaming("Gaming"),
    Health("Health"),
    Meditation("Meditation"),
    News("News"),
    Reading("Reading"),
    Utilities("Utilities"),
    NA("NA");

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): Category {
            return entries.firstOrNull { it.value.equals(value, ignoreCase = true) } ?: NA
        }
    }
}
