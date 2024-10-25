package strategy.staticStrategy

import json.FormattingRules
import node.staticpkg.AssignationType
import strategy.dynamicStrategy.DynamicStrategyFactory
import strategy.FormatStrategy
import java.io.Writer

class AssignationStrategy : FormatStrategy<AssignationType> {

    private val declarationStrategy = DeclarationStrategy()
    private val dynamicFactory = DynamicStrategyFactory()

    override fun apply(node: AssignationType, rules: FormattingRules, writer: Writer) {

        declarationStrategy.apply(node.declaration, rules, writer)

        if (rules.spaceAroundEquals) {
            writer.write(" = ")
        } else {
            writer.write("=")
        }

        val dynamicStrategy = dynamicFactory.getStrategy(node.value)

        dynamicStrategy.apply(node.value, rules, writer)
        writer.write(";\n")
    }
}