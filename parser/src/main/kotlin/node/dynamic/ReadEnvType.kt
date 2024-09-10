package node.dynamic

import node.TypeValue

class ReadEnvType(val argument: DynamicNode, override var result: LiteralValue?) : DynamicNode {

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): TypeValue {
        TODO("Not yet implemented")
    }

    override fun format(version: String): String {
        TODO("Not yet implemented")
    }
}
