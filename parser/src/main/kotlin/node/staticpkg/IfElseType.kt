package node.staticpkg

import node.TypeValue
import node.dynamic.LiteralValue
import operations.StaticVisitorV1

class IfElseType(
    private val ifBranch: StaticNode,
    private val elseBranch: StaticNode,
    private val boolean: LiteralValue.BooleanValue
) : StaticNode {
    override fun visit(visitor: StaticVisitorV1) {
        TODO("Not yet implemented")
    }

    override fun run(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): StaticResult {
        require(version != "1.0") { "IfElse is not supported in version 1.0" }
        return if (boolean.boolean) {
            ifBranch.run(valueMap, version)
        } else {
            elseBranch.run(valueMap, version)
        }
    }

    override fun format(version: String): String {
        val ifBody: String = ifBranch.format(version)
        val elseBody: String = elseBranch.format(version)
        return "if ($boolean) {\n" +
            "    $ifBody\n" +
            "} else {\n" +
            "    $elseBody\n" +
            "}"
    }
}
