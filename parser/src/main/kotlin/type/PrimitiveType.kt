package type

import node.Node

interface PrimitiveType {
    fun apply(children: List<Node>): PrimitiveType
    fun print(children: List<Node>): String
}