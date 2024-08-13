package type

import node.Node

interface PrimitiveType {
    fun resultType(children: List<Node>): PrimitiveType
    fun apply()
    fun print(children: List<Node>): String
}