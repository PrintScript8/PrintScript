package strategy.staticStrategy

import json.FormattingRules
import node.staticpkg.PrintLnType
import strategy.FormatStrategy
import strategy.provider.DynamicStrategyProvider
import java.io.Writer

class PrintLnStrategy: FormatStrategy<PrintLnType> {

    private val dynamicFactory = DynamicStrategyProvider()

    override fun apply(node: PrintLnType, rules: FormattingRules, writer: Writer) {

        val newlineBeforePrintln = rules.newlineBeforePrintln
        if (newlineBeforePrintln > 0) {
            writer.write("\n".repeat(newlineBeforePrintln))
        }
        writer.write("    ".repeat(rules.indentation))

        writer.write("println(")

        val dynamicStrategy = dynamicFactory.getStrategy(node.argument, rules.version)
        dynamicStrategy.apply(node.argument, rules, writer)

        writer.write(");")
    }
}