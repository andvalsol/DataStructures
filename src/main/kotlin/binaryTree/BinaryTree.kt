package binaryTree

import stacksAndQueues.isBalanced
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.abs
import kotlin.math.pow


data class Node(var left: Node?, var data: Int, var right: Node?)


data class Result(var height: Int, var overallDiameter: Int)

fun nodeDiameter(root: Node?): Int {
    val result = recursiveNodeDiameter(root)

    return result.overallDiameter
}

private val inOrderRecursionArray = arrayListOf<Int>()


fun main(args: Array<String>) {
    // TODO
}

fun breathFirstSearch(root: Node?) {
    if (root == null) return

    val queue = ArrayDeque<Node>()

    queue.addLast(root)

    while (queue.isNotEmpty()) {
        val head = queue.removeFirst()

        println(head.data)

        val left = head.left

        val right = head.right

        if (left != null) queue.addLast(left)

        if (right != null) queue.addLast(right)
    }
}


data class NodeWithNext(var left: NodeWithNext?, val data: Int?, var right: NodeWithNext?, var next: NodeWithNext?)

fun nodeWithNext(root: NodeWithNext): NodeWithNext {
    val nodesArray = arrayListOf<NodeWithNext>()

    val queue = ArrayDeque<NodeWithNext>()

    val hashMap = hashMapOf<Int, Int>()

    var indexMap = 0

    queue.addLast(root)

    while (queue.isNotEmpty()) {
        var currentIndex = 0

        indexMap++

        val current = queue.first()

        nodesArray.add(current)

        val left = current.left
        val right = current.right

        if (left != null) {
            queue.addLast(left)
            currentIndex++
        }
        if (right != null) {
            queue.addLast(right)
            currentIndex++
        }

        hashMap[indexMap] = currentIndex
    }

    var index2 = 0
    var i = 0
    val indexOfArrayNodes = i + hashMap[i]!!

    while (index2 < nodesArray.size) {
        if (index2 < indexOfArrayNodes) {
            i++
        } else {
            nodesArray[index2].next = nodesArray[index2 + 1]
        }

        index2++
    }

    return root
}

fun connectNode(root: NodeWithNext) {
    var node = root

    val q: Queue<NodeWithNext> = LinkedList()

    var previousNode: NodeWithNext?

    q.add(node)

    while (q.isNotEmpty()) {
        var size: Int = q.size

        previousNode = null

        while (size > 0) {
            node = q.remove()

            if (previousNode != null) {
                previousNode.next = node
            }

            previousNode = node

            if (node.left != null) {
                q.add(node.left)
            }

            if (node.right != null) {
                q.add(node.right)
            }

            size--
        }
    }
}

data class IsBalance(var height: Int, var isBalanced: Boolean)

private fun isBalanceBinaryTree(root: Node?): Boolean {
    if (root == null) return false

    val result = isBalanceBinaryTreeRecursive(root)

    return result.isBalanced
}

fun serialize(root: Node?): String {
    if (root == null) return ""

    return serializeRec(root)
}

/**
General procedure:
1) Iterate over every node
1a) Use NLR and recursion
1b) Add a trailing comma
1c) Return X when node is null
2) Return the string representation of the tree

Sudo

rec(node): String {
if (node = null) return 'X,'

left = rec(node.left)
right = rec(node.right)

return data,leftright
}

 * */
private fun serializeRec(node: Node?): String {
    if (node == null) return "X,"

    // Go through every node pre-order traversal
    // Add a trailing , to every data
    val left = serialize(node.left)
    val right = serialize(node.right)

    // Return data with the left and right sub trees
    return "${node.data},${left}${right}"
}

/*
 Deserialize

 General
 1) Transform from string into a map of nodes
    1a) Split the array with the comma as a delimiter
    1b) Map function that transforms each array of chars into a Node
        1ba) Pass to int (chars) if x return null

 2) Iterate over each node and point left and right accordingly
    2a) Use recursion
    2b) Exit case would be null node
    2c) Parameter:
    2c) Point left and right to each node

 3) Return the root

    rec(queue): Node {
        node = queue.removeFirst()
        if (node is null) return queue

        node.left = rec(queue)
        node.right = rec(queue)

        return queue
    }
* */

