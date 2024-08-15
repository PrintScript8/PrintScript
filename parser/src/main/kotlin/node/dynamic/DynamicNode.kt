package node.dynamic

import operations.DynamicVisitor
import type.LiteralValue

interface DynamicNode {
    var result: LiteralValue?
    fun visit(visitor: DynamicVisitor)
}