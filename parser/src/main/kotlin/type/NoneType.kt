package type

import node.Node

class NoneType: PrimitiveType {
    override fun apply(children: List<Node>): PrimitiveType {
        throw IllegalArgumentException("Invalid type in none")
    }

    override fun print(children: List<Node>): String {
        return ""
    }
}