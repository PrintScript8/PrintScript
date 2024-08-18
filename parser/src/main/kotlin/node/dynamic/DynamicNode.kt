package node.dynamic

import operations.DynamicVisitor
import org.example.node.Node
import type.LiteralValue

interface DynamicNode : Node{
    var result: LiteralValue?
    fun visit(visitor: DynamicVisitor)
}