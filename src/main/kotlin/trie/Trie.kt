package trie

class Trie {
    private val root = TrieNode('*')

    /*
    General:
    1) Iterate through every character of the string and add it into the adjacents list
         1a) Check if the character has already been added into the adjacents list
            1a1) If not then save TrieNode with the char into the index of the representation of the character
    * */
    fun insert(string: String) {
        var currentNode = root


        string.forEach {
            val position = it - 'a'

            if (currentNode.adjacents[position] == null) {
                currentNode.adjacents[position] = TrieNode(it)
            }

            currentNode = currentNode.adjacents[position]!!
        }

        currentNode.isComplete = true
    }

    /*
    General:
    1) Iterate through every character of the string
        1a) Grab the index representation of this character
        1b) Check if the index has already been populated
        1c) Return false otherwise
    2) Return true
    * */
    fun startsWith(string: String) = searchWithCompletion(string, isComplete = false)

    fun search(string: String) = searchWithCompletion(string, isComplete = true)

    /*
    *
    * */
    private fun searchWithCompletion(string: String, isComplete: Boolean): Boolean {
        var current = root

        string.forEach {
            val position = it - 'a'

            if (current.adjacents[position] == null) return false

            current = current.adjacents[position]!!
        }

        if (isComplete) {
            // We need to check if the word ends here
            if (!current.isComplete) return false
        }

        return true
    }
}

data class TrieNode(val character: Char, var isComplete: Boolean = false) {
    companion object {
        const val LATIN_CHARACTER_COUNT = 26
    }

    var adjacents = arrayOfNulls<TrieNode>(LATIN_CHARACTER_COUNT)
}