fun deserialize(string: String): Node? {
    if (string.isEmpty()) return null

    val arrayOfNodes = string.split(',').map {
        if (it == "X") null else Node(left = null, data = it.toInt(), right = null)
    }
    val queue = ArrayDeque<Node?>().apply {
        addAll(arrayOfNodes)
    }

    return deserializeRec(queue)
}

fun deserializeRec(queue: ArrayDeque<Node?>): Node? {
    val node: Node? = queue.removeFirst()

    if (node == null) return null

    node.left = deserializeRec(queue)
    node.right = deserializeRec(queue)

    return node
}

fun sumToK(root: Node?): Int {
    if (root == null) return 0

    return sumToKRec(10, root, 10)
}

fun sumToKRec(left: Int, node: Node?, k: Int): Int {
    if (node == null) return 0

    var pathsToK = 0

    val leftSum = sumToKRec(left - node.data, node.left, k)
    val rightSum = sumToKRec(left - node.data, node.right, k)

    if (left == node.data) pathsToK++

    return pathsToK + leftSum + rightSum
}




fun isBalanceBinaryTreeRecursive(node: Node?): IsBalance {
    // Exit case
    if (node == null) return IsBalance(height = 0, isBalanced = true)

    val left = isBalanceBinaryTreeRecursive(node.left)

    // If early exit
    if (!left.isBalanced) return left

    val right = isBalanceBinaryTreeRecursive(node.right)

    if (!right.isBalanced) return right

    val max = Math.max(left.height, right.height)

    val diff = abs(left.height - right.height)

    return IsBalance(height = max + 1, isBalanced = (diff <= 1))
}


fun connectNode3(node: NodeWithNext?): NodeWithNext? {
    if (node == null) return null

    var leftNode: NodeWithNext = node

    while (leftNode.left != null) {
        var currentNode: NodeWithNext? = leftNode

        while (currentNode != null) {
            val leftChild = currentNode.left

            val rightChild = currentNode.right

            if (leftChild != null) {
                if (rightChild != null) {
                    leftChild.next = rightChild
                } else {
                    val nextLeft = currentNode.next?.left

                    val nextRight = currentNode.next?.right

                    if (nextLeft != null) {
                        leftChild.next = nextLeft
                    } else if (nextRight != null) {
                        leftChild.next = nextRight
                    }
                }
            } else if (rightChild != null) {
                val nextLeft = currentNode.next?.left

                val nextRight = currentNode.next?.right

                if (nextLeft != null) {
                    rightChild.next = nextLeft
                } else if (nextRight != null) {
                    rightChild.next = nextRight
                }
            }

            currentNode = currentNode.next!!
        }

        leftNode = leftNode.left!!
    }

    return node
}

fun connectNode2(root: NodeWithNext) {
    val queue = ArrayDeque<NodeWithNext>()

    queue.addLast(root)

    while (queue.isNotEmpty()) {
        var size = queue.size

        var prevNode: NodeWithNext? = null

        while (size > 0) {
            val node = queue.removeLast()

            if (node.left != null)
                queue.addLast(node.left!!)

            if (node.right != null)
                queue.addLast(node.right!!)

            if (prevNode != null) {
                prevNode.next = node
            }

            prevNode = node

            size--
        }
    }
}

fun inOrderTraversal(root: Node?): ArrayList<Int> {
    if (root == null) return arrayListOf()

    inOrderTraversalRecursive(root)

    return inOrderRecursionArray
}

fun inOrderTraversalRecursive(node: Node?) {
    if (node == null) return

    inOrderTraversalRecursive(node.left)

    inOrderRecursionArray.add(node.data)

    inOrderTraversalRecursive(node.right)
}


fun insert(node: Node?, valueToInsert: Int) {

}

