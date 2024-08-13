package node

import type.PrimitiveType

class AST(private val type: PrimitiveType, private val children: List<Node>) : Node {

    override fun getChildren(): List<Node> {
        return children
    }

    override fun getType(): PrimitiveType {
        return type.resultType(children)
    }

    override fun addChildren(child: Node): Node {
        return AST(type, children + child)
    }
}