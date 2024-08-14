package node

import type.PrimitiveType

interface Node {
    fun getChildren(): List<Node>
    fun addChildren(children: Node): Node
    fun getType(): PrimitiveType
}