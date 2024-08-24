package parser.strategies

import node.dynamic.*
import node.staticpkg.PrintLnType
import org.example.node.Node
import token.CloseParenthesis
import token.Token


class MethodStrategy : ParseStrategy {

    private val rightSideParser:RightSideParser = RightSideParser()

    override fun parse(tokens: List<Token>, currentIndex: Int, statementNodes: MutableList<Node>): Int {
        if(statementNodes.isNotEmpty()){
            throw IllegalArgumentException("Can't call nativeMethod '${tokens[currentIndex].text}' with other arguments before it")
        }
        else{
            if(currentIndex + 1 >= tokens.size || tokens[currentIndex+1].type == CloseParenthesis){
                throw IllegalArgumentException("Expected arguments for method")
            }
            else{
                val tuple: Pair<DynamicNode, Int> = rightSideParser.parseRightHandSide(tokens, currentIndex + 1, CloseParenthesis)
                val argument: DynamicNode = tuple.first
                val resultIndex: Int = tuple.second
                statementNodes.add(PrintLnType(argument))
                return resultIndex
            }
        }
    }
}