fun delete2(root: Node?, valueToDelete: Int): Node? {
    if (root == null) return null

    return deleteRec2(root, valueToDelete)
}

fun deleteRec2(node: Node?, valueToDelete: Int): Node? {
    if (node == null) return null

    if (valueToDelete > node.data) {
        node.right = deleteRec2(node.right, valueToDelete)
    } else if (valueToDelete < node.data) {
        node.left = deleteRec2(node.left, valueToDelete)
    } else {
        // I have found the node
        if (node.left == null && node.right == null) return null
        else {
            if (node.left == null) {
                return node.right
            } else if (node.right == null) {
                return node.left
            } else {
                // 2 children
                // Find the next in order predecessor or the next in order successor
                // Need to find the next in order predecessor
                val predecessor = nextInOrderSuccessor(node)

                val oldLeft = node.left
                val oldRight = node.right

                predecessor.apply {
                    left = oldLeft
                    right = oldRight
                }

                delete2(node, 10)
            }
        }
    }

    return node
}

private fun nextInOrderPredecessor(node: Node): Node {
    var current = node

    // Go to the farthest right possible
    while (current.right != null) {
        current = current.right!!
    }

    return current
}

private fun nextInOrderSuccessor(node: Node): Node {
    var current = node

    // Go to the farthest left possible
    while (current.left != null) {
        current = current.left!!
    }

    return current
}

fun insertRec(node: Node?, valueToInsert: Int): Node? {
    if (node == null) return null

    if (node.data < valueToInsert) {
        node.left = insertRec(node, valueToInsert)
    } else if (node.data > valueToInsert) {
        node.right = insertRec(node, valueToInsert)
    }

    return node
}

fun insertIterative(root: Node, valueToInsert: Int): Node {
    var node: Node? = root

    while (node!!.left == null && node.right == null) {
        if (node.data < valueToInsert) {
            if (node.right != null) {
                if (node.right!!.data > valueToInsert) {
                    val oldRight = node.right
                    node.right = Node(left = null, data = valueToInsert, right = oldRight)

                    return root
                } else {
                    node = node.right
                }
            } else {
                node.right = Node(left = null, data = valueToInsert, right = null)

                return root
            }
        } else if (node.data > valueToInsert) {
            if (node.left != null) {
                if (node.left!!.data < valueToInsert) {
                    val oldLeft = node.left
                    node.left = Node(left = oldLeft, data = valueToInsert, right = null)

                    return root
                } else {
                    node = node.left
                }
            } else {
                node.right = Node(left = null, data = valueToInsert, right = null)

                return root
            }
        }
    }

    return root
}

fun deleteNodeRecursive(node: Node, valueToDelete: Int): Node? {
    if (node.left?.data == valueToDelete || node.right?.data == valueToDelete) return node


    val parent = deleteNodeRecursive(node, valueToDelete)

    if (parent?.left?.data == valueToDelete) {
        // Found the node to delete (on the left)
        // Is a leaf
        if (parent.left == null && parent.right == null) parent.left = null

        // It has one child
        if ((parent.left == null && parent.right != null)) {
            parent.left = parent.left?.right
        } else if (parent.left != null && parent.right == null) {
            parent.left = parent.left?.left
        }

        // It has 2 children
        val left = parent.left?.left

        if (left == null) parent.left = parent.left!!.right
        else {
            parent.left = left
            parent.left!!.left = parent.left!!.right
        }

    } else if (parent?.right?.data == valueToDelete) {
        // Found the node to delete (on the right)
        // Is a leaf
        if (parent.left == null && parent.right == null) parent.right = null

        // It has one child
        if ((parent.right == null && parent.left != null)) {
            parent.right = parent.right?.right
        } else if (parent.right != null && parent.left == null) {
            parent.right = parent.right?.left
        }

        // It has 2 children
        val left = parent.right?.left

        if (left == null) parent.right = parent.right!!.right
        else {
            parent.right = left
            parent.right!!.left = parent.right!!.right
        }
    }

    return null
}

