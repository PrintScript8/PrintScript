package type

import node.Node

class StringType(private val value: String): PrimitiveType {

    override fun apply(children: List<Node>): PrimitiveType {
        return StringType(value)
    }

    override fun print(children: List<Node>): String {
        return value
    }
}