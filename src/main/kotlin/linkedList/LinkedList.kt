package linkedList

/*
* Important notes:
* => Values can be changed
* */

// Structure of linked list Node
class RandomNode<T>(var value: T) {
    var next: RandomNode<T>?
    var random: RandomNode<T>? = null

    init {
        next = random
    }
}

data class Node<T>(var value: T, var next: Node<T>? = null) {
    override fun toString(): String {
        return if (next != null) {
            "$value -> ${next.toString()}"
        } else {
            "$value"
        }
    }
}

class LinkedList<T> {
    internal var head: Node<T>? = null
    internal var tail: Node<T>? = null
    internal var size = 0

    fun isEmpty() = (size == 0)

    // Add the value in front of the list
    fun push(value: T): LinkedList<T> {
        val newHead = Node(value, next = head)

        if (head == null) {
            head = newHead
        }

        size++

        return this
    }

    // This function should add at the end of the list a new node with the value
    fun append(value: T) {
        if (isEmpty()) {
            push(value)
        } else {
            val previousTail = tail

            val newTail = Node(value, next = null)

            previousTail?.next = newTail

            tail = newTail
        }

        size++
    }

    fun add(value: T, index: Int) {
        // First we need to validate that the index passed is legal
        validateIndex(index)

        // The code here will be called in the index is valid

        // We want to insert at the beggining of the list
        if (index == 0) {
            push(value)

            return
        }

        // We want to insert at the end of the list
        if (index == size - 1) {
            append(value)

            return
        }

        // We want to insert in the middle of the linked linkedList
        /*
        * Steps:
        * 1) Traverse until we get the index - 1 position
        * 2) Keep a reference of Node at position index
        * 3) Node at position index - 1 should point to the new Node
        * 4) New node should point to the node from step 2)
        * */
        var currentIndex = 0

        // 2)
        var currentHead = head

        // 1)
        while (currentIndex < index - 1) {
            currentHead = currentHead!!.next

            currentIndex++
        }

        val newNode = Node(value, next = currentHead!!.next)

        currentHead.next = newNode

        size++

    }



    // Function that validates if a position of insertion is valid or not
    fun validateIndex(index: Int) {
        if (index > size || index < 0) throw IndexOutOfBoundsException("$index should be more than 0 and less than $size")
    }
}


