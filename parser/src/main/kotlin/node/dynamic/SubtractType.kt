package node.dynamic

import operations.DynamicVisitor
import type.LiteralType
import type.LiteralValue

class SubtractType(val left: DynamicNode, val right: DynamicNode, override var result: LiteralValue?): DynamicNode {
    override fun visit(visitor: DynamicVisitor) {
        visitor.acceptSubtract(this)
    }

    override fun toString(): String {
        return "SubtractType(left='$left', right=$right)"
    }
}