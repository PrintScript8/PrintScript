package node.staticpkg

import node.TypeValue

class DeclarationType(val modifier: ModifierType, val type: IdentifierType, val name: String) : StaticNode {

    override fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        require(modifier.canModify || version != "1.0") {
            "const not supported in this version"
        }
        val newMap = valueMap + (name to Pair(modifier.canModify, TypeValue(null, type.type)))
        return StaticResult(newMap, emptyList())
    }

    override fun toString(): String {
        return "DeclarationType( \n modifier= '$modifier' , type= $type \n, name= $name \n)"
    }
}
