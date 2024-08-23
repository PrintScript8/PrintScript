package node.dynamic

import operations.DynamicVisitor

interface DynamicNode {
    var result: LiteralValue?
    fun visit(visitor: DynamicVisitor)
}
