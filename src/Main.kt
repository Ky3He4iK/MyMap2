val help = listOf(
        "help" to " - show help message",
        "stop" to "- exit program",
        "clear" to " - erase all keys",
        "list"  to " - show all keys and values",
        "" to "", // Костыльный разделитель
        "add <key>" to " - add new <key>",
        "myMap.set <key>" to " - myMap.set <value> to <key>",
        "del <key>" to " - delete <key>",
        "del_v <value>" to " - delete key with <value> -",
        "show <key>" to " - show value for <key>",
        "find <part>" to " - show all keys and values that myMap.contains <part>",
        "" to "",
        "<key> = <value>" to " - see `myMap.set <key>`",
        "<key>" to " - see `show <key>`"
) //TODO: add find_regex

fun printBold(text: String) {
    val esc = "\u001B"
    val bold = esc + "[1"
    val normal = esc + "[0"
    val colorWhite = ";37m"
    print("$bold$colorWhite$text")
    print("$normal$colorWhite${""}")
}


fun main(args: Array<String>) {
    println("Hello!")
    val myMap = NewMap()
    val handlers = Handlers(myMap)
    handlers.onHelp(null)
    while (true) {
        try {
            print("\nYour command: ")
            val input = readLine()!!
            if (handlers.isAdd(input)) {
                handlers.onAdd(input)
            } else if (handlers.isHelp(input)) {
                handlers.onHelp(input)
            } else if (handlers.isSet(input)) {
                handlers.onSet(input)
            } else if (handlers.isStop(input)) {
                handlers.onStop(input)
                break
            } else if (handlers.isClear(input)) {
                handlers.onClear(input)
            } else if (handlers.isList(input)) {
                handlers.onList(input)
            } else if (handlers.isDel(input)) {
                handlers.onDel(input)
            } else if (handlers.isDelByVal(input)) {
                handlers.onDelByVal(input)
            } else if (handlers.isShow(input)) {
                handlers.onShow(input)
            } else if (handlers.isFind(input)) {
                handlers.onFind(input)
            } else if (handlers.isShowShort(input)) {
                handlers.onShowShort(input)
            } else {
                handlers.default(input)
            }
        } catch (e: InterruptedException) {
            println()
        } catch (e: Exception) {
            println("Sorry, but an exception occupied (" + e.message + ")")
            e.printStackTrace()
        }
    }
}
