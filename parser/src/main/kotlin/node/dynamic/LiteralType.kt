package node.dynamic

import operations.DynamicVisitor

sealed class LiteralValue {
    data class NumberValue(val number: Number) : LiteralValue() {
        override fun toString(): String {
            return if (number.toDouble() % 1 == 0.0) {
                number.toInt().toString()
            } else {
                number.toString()
            }
        }
    }
    data class StringValue(val string: String) : LiteralValue() {
        override fun toString(): String {
            return string
        }
    }

    operator fun plus(other: LiteralValue): LiteralValue {
        return when (this) {
            is StringValue -> {
                when (other) {
                    is StringValue -> StringValue(this.string + other.string)
                    is NumberValue -> StringValue(this.string + other.number.toString())
                }
            }
            is NumberValue -> {
                when (other) {
                    is StringValue -> StringValue(this.number.toString() + other.string)
                    is NumberValue -> NumberValue(this.number.toDouble() + other.number.toDouble())
                }
            }
        }
    }

    operator fun minus(other: LiteralValue): LiteralValue {
        require(this is NumberValue && other is NumberValue) {
            "Subtraction is only supported for Number values"
        }
        return NumberValue(this.number.toDouble() - other.number.toDouble())
    }

    operator fun div(other: LiteralValue): LiteralValue {
        require(this is NumberValue && other is NumberValue) {
            "Division is only supported for Number values"
        }
        return NumberValue(this.number.toDouble() / other.number.toDouble())
    }

    operator fun times(other: LiteralValue): LiteralValue {
        require(this is NumberValue && other is NumberValue) {
            "Multiplication is only supported for Number values"
        }
        return NumberValue(this.number.toDouble() * other.number.toDouble())
    }
}

class LiteralType(override var result: LiteralValue?) : DynamicNode {
    override fun visit(visitor: DynamicVisitor) {
        visitor.acceptLiteral(this)
    }
}
