package type

import node.Node

class SumType : PrimitiveType {

    override fun apply(children: List<Node>): PrimitiveType {
        if (!checkValidType(children)) {
            throw IllegalArgumentException("Invalid type in sum")
        }
        var result = children.first().getType()
        for (i in 1 until children.size) {
            result = if (result is StringType || children[i].getType() is StringType) {
                StringType(result.print(children) + children[i].getType().print(children[i].getChildren()))
            } else {
                NumberType(result.apply(children).print(children).toInt() +
                        children[i].getType().apply(children).print(children[i].getChildren()).toInt())
            }
        }
        return result
    }

    override fun print(children: List<Node>): String {
        var result: String = children.first().getType().print(children.first().getChildren())
        for (i in 1 until children.size) {
            result += " + " + children[i].getType().print(children[i].getChildren())
        }
        return result
    }

    private fun checkValidType(list: List<Node>): Boolean {
        for (element in list) {
            if (element.getType() !is NumberType && element.getType() !is StringType) {
                return false
            }
        }
        return true
    }
}