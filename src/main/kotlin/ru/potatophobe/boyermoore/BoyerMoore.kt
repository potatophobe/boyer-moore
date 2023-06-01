package ru.potatophobe.boyermoore

fun String.fullSearchFirstIndexOf(substring: String): Int {
    var i = 0
    while (i < this.length - substring.length + 1) {
        var j = 0
        while (j < substring.length && this[i + j] == substring[j]) {
            j++
        }
        if (j > substring.lastIndex) {
            return i
        }
        i++
    }
    return -1
}

fun String.boyerMooreHorspoolFirstIndexOf(substring: String): Int {
    val charStepMap = calculateCharStepMap(substring)
    var i = 0
    while (i < this.length - substring.length + 1) {
        var j = substring.lastIndex
        while (j >= 0 && this[i + j] == substring[j]) {
            j--
        }
        if (j < 0) {
            return i
        }
        i += charStepMap[this[i + substring.lastIndex].code]
    }
    return -1
}

fun String.boyerMooreFirstIndexOf(substring: String): Int {
    val charStepMap = calculateCharStepMap(substring)
    val suffixStepMap = calculateSuffixStepMap(substring)
    var i = 0
    while (i < this.length - substring.length + 1) {
        var j = substring.lastIndex
        while (j >= 0 && this[i + j] == substring[j]) {
            j--
        }
        if (j < 0) {
            return i
        }
        val suffixMatchSize = substring.lastIndex - j
        i += if (suffixMatchSize > 0) suffixStepMap[suffixMatchSize]
        else charStepMap[this[i + substring.lastIndex].code]
    }
    return -1
}

private fun calculateCharStepMap(string: String): IntArray {
    return IntArray(Char.MAX_VALUE.code) { string.length }
        .also {
            for (i in 0 until string.lastIndex) {
                it[string[i].code] = string.lastIndex - i
            }
        }
}

private fun calculateSuffixStepMap(string: String): IntArray {
    return IntArray(string.length - 1) {
        var substringSize = it
        var substringLastIndex = -1
        while (substringSize > 0 && substringLastIndex < 0) {
            substringLastIndex = string.lastIndexOf(string.takeLast(substringSize))
            substringSize--
        }
        if (substringLastIndex < 0) string.length else string.lastIndex - substringLastIndex
    }
}