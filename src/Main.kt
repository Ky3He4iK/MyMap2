/**
 * Это - обертка над классом Map из стандартной библиотеки для CLI. Прошу сильно не кидать тамками.
 * Я тоже человек, могу ошибиться и буду блягодарен за найденные ошибки
 * Скорее всего, в коде мало комментариев. Код вроде как самокомментирующмйся, так как я проблем с понимаем кода не встретил)
 *
 * @ky3he4ik. 04/2018
 */

val help = listOf(
        "help" to "show help message",
        "stop" to "exit program",
        "clear" to "erase all keys",
        "list" to "show all keys and values",
        "load" to "load keys and values from file",
        "write" to "write keys and values to file",
        "" to "", // Костыльный разделитель
        "add <key>" to "add new <key>",
        "set <key>" to "set <value> to <key>",
        "del <key>" to "delete <key>",
        "del_v <value>" to "delete keys with value <value>",
        "show <key>" to "show value for <key>",
        "find <part>" to "show all keys and values that contains <part>",
        "regex <regex>" to "show all keys and values that match <regex>",
        "" to "",
        "<key> = <value>" to "see \"set <key>\"",
        "<key>" to "see \"show <key>\""
) // Список все хоступных комманд

fun toBoldString(text: String): String { // Специальная функци чтоб писать жирным
    val esc = "\u001B"
    val bold = "$esc[1"
    val normal = "$esc[0"
    val colorWhite = ";37m" // Различные escape-последовательности для форматирования вывода
    val boldText = "$bold$colorWhite$text"
    val normalText = "$normal$colorWhite" // Возвращаемся к обычной толщине
    return boldText + normalText // Для возможности встраивания в print. Вывод становится в 1 строчку в большинстве случаев
}

fun getAssent(assentMessage: String): Boolean {
    while (true) {
        print("$assentMessage (y/n): ")
        val input = readLine()!!
        if (input == "y" || input == "yes")
            return true
        else if (input == "n" || input == "no") {
            return false
        } else
            println("Please, enter \"yes\" or \"no\"")
    }
}

fun main(args: Array<String>) {
    println("Hello!")
    val myMap = NewMap()
    val handlers = listOf(HandlerAdd(), HandlerClear(), HandlerDel(), HandlerDelByVal(), HandlerFind(), HandlerHelp(), // 0 - 5
            HandlerList(), HandlerLoad(), HandlerRegex(), HandlerSet(), HandlerShow(), HandlerStop(), HandlerShowShort(), // 6 - 12
            HandlerWrite(), HandlerDefault()) // 13 - 14
    handlers[5].onMatch(null, myMap)
    while (true) {
        print("\nYour command: ")
        val input = readLine()!!
        try {
            val spacePos = input.indexOf(" ")
            val command: String = (if (spacePos == -1) input else input.substring(0, spacePos)).toLowerCase()
            val data: String? = if (spacePos == -1) null else input.substring(spacePos + 1)

            for (handler in handlers)
                if (handler.isMatch(command, myMap)) {
                    handler.onMatch(data, myMap)
                    break
                }

            if (handlers[11].isMatch(command, myMap))
                break
        } catch (e: InterruptedException) {
            println()
        } catch (e: Exception) {
            println("Sorry, but an exception occupied (" + e.message + ")")
            e.printStackTrace()
            Thread.sleep(100)
        }
    }
}
