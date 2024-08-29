package interpreter

import node.staticpkg.StaticNode

interface Interpreter {
    fun execute(list: List<StaticNode>): List<String>
}
