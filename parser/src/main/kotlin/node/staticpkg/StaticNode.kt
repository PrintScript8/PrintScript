package node.staticpkg

import operations.StaticVisitor
import org.example.node.Node

interface StaticNode : Node {
    fun visit(visitor: StaticVisitor)
}
