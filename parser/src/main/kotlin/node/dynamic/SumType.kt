package node.dynamic

import operations.DynamicVisitor

class SumType(val left: DynamicNode, val right: DynamicNode, override var result: LiteralValue?) : DynamicNode {
    override fun visit(visitor: DynamicVisitor) {
        visitor.acceptSum(this)
    }
}
