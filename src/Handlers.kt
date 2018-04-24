class Handlers(mMap: NewMap) {
    private val myMap = mMap

    fun isAdd(command: String): Boolean {
        return command == "add"
    }

    fun isClear(command: String): Boolean {
        return command == "clear"
    }

    fun isDel(command: String): Boolean {
        return command == "del"
    }

    fun isDelByVal(command: String): Boolean {
        return command == "del_v"
    }

    fun isFind(command: String): Boolean {
        return command == "find"
    }

    fun isHelp(command: String): Boolean {
        return command == "help"
    }

    fun isList(command: String): Boolean {
        return command == "list"
    }

    fun isSet(command: String): Boolean {
        return command == "set"
    }

    fun isShow(command: String): Boolean {
        return command == "show"
    }

    fun isStop(command: String): Boolean {
        return command == "stop" || command == "exit"
    }

    fun isShowShort(input: String): Boolean {
        return input.contains(" = ") && !myMap.contains(input)
    }


    fun onAdd(data: String?) {
        if (data != null) {
            if (myMap.contains(data))
                println("This key already exists!")
            else {
                print("And now, value for this key: ")
                myMap.set(data, readLine()!!)
                println("Added successfully")
            }
        } else
            println("usage: `add <key>`")
    }

    fun onClear() {
        myMap.clear()
        println("Cleared!")
    }

    fun onDel(data: String?) {
        if (data != null) {
            if (myMap.contains(data)) {
                myMap.del(data)
                println("Deleted successfully")
            } else
                println("I haven't this key!")
        } else
            print("usage: `del <key>`")
    }

    fun onDelByVal(data: String?) {
        if (data != null) {
            if (myMap.containsValue(data)) {
                val delKeys = myMap.delValue(data)
                println("Deleted successfully with keys:")
                for (key in delKeys)
                    println("\"${printBold(key)}\"")
            } else
                println("I haven't key with this value!")
        } else
            print("usage: `del_v <value>`")
    }

    fun onFind(data: String?) {
        if (data != null) {
            val found = myMap.find(data)
            if (found.isEmpty())
                println("I can't found keys or values with $data")
            else
                for (foundWrite in found) {  // У меня огромное желание запихнуть тело цикла в один println(...)
                    // Правда мне надо показать уменя писать качественный код, а не код с минимальным количеством строчек
                    print("\"")
                    if (foundWrite.isKey)
                        print(foundWrite.key.substring(0, foundWrite.pos) + printBold(data) +
                                foundWrite.key.substring(foundWrite.pos + data.length))
                    else
                        print(foundWrite.key)
                    print("\" : \"")
                    if (foundWrite.isKey)
                        print(myMap.get(foundWrite.key))
                    else
                        print(myMap.get(foundWrite.key)!!.substring(0, foundWrite.pos) + printBold(data) +
                                myMap.get(foundWrite.key)!!.substring(foundWrite.pos + data.length))
                    println("\"")
                }
        } else
            println("usage: `find <part>`")
    }

    fun onHelp() {
        for (com in help)
            if (com.first.isEmpty())
                println()
            else
                println("${printBold(com.first)} - ${com.second}")
    }

    fun onList() {
        if (!myMap.isEmpty())
            for (key in myMap.getKeys())
                onShow(key)
        else
            println("I haven't any data yet :(")
    }

    fun onSet(data: String?) {
        if (data != null)
            if (myMap.contains(data)) {
                print("And now, value for this key: ")
                myMap.set(data, readLine()!!)
                println("Set successfully")
            } else
                println("I haven't this key!")
        else
            println("Usage: `set <key>`")
    }

    fun onShow(data: String?) {
        if (data != null)
            if (myMap.contains(data))
                println("\"${printBold(data)}\" - \"" + myMap.get(data) + "\"")
            else
                println("I haven't  this key!")
        else
            println("Usage: `show <key>`")
    }

    fun onStop() {
        myMap.write()
        println("Goodbye...")
    }

    fun onShowShort(input: String) {
        val sepPlace = input.indexOf(" = ")
        val key = input.substring(0, sepPlace)
        val value = input.substring(sepPlace + " = ".length)
        if (myMap.contains(key)) {
            myMap.set(key, value)
            println("set successfully")
        } else
            println("I haven't this key")
    }


    fun default(input: String) {
        if (myMap.contains(input))
            myMap.printPair(input)
        else
            println("I don't know this command or key\n${printBold("help")} for list of available details")
    }
}
