package strategy

import json.FormattingRules
import node.staticpkg.ModifierType
import java.io.Writer

class ModifierStrategy: FormatStrategy<ModifierType> {

    override fun apply(node: ModifierType, rules: FormattingRules, writer: Writer) {

        writer.write(node.value.toString())
    }

}