package node

import parser.PrimitiveType

interface Node {
    fun getChildren(): List<Node>
    fun getType(): PrimitiveType
    fun apply(): Node
    fun print(): String
}