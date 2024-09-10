package node.dynamic

import node.TypeValue

class ReadInputType(val argument: DynamicNode, override var result: LiteralValue?) : DynamicNode {

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): TypeValue {
        /*
        print("$text:\n> ")
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
            // default case
            TypeValue(LiteralValue.StringValue(""), PrimType.STRING)
        }

         */
        TODO("Not yet implemented")
    }

    override fun format(version: String): String {
        TODO("Not yet implemented")
        // return "readInput(\"$text\")"
    }
}
