package com.gurunelee.optlock.domains.converters

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * Created by chlee on 5/1/24.
 *
 * @author Changha Lee
 * @version opt-lock
 * @since opt-lock
 */
@Converter
class StringBooleanConverter: AttributeConverter<Boolean?, String?> {
    override fun convertToDatabaseColumn(value: Boolean?): String? {
        return when (value) {
            true -> "Y"
            false -> "N"
            else -> null
        }
    }

    override fun convertToEntityAttribute(value: String?): Boolean? {
        return when (value) {
            "Y" -> true
            "N" -> false
            else -> null
        }
    }
}
