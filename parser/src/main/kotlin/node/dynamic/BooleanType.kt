package node.dynamic

import operations.DynamicVisitor

class BooleanType(override var result: LiteralValue?) : DynamicNode {

    override fun visit(visitor: DynamicVisitor) {
        visitor.acceptBoolean(this)
    }

    override fun toString(): String {
        return "DivisionType($result)"
    }
}
