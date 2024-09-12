package parser.strategies

import node.Node
import node.staticpkg.IfElseType
import node.staticpkg.StaticNode
import parser.elements.StatementParser
import parser.elements.TokenHandler
import token.OpenBrace
import token.TokenInterface

class ElseStrategy : ParseStrategy{

    var tokenHandler: TokenHandler? = null

    override fun parse(
        tokenInterfaces: List<TokenInterface>,
        currentIndex: Int,
        statementNodes: MutableList<Node>
    ): Int {
        require(currentIndex + 1 < tokenInterfaces.size && tokenInterfaces[currentIndex + 1].type == OpenBrace){
            "else missing '{' for condition at: ${tokenInterfaces[currentIndex].position}"
        }
        val statementParser = StatementParser(tokenHandler!!)

        val ifNode = statementNodes.last()
        require(ifNode is IfElseType) { "Missing if statement before else at: ${tokenInterfaces[currentIndex].position}" }

        val elseBlock: List<StaticNode> = statementParser.parseStatement(
            createSubList(tokenInterfaces, 2, tokenInterfaces.lastIndex))
        val updatedIf = createIf(ifNode, elseBlock)
        statementNodes.add(updatedIf)
        return tokenInterfaces.lastIndex
        TODO("Not yet implemented")
    }

    private fun createIf(ifNode: IfElseType, elseBlock: List<StaticNode>): IfElseType{
        val ifBranch = ifNode.ifBranch
        val boolValue = ifNode.boolean
        return IfElseType(ifBranch, boolValue, elseBlock)
    }

    private fun <T> createSubList(originalList: List<T>, startIndex: Int, endIndex: Int): List<T> {
        require(startIndex in 0..originalList.size) { "Start index out of bounds" }
        require(endIndex in startIndex..originalList.size) { "End index out of bounds" }
        val newList = mutableListOf<T>()
        for (i in startIndex until endIndex) {
            newList.add(originalList[i])
        }
        return newList
    }
}