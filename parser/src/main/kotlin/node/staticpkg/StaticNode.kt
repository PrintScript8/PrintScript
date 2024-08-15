package node.staticpkg

import operations.StaticVisitor

interface StaticNode {
    fun visit(visitor: StaticVisitor)
}