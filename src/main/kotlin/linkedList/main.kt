package linkedList

import com.sun.jmx.remote.internal.ArrayQueue

fun main(args: Array<String>) {

}

// Swap Linked List Nodes in Pairs

/*
* 'a' -> 'b' -> 'c' -> 'd' -> 'e' -> 'f' -> null
* 'b' -> 'a' -> 'd' -> 'c' -> 'f' -> 'e' -> null
* (f      s)    (f'     s')
*
* s   ->  f  ->  s'  ->   f'
*
* b   -> a   ->  d     c -> d -> e
*
*
*  b -> null
* */

fun <T> swapInPairsWithPointers(head: Node<T>?): Node<T>? {
    if (head?.next == null) return head

    val newHead = head.next

    // Make pointers
    var first: Node<T>? = head
    var second: Node<T>? = head.next

    // Iteration that covers edge cases
    while (true) {
        val secondSegmentFirst = second?.next

        second?.next = first

        if (secondSegmentFirst?.next == null) {
            first!!.next = secondSegmentFirst

            return newHead
        }

        first!!.next = secondSegmentFirst.next!!

        first = secondSegmentFirst
        second = secondSegmentFirst.next
    }
}


fun <T> swapInPairsOnlyValues(firstNode: Node<T>?): Node<T>? {
    var head: Node<T>? = firstNode

    while (head?.next != null) {
        val currentValue = head.value
        val nextValue = head.next!!.value

        head.next?.value = currentValue
        head.value = nextValue

        head = head.next!!.next
    }

    return firstNode
}

/*

Testing for cycles

1 -> 2 -> 3 -> 4
          ^    |
          |    |
           ----

Edge cases:
* 1 -> null  => false
* null       => false

The previous should return true for cycles
**/

fun <T> testingForCycles(node: Node<T>?): Boolean {
    if (node?.next == null) return false

    var fast = node
    var slow = node.next

    while (fast?.next?.next != null) {
        fast = fast.next?.next
        slow = slow!!.next

        if (fast == slow) return true
    }

    return false
}

fun minimalRemovalForAString(s: String): String {
    if (s.isEmpty()) return s

    val stack = ArrayQueue<Int>(s.length)

    val stringBuilder = StringBuilder(s)

    for (i in s.indices) {
        if (s[i] == ')') stack.removeLast()
        else stack.add(i)
    }

    if (stack.isEmpty()) return s

    stack.forEach{ index ->
        stringBuilder.deleteCharAt(index)
    }

    return stringBuilder.toString()
}

fun <T> copyListWithRandom(node: RandomNode<T>): RandomNode<T>? {
    var head: RandomNode<T>? = node
    val hashMap = hashMapOf<RandomNode<T>, RandomNode<T>>()

    while(head != null) {
        hashMap[head] = RandomNode(head.value)

        head = head.next
    }

    head = node

    while(head != null) {
        val copyHead = hashMap[head]!!
        val newNext = hashMap[head.next]
        val newRandom = hashMap[head.random]

        // Wiring
        copyHead.next = newNext
        copyHead.random = newRandom

        // Keep iteration going
        head = head.next
    }

    return hashMap[node]
}

fun <T> getCycleStartingNode(node: Node<T>?): Node<T>? {
    // 1) Determine where both pointers meet
    // 2) Determine the length of the cycle by moving one pointer and leaving the other there
    // 3) Place the pointers at the start of the linkedList
    // 4) Move one pointer until the length of the list
    // 5) Move one by one and see where they meet
    if (node?.next == null) return node

    var fast = node
    var slow = node.next

    var meetingNode: Node<T>? = null

    while (fast?.next?.next != null) {
        fast = fast.next?.next
        slow = slow!!.next

        if (fast == slow) {
            // This is where the pointers meet
            meetingNode = slow
        }
    }

    if (meetingNode == null) return meetingNode // There are no cycles

    var cycleLength = 1 // Take into account the current node

    /* We have a cycle! */
    // Figure out the length of the cycle
    while (fast != meetingNode) {
        fast = fast!!.next

        cycleLength++
    }

    // Place the pointers at the start of the linkedList
    fast = node
    slow = node

    // Move one pointer until the length of the cycle
    for (i in 1..cycleLength) {
        fast = fast!!.next
    }

    // Move one by one until they meet
    while (true) { // They will do meet
        if (fast == slow) {

            return fast
        }

        fast = fast!!.next
        slow = slow!!.next
    }
}
