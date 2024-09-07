package interpreter

import node.staticpkg.StaticNode

interface Interpreter {
    fun execute(): List<String>
}
