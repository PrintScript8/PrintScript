package node.dynamic

import node.Node
import operations.DynamicVisitor

interface DynamicNode : Node {
    var result: LiteralValue?
    fun visit(visitor: DynamicVisitor)
}
