package node.staticpkg

import node.Node
import operations.StaticVisitor

interface StaticNode : Node {
    fun visit(visitor: StaticVisitor)
}
