package interpreter

import node.staticpkg.StaticNode

interface Intepreter {
    fun excecute(list: List<StaticNode>)
}