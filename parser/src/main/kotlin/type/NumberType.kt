package type

import node.Node

class NumberType(private val value: Int): PrimitiveType { //Ver como resolvemos con el value double!!

    override fun apply(children: List<Node>): PrimitiveType {
        return NumberType(value)
    }

    override fun print(children: List<Node>): String {
        return value.toString()
    }
}