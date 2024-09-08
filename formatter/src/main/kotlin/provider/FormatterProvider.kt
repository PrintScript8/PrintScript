package provider

import formatter.FormatterInterface
import formatter.FormatterV1
import formatter.FormatterV2
import node.staticpkg.StaticNode

class FormatterProvider(private val iterator: Iterator<StaticNode>) {
    fun provideFormatter(version: String): FormatterInterface {
        return when (version) {
            "1.0" -> FormatterV1(iterator)
            "1.1" -> FormatterV2(iterator)
            else -> throw IllegalArgumentException("Version not found")
        }
    }
}
