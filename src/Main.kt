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
    val normalText = "$normal$colorWhite${""}" // Возвращаемся к обычной толщине
    return boldText + normalText // Для возможности встраивания в print. Вывод становится в 1 строчку в большинстве случаев
}


fun main(args: Array<String>) {
    println("Hello!")
    val handlers = Handlers(NewMap())
    handlers.onHelp()
    while (true) {
        print("\nYour command: ")
        val input = readLine()!!
        try {
            val spacePos = input.indexOf(" ")
            val command: String = (if (spacePos == -1) input else input.substring(0, spacePos)).toLowerCase()
            val data: String? = if (spacePos == -1) null else input.substring(spacePos + 1)

            // TODO: заменить на forEach по массиву, упрятав каждый хендлер в отдельный класс
            if (handlers.isAdd(command))
                handlers.onAdd(data)
            else if (handlers.isClear(command))
                handlers.onClear()
            else if (handlers.isDel(command))
                handlers.onDel(data)
            else if (handlers.isDelByVal(command))
                handlers.onDelByVal(data)
            else if (handlers.isFind(command))
                handlers.onFind(data)
            else if (handlers.isHelp(command))
                handlers.onHelp()
            else if (handlers.isList(command))
                handlers.onList()
            else if (handlers.isLoad(command))
                handlers.onLoad(data)
            else if (handlers.isRegex(command))
                handlers.onRegex(data)
            else if (handlers.isSet(command))
                handlers.onSet(data)
            else if (handlers.isShow(command))
                handlers.onShow(data)
            else if (handlers.isStop(command)) {
                handlers.onStop()
                break
            } else if (handlers.isWrite(command))
                handlers.onWrite(data)
            else if (handlers.isShowShort(input))
                handlers.onShowShort(input)
            else
                handlers.default(input)
        } catch (e: InterruptedException) {
            println()
        } catch (e: Exception) {
            println("Sorry, but an exception occupied (" + e.message + ")")
            e.printStackTrace()
            Thread.sleep(100)
        }
    }
}
