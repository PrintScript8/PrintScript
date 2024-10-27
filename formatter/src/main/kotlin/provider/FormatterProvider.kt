package provider

import formatter.Formatter
import json.parseJsonRules
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import strategy.provider.StrategyProvider

class FormatterProvider {

    fun provideFormatter(rules: String, version: String): Formatter {

        val parsedRules = Json.parseToJsonElement(rules).jsonObject
        val addConfig = mapOf("version" to JsonPrimitive(version))
        val config = addElementsToJson(parsedRules, addConfig)
        val formattingRules = parseJsonRules(config.toString())
        val strategies = StrategyProvider().provideStrategy(version)

        return when (version) {
            "1.0" -> Formatter(formattingRules, strategies)
            "1.1" -> Formatter(formattingRules, strategies)
            else -> throw IllegalArgumentException("Unsupported version")
        }
    }

    private fun addElementsToJson(original: JsonObject, elements: Map<String, JsonPrimitive>): JsonObject {
        return buildJsonObject {
            original.forEach { (key, value) -> put(key, value) }
            elements.forEach { (key, value) -> put(key, value) }
        }
    }
}