package node.staticpkg

import node.TypeValue

class StaticResult(val valueMap: Map<String, Pair<Boolean, TypeValue>>, val output: List<String>) {
    operator fun component1(): Map<String, Pair<Boolean, TypeValue>> {
        return valueMap
    }
    operator fun component2(): List<String> {
        return output
    }
}
