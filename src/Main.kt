val help = listOf(
        "help" to " - show help message +",
        "stop" to "- exit program +",
        "clear" to " - erase all keys +",
        "list"  to " - show all keys and values +",
        "" to "", // Костыльный разделитель
        "add <key>" to " - add new <key> +",
        "set <key>" to " - set <value> to <key> +",
        "del <key>" to " - delete <key> +",
        "del_v <value>" to " - delete key with <value> +",
        "show <key>" to " - show value for <key> +",
        "find <part>" to " - show all keys and values that contains <part> +",
        "" to "",
        "<key> = <value>" to " - see `set <key>`",
        "<key>" to " - see `show <key>` +"
) //TODO: add find_regex

var myMap: MutableMap <String, String>? = null

fun load(filename: String = "data.json") {
    //TODO
}

fun write(filename: String = "data.json") {
    //TODO
}

fun set(key: String, value: String) {
    if (myMap == null)
        myMap = mutableMapOf()
    myMap!![key] = value
}

fun del(key: String) : Boolean {
    if (myMap != null && myMap!!.containsKey(key)) {
        myMap!!.remove(key)
        return true
    }
    return false
}

fun delValue(value: String) : ArrayList<String>? {
    if (myMap != null && myMap!!.containsValue(value)) {
        //TODO: do
        return null //TODO: return deleted key
    }
    return null
}

fun get(key: String) : String? {
    if (myMap != null)
        return myMap!!.getOrDefault(key, null)
    return null
}

fun clear() {
    if (myMap != null)
        myMap!!.clear()
}

fun contains(key: String) : Boolean {
    return myMap != null && myMap!!.containsKey(key)
}

fun containsValue(value: String) : Boolean {
    return myMap != null && myMap!!.containsValue(value)
}

fun find(part: String) : ArrayList<Pair<String, Pair<Int, Boolean>>>? { // Возвращает ключ, индекс данной части в ключе/значении и ключ ли это
    if (myMap == null)
        return null
    //TODO: сделать класс вместо пары строки и пары числа с bool
    val res = ArrayList<Pair<String, Pair<Int, Boolean>>>()
    for (key in myMap!!.keys) {
        if (key.contains(part))
            res.add(key to (key.indexOf(part) to true))
        else if (myMap!![key]!!.contains(part))
            res.add(key to (myMap!![key]!!.indexOf(part) to false))
    }
    return res
}


fun printPair(key: String) : Boolean {
    if (contains(key)) {
        print("\"")
        printBold(key)
        print("\" : \"" + get(key) + "\"")
        return true
    }
    return false
}

fun printBold(text: String) {
    val esc = "\u001B"
    val bold = esc + "[1"
    val normal = esc + "[0"
    val colorWhite = ";37m"
    print("$bold$colorWhite$text")
    print("$normal$colorWhite${""}")
}

fun printHelpMes() {
    for (com in help) {
        printBold(com.first)
        println(com.second)
    }
}

fun main(args: Array<String>) {
    println("Hello!")
    printHelpMes()
    load()
    while (true) {
        try {
            print("\nYour command: ")
            val input = readLine()!!
            if (input.startsWith("add")) {
                if (input.length > "add ".length) {
                    val key = input.substring("add ".length)
                    if (contains(key))
                        println("This key already exists!")
                    else {
                        print("And now, value for this key: ")
                        val value = readLine()!!
                        set(key, value)
                        println("Added successfully")
                    }
                } else
                    print("usage: `add <key>`")
            } else if (input == "help") {
                printHelpMes()
            } else if (input.startsWith("set")) {
                if (input.length > "set ".length) {
                    val key = input.substring("set ".length)
                    if (contains(key)) {
                        print("And now, value for this key: ")
                        val value = readLine()!!
                        set(key, value)
                        println("Set successfully")
                    } else
                        println("I haven't this key!")
                } else
                    println("usage: `set <key>`")
            } else if (input == "stop" || input == "exit") {
                write()
                println("Goodbye...")
                break
            } else if (input == "clear") {
                clear()
                println("Cleared!")
            } else if (input == "list") {
                if (!myMap!!.isEmpty())
                    for ((key, value) in myMap!!) {
                        print("\"")
                        printBold(key)
                        println("\" : \"" + value + "\"")
                    }
                else
                    println("I haven't any data yet :(")
            } else if (input.startsWith("del") && !input.startsWith("del_v")) {
                if (input.length > "del ".length) {

                    val key = input.substring("del ".length)
                    if (contains(key)) {
                        del(key)
                        println("Deleted successfully")
                    } else
                        println("I haven't this key!")
                } else
                    print("usage: `del <key>`")
            } else if (input.startsWith("del_v")) {
                println("Hot working yes")
                continue
                if (input.length > "del_v ".length) {
                    val value = input.substring("del_v ".length)
                    if (containsValue(value)) {
                        val delKeys = delValue(value)!!
                        println("Deleted successfully with keys:")
                        for (key in delKeys)
                            printBold(key + "\n")
                    } else
                        println("I haven't key with this value!")
                } else
                    print("usage: `del_v <value>`")
            } else if (input.startsWith("show")) {
                if (input.length > "show ".length) {
                    val key = input.substring("show ".length)
                    if (contains(key)) {
                        print("\"")
                        printBold(key)
                        println("\" - \"" + get(key) + "\"")
                    } else
                        println("I haven't  this key!")
                } else
                    println("usage: `show <key>`")
            } else if (input.startsWith("find")) {
                if (input.length > "find ".length) {
                    val part = input.substring("find ".length)
                    val found = find(part)
                    if (found == null || found.isEmpty())
                        println("I can't found keys or values with " + part)
                    else {
                        for (foundWrite in found) {
                            print("\"")
                            if (foundWrite.second.second) {
                                print(foundWrite.first.substring(0, foundWrite.second.first))
                                printBold(part)
                                print(foundWrite.first.substring(foundWrite.second.first + part.length))
                            } else
                                print(foundWrite.first)
                            print("\" : \"")
                            if (foundWrite.second.second)
                                print(get(foundWrite.first))
                            else {
                                print(get(foundWrite.first)!!.substring(0, foundWrite.second.first))
                                printBold(part)
                                print(get(foundWrite.first)!!.substring(foundWrite.second.first + part.length))
                            }
                            println("\"")
                        }
                    }
                } else
                    println("usage: `find <part>`")
            } else if (input.contains(" = ") && !contains(input)) {
                val sepPlace = input.indexOf(" = ")
                val key = input.substring(0, sepPlace)
                val value = input.substring(sepPlace + " = ".length)
                if (contains(key)) {
                    set(key, value)
                    println("Set successfully")
                } else
                    println("I haven't this key")
            } else {
                if (contains(input))
                    printPair(input)
                else {
                    println("I don't know this command or key")
                    printBold("help")
                    println(" for list of available details")
                }
            }
        } catch (e: InterruptedException) {
            //println("\nCanceled")
            println()
        } catch (e: Exception) {
            println("Sorry, but an exception occupied (" + e.message + ")")
        }
    }
}
