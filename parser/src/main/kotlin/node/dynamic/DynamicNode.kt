package node.dynamic

import operations.DynamicVisitor
import org.example.node.Node

interface DynamicNode : Node{
    var result: LiteralValue?
    fun visit(visitor: DynamicVisitor)
}