package parser.elements

import node.staticpkg.StaticNode
import token.TokenInterface

interface Parser {
    fun parse(tokenInterfaces: List<TokenInterface>): List<StaticNode>
}