fun inOrderTraversalWithoutRecursion2(root: Node?): ArrayList<Int> {
    val inorderTraversal = arrayListOf<Int>()
    val stack = Stack<Node>()

    var curr: Node? = root
    while (stack.isNotEmpty() || curr != null) {
        /*
       * Left: go as left as possible, the stack keeps the history of nodes that need
       * searching
       */
        while (curr != null) { // This is the base case

            stack.push(curr)

            curr = curr.left
        }

        // Node: curr is null now, investigate the node on the top of the stack
        curr = stack.pop()
        inorderTraversal.add(curr.data)

        // Right: now do this same routine on this node's right subtree
        curr = curr.right
    }

    return inorderTraversal
}

fun inOrderTraversalWithoutRecursion(root: Node?): ArrayList<Int> {
    val arrayList = arrayListOf<Int>()

    if (root == null) return arrayList

    // Go to the furthest left
    val stack = ArrayDeque<Node>()

    stack.addLast(root)

    while (stack.isNotEmpty()) {
        val node = stack.last()

        if (node.left != null && arrayList.size > 0 && arrayList[arrayList.size - 1] != node.left?.data) {
            stack.addLast(node)
        } else {
            val value = node.data

            arrayList.add(value)

            val right = node.right

            if (right != null) stack.addLast(right)

            stack.removeLast()
        }
    }

    return arrayList
}

fun recursiveNodeDiameter(node: Node?): Result {
    if (node == null) return Result(height = 0, overallDiameter = 0)

    val left = recursiveNodeDiameter(node.left)
    val right = recursiveNodeDiameter(node.right)

    val leftHeight = left.height
    val rightHeight = right.height

    val currentNodeHeight = Math.max(leftHeight, rightHeight)
    val currentNodeDiameter = leftHeight + rightHeight
    val overallDiameter = Math.max(left.overallDiameter, currentNodeDiameter)

    return Result(height = currentNodeHeight + 1, overallDiameter)
}

class BinaryTree {

    var root: Node? = null

    fun add(element: Int) {
        // We need to check if the root has been defined

        val newNode = Node(left = null, data = element, right = null)

        if (root == null) {
            // We need to set the root node
            root = newNode
        } else {
            // We need to set the right order of insertion for the node, this is why we use
            // an integer or a Comparable type object
            // Start from the root node
            var focusNode: Node = root!!

            while (true) {

                if (element < focusNode.data) {
                    val left = focusNode.left

                    if (left == null) {
                        focusNode.left = newNode

                        return
                    }

                    focusNode = focusNode.left!!
                } else {
                    val right = focusNode.right

                    if (right == null) {
                        focusNode.right = newNode

                        return
                    }

                    focusNode = focusNode.right!!
                }
            }
        }
    }

    // lnr
    fun inOrderTraverse(focusNode: Node?) {
        if (focusNode != null) {
            inOrderTraverse(focusNode.left)

            print(focusNode.data)

            inOrderTraverse(focusNode.right)
        }
    }

    // nlr
    fun preOrderTraverse(focusNode: Node?) {
        if (focusNode != null) {
            print(focusNode.data)

            preOrderTraverse(focusNode.left)

            preOrderTraverse(focusNode.right)
        }
    }

    // lrn
    fun postOrderTraverse(focusNode: Node?) {
        if (focusNode != null) {
            postOrderTraverse(focusNode.left)

            postOrderTraverse(focusNode.right)

            print(focusNode.data)
        }
    }

    fun binarySearch(element: Int): Node? {
        if (root == null) return null

        // We want to start with the root node
        var focusNode = root!!

        while (focusNode.data != element) {
            // We can use the same logic for adding new nodes into the binary BinaryTree

            focusNode = if (element < focusNode.data) {
                val left = focusNode.left ?: return null

                left
            } else {
                val right = focusNode.right ?: return null

                right
            }
        }

        // Element was found
        return focusNode
    }

    fun isSymmetric(root: Node) =
        recursiveIsSymmetric(nodeLeft = root.left, nodeRight = root.right)

