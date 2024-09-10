package node.dynamic

import node.PrimType
import node.TypeValue

class ReadInputType(val argument: DynamicNode, override var result: LiteralValue?) : DynamicNode {

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): TypeValue {
        require(argument.execute(valueMap, version).component2() == PrimType.STRING) {
            "readInput method can only have String arguments, not ${argument.execute(valueMap, version).component2()}"
        }
        print("${argument.execute(valueMap, version).value}:\n> ")
        val input = readlnOrNull()

        return if (input != null) {
            when {
                input.equals("true", ignoreCase = true) || input.equals("false", ignoreCase = true) -> {
                    TypeValue(LiteralValue.BooleanValue(input.toBoolean()), PrimType.BOOLEAN)
                }
                input.toIntOrNull() != null -> {
                    TypeValue(LiteralValue.NumberValue(input.toInt()), PrimType.NUMBER)
                }
                input.toDoubleOrNull() != null -> {
                    TypeValue(LiteralValue.NumberValue(input.toDouble()), PrimType.NUMBER)
                }
                else -> {
                    // string case
                    TypeValue(LiteralValue.StringValue(input), PrimType.STRING)
                }
            }
        } else {
            throw IllegalArgumentException(
                "No value passed for input asked by: '" +
                    "${argument.execute(valueMap, version).value}'"
            )
        }
    }

    override fun format(version: String): String {
        return "readInput(${argument.format(version)})"
    }
}
