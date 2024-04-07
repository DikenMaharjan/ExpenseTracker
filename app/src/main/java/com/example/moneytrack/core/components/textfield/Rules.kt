package com.example.moneytrack.core.components.textfield

import android.util.Patterns

class Rule(
    val validate: (String) -> Pair<Boolean, String>
) {
    fun getError(text: String): String? {
        val validity = validate(text)
        return if (validity.first) validity.second else null
    }

    operator fun plus(other: Rule): Rule {
        return Rule {
            val firstError = this.validate(it)
            val secondError = other.validate(it)
            if (firstError.first) {
                true to firstError.second
            } else {
                secondError.first to secondError.second
            }
        }
    }
}

object Rules {
    val requiredRule = Rule {
        it.isBlank() to "Required"
    }
    val emailRule = requiredRule + Rule {
        !it.isEmailValid() to "Invalid Email"
    }

    private val numbersRule = Rule {
        (it.toDoubleOrNull() == null) to "Invalid Input"
    }

    val amountRule = requiredRule + numbersRule

    private val alphaNumericCharacterRule = Rule { text ->
        text.any {
            !it.isLetter() && !it.isWhitespace() && !it.isDigit()
        } to "Invalid Characters"
    }

    val userNameRule = requiredRule + minimum4Rule(
        "Username should be greater than 3 characters"
    ) + alphaNumericCharacterRule

    private fun minimum4Rule(errorMsg: String) = Rule {
        (it.length <= 3) to errorMsg
    }
}

private fun String.isEmailValid(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
