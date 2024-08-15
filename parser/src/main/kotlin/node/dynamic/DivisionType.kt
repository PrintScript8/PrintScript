package node.dynamic

import operations.DynamicVisitor
import type.LiteralType
import type.LiteralValue

class DivisionType(val children: List<DynamicNode>, override var result: LiteralValue?) : DynamicNode {

    override fun visit(visitor: DynamicVisitor) {
        visitor.acceptDivision(this)
    }

}