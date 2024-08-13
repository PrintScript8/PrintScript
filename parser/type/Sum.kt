package type

import node.Node

class Sum : PrimitiveType {

    override fun resultType(children: List<Node>): PrimitiveType {
        return this
    }

    override fun apply() {
        println("Applying Sum")
    }

    override fun print(children: List<Node>): String {
        var result: String = ""
        for (child in children) {
            result += child.getType(child.getChildren()).print(child.getChildren()) + " + "
        }
        return result
    }
}