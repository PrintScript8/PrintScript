package rules.format

import node.staticpkg.StaticNode

interface FormatRule {
    fun analyzeFormat(root: StaticNode)
}