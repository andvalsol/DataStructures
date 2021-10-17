package stacksAndQueues

import java.util.ArrayDeque

fun compareBuildings(buildings: ArrayList<Int>): List<Int> {
    if (buildings.isEmpty()) return emptyList()

    val stack = ArrayDeque<Int>(buildings.size)

    for (i in buildings.size - 1 downTo 0) {
        if (i != 0) {
            if (buildings[i - 1] < buildings[i]) stack.push(i)
        } else stack.push(i)
    }

    val result = ArrayList<Int>(stack.size)

    for (i in stack) {
        val valueToAdd = stack.pop()

        result.add(valueToAdd)
    }

    return result
}

fun isBalanced(s: String): Boolean {
    val hashMap = hashMapOf('(' to  ')', '[' to ']', '{' to '}')

    val stack = ArrayDeque<Char>(s.length)

    for (index in s.indices) {
        if (hashMap.containsKey(s[index])) stack.push(s[index])
        else {
            if (stack.isEmpty()) return false

            val openToken = stack.pop()

            if (hashMap[openToken] != s[index]) return false
        }

    }

    return stack.isEmpty()
}

fun main(args: Array<String>) {
    print(compareBuildings(arrayListOf(7, 4, 8, 2, 9)))
}