    private fun recursiveIsSymmetric(nodeLeft: Node?, nodeRight: Node?): Boolean {
        if (nodeLeft == null && nodeRight == null) return true

        // This will also check for null value compared to a node that has a value
        val isSymmetric = nodeLeft == nodeRight

        if (!isSymmetric) return false

        return (recursiveIsSymmetric(nodeLeft!!.left, nodeRight!!.right) &&
                recursiveIsSymmetric(nodeLeft.right, nodeRight.left)
                )
    }

    fun rootToLeafSum() = rootToLeafSum(root!!, 0, 5)


    private fun rootToLeafSum(node: Node, currentSum: Int, total: Int): Boolean {
        if (currentSum > total) return false

        if (node.left == null && node.right == null && currentSum == total) return true

        if (node.left != null) {
            val leftBranchSum = rootToLeafSum(node.left!!, currentSum + node.data, total)

            if (leftBranchSum) return true
        }

        if (node.right != null) {
            val rightBranchSum = rootToLeafSum(node.right!!, currentSum + node.data, total)

            if (rightBranchSum) return true
        }

        return false
    }

    fun isValidBST() = recursiveBST(min = Int.MIN_VALUE, max = Int.MAX_VALUE, node = root!!)

    fun lowestCommonAncestor(root: Node?): Node? {
        if (root == null) return null

        return lowestCommonAncestorRecursive(root, 0, 0)
    }

    private fun lowestCommonAncestorRecursive(currentNode: Node?, intX: Int, intY: Int): Node? {
        if (currentNode == null) return null

        val nodeValue = currentNode.data

        if (intX < nodeValue && intY < nodeValue) lowestCommonAncestorRecursive(currentNode.left, intX, intY)
        else if (intX > nodeValue && intY > nodeValue) lowestCommonAncestorRecursive(currentNode.right, intX, intY)

        return currentNode
    }


    private fun recursiveBST(min: Int, max: Int, node: Node?): Boolean {
        if (node == null) return true

        if (node.data > max || node.data < min) return false

        return (recursiveBST(min, node.data, node.left) &&
                recursiveBST(node.data, max, node.right))
    }
}

class DeleteNode {

    var root: Node? = null

    // This method mainly calls deleteRec()
    fun deleteKey(key: Int) {
        root = deleteRec(root, key)
    }

    /* A recursive function to
      delete an existing key in BST
     */
    fun deleteRec(root: Node?, key: Int): Node? {
        /* Base Case: If the tree is empty */
        if (root == null) return root

        /* Otherwise, traverse down the tree */
        if (key < root.data)
            root.left = deleteRec(root.left, key)
        else if (key > root.data)
            root.right = deleteRec(root.right, key)
        else {
            // node with only one child or no child
            if (root.left == null) return root.right
            else if (root.right == null) return root.left

            // node with two children: Get the inorder
            // successor (smallest in the right subtree)
            root.data = minValue(root.right)

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.data)
        }
        return root
    }

    fun minValue(root: Node?): Int {
        var root = root!!
        var minv: Int = root.data

        while (root.left != null) {
            minv = root.left!!.data
            root = root.left!!
        }

        return minv
    }

    // This method mainly calls insertRec()
    fun insert(key: Int) {
        root = insertRec(root, key)
    }

    /* A recursive function to
       insert a new key in BST */
    fun insertRec(root: Node?, key: Int): Node {

        /* If the tree is empty,
          return a new node */
        var root = root
        if (root == null) {
            root = Node(left = null, data = key, right = null)
            return root
        }

        /* Otherwise, recur down the tree */if (key < root.data) root.left =
            insertRec(root.left, key) else if (key > root.data) root.right = insertRec(root.right, key)

        /* return the (unchanged) node pointer */return root
    }

    // This method mainly calls InorderRec()
    fun inorder() {
        inorderRec(root)
    }

    // A utility function to do inorder traversal of BST
    fun inorderRec(root: Node?) {
        if (root != null) {
            inorderRec(root.left)
            print(root.data.toString() + " ")
            inorderRec(root.right)
        }
    }
}
