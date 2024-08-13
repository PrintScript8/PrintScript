package node

import jdk.nashorn.internal.objects.NativeArray.reduce
import parser.PrimitiveType
import java.util.Collections.emptyList

class Sum : Node {

    private val children: List<Node> = emptyList<Node>()

    override fun getChildren(): List<Node> {
        return children
    }

    override fun getType(): PrimitiveType {
        return PrimitiveType.SUM
    }

    override fun apply(): Node {
        return reduce()
    }

    override fun print(): String {
        var result = ""
        for (i in 0 .. children.size - 1) {
            result += children[i].print()
            if (i < children.size - 1) {
                result += " + "
            }
        }
        result += children[children.size - 1].print()
        return result
    }
}