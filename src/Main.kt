import kotlin.math.max

/**
 * Kotlin Collections Task 3 - Monkey Errors
 *
 *       OOOOO  OOO   OOO
 *          O  O   O O   O
 *         O   O   O O   O
 *       OOOOO  OOO   OOO
 * +-------------+-------------+
 * |    __v__    |     ___     |
 * |   ( o o )   |    /   |    |
 * |    (---)    |       _/    |
 * |    __|__    |      |      |
 * |   /|. .|\   |      o      |
 * +-------------+-------------+
 *
 * It seems that some of the zookeepers are not very
 * good at their job, and keep getting the cage numbers
 * muddled up. Can you add some error checking to the
 * system to help indicate when things go wrong?
 */


/**
 * Constant vales used to define some key values
 * which can then be used throughout the code...
 */
const val NUMCAGES = 8      // The total number of cages
const val EMPTY = "---"     // Represents an empty cage


/**
 * Program entry point
 */
fun main() {
    //-------------------------------------------------
    println("Setting up the cages...")

    val cages = setupCages()

    showMonkeyCages(cages)
    println()

    //-------------------------------------------------
    println("Placing monkeys into cages...")

    placeMonkeyInCage(cages, 1, "Kevin")
    placeMonkeyInCage(cages, 8, "Sally")
    placeMonkeyInCage(cages, 4, "Pam")
    placeMonkeyInCage(cages, 3, "Samson")
    placeMonkeyInCage(cages, 5, "Frank")
    placeMonkeyInCage(cages, 6, "Jim")

    println()

    showMonkeyCages(cages)
    println("Monkeys: ${monkeyCount(cages)}")
    println("Empty: ${emptyCount(cages)}")
    println()

    //-------------------------------------------------
    println("Trying some invalid placements...")

    placeMonkeyInCage(cages, 0, "Nigel")
    placeMonkeyInCage(cages, NUMCAGES + 1, "Nigel")

    println()

    //-------------------------------------------------
    println("Trying some more invalid placements...")

    placeMonkeyInCage(cages, 2, "")
    placeMonkeyInCage(cages, 2, "        ")

    println()

    //-------------------------------------------------
    println("Trying to clear invalid cages...")

    clearCage(cages, 0)
    clearCage(cages, NUMCAGES + 1)

    println()

    //-------------------------------------------------
    println("Trying to do some invalid swaps...")

    swapCages(cages, 0, 0)
    swapCages(cages, 0, 1)
    swapCages(cages, 1, 0)

    swapCages(cages, NUMCAGES + 1, NUMCAGES + 1)
    swapCages(cages, NUMCAGES + 1, NUMCAGES)
    swapCages(cages, NUMCAGES, NUMCAGES + 1)
}


fun setupCages(): MutableList<String> {
    val cageList = mutableListOf<String>()
    for (i in 1..NUMCAGES) cageList.add(EMPTY)
    return cageList
}


/**
 * Returns the number of monkeys found in the given cage list
 */
fun monkeyCount(cageList: List<String>): Int {
    return cageList.filter { cage -> cage != EMPTY }.size
}


/**
 * Returns the number of cages that are empty in the given cage list
 */
fun emptyCount(cageList: List<String>): Int {
    return cageList.filter { cage -> cage == EMPTY }.size
}


/**
 * Show all cages from the given list, formatted as a horizontal table:
 *
 * +--------+--------+--------+--------+----
 * | Cage 1 | Cage 2 | Cage 3 | Cage 4 | Etc.
 * +--------+--------+--------+--------+----
 * | Kevin  | ---    | Samson | Pam    | Etc.
 * +--------+--------+--------+--------+----
 *
 * Tip: the String.padEnd(N) function will help you here
 */
fun showMonkeyCages(cageList: List<String>) {
    var banner = "+"
    var namesAndPipes = "|"
    var cagesAndPipes = "|"

    val longestEntry = max(cageList.maxBy{it.length}.length, "Cage $NUMCAGES".length)

    cageList.forEachIndexed { index, cageOccupant ->
        //Prepare cage num | Cage 1 | Cage 2| ...
        val cageDescriptor = "Cage ${index+1}"
        cagesAndPipes += " ${cageDescriptor.padEnd(longestEntry, ' ')} |"

        //Prepare banner +--------+--------+
        banner += "-".repeat(longestEntry + 2)
        banner += "+"

        //Prepare names | monkey | monkey | monkey |
        namesAndPipes += " ${cageOccupant.padEnd(longestEntry, ' ')} |"
    }

    println(banner)
    println(cagesAndPipes)
    println(banner)
    println(namesAndPipes)
    println(banner)
}








/**
 * Put a given monkey into the specified cage number (1...MAX)
 */
fun placeMonkeyInCage(cageList: MutableList<String>, cageNum: Int, name: String) {
    println("+++ Putting $name into cage $cageNum")
    if(checkCageError(cageNum,"PLACING")) return
    if(name.isBlank()) {
        println("ERROR PLACING MONKEY: Name cannot be blank")
        return
    }

    cageList.removeAt(cageNum-1)
    cageList.add(cageNum-1,name)
}



/**
 * Make a given cage empty (if a monkey was in it, it's gone now!)
 */
fun clearCage(cageList: MutableList<String>, cageNum: Int) {
    println("--- Clearing cage $cageNum")
    if(checkCageError(cageNum,"CLEARING")) return

    cageList.removeAt(cageNum-1)
    cageList.add(cageNum-1,EMPTY)
}



/**
 * Swap the contents of two given cages.
 *
 * If one was full and the other empty, then the monkey just swaps
 * into the empty cage.
 */

fun swapCages(cageList: MutableList<String>, cageNum1: Int, cageNum2: Int) {
    println("<-> Swapping cages $cageNum1 and $cageNum2")

    if(checkCageError(cageNum1,"SWAPPING")) return
    if(checkCageError(cageNum2,"SWAPPING")) return


    cageList[cageNum1-1] = cageList[cageNum2-1].also { cageList[cageNum2-1] = cageList[cageNum1-1] }
}

fun checkCageError(cageNum: Int, errorVerb: String): Boolean {
    if(cageNum !in 1..NUMCAGES) {
        println("ERROR $errorVerb CAGE: Cage $cageNum is out of bounds")
        return true
    }
    return false
}




/**
 * ========================================================
 * ABOVE THIS COMMENT, PLACE A COPY OF ALL THE FUNCTIONS
 * YOU WORKED ON AND GOT WORKING IN TASK 2
 * ========================================================
 *
 * You will be modifying the following functions to add
 * error-checking:
 * - placeMonkeyInCage()
 * - clearCage()
 * - swapCages()
 *
 * If an error is detected, a suitable error message should
 * be shown such as:
 * - ERROR PLACING MONKEY: cage number 20 is out of range
 * - ERROR CLEARING CAGE: cage number 0 is out of range
 * - Etc.
 *
 * ========================================================
 */