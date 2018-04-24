class Handlers(mMap: NewMap) {
    private val myMap = mMap

    // Проверка на соответствие той или иной комманде
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

    fun isRegex(command: String): Boolean {
        return command == "find_regex"
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


    // Выполнение комманд
    fun onAdd(data: String?) {
        var mData = data
        if (mData == null) {
            print("Please, enter the key: ")
            mData = readLine()!!
        }
        if (myMap.contains(mData))
            println("This key already exists!")
        else {
            print("And now, value for this key: ")
            myMap.set(mData, readLine()!!)
            println("Added successfully")
        }
    }

    fun onClear() {
        myMap.clear()
        println("Cleared!")
    }

    fun onDel(data: String?) {
        var mData = data
        if (mData == null) {
            print("Please, enter the key: ")
            mData = readLine()!!
        }
        if (myMap.contains(mData)) {
            myMap.del(mData)
            println("Deleted successfully")
        } else
            println("I haven't this key!")
    }

    fun onDelByVal(data: String?) {
        var mData = data
        if (mData == null) {
            print("Please, enter the value: ")
            mData = readLine()!!
        }
        if (myMap.containsValue(mData)) {
            val delKeys = myMap.delValue(mData)
            println("Deleted successfully with keys:")
            for (key in delKeys)
                println("\"${printBold(key)}\"")
        } else
            println("I haven't key with this value!")
    }

    fun onFind(data: String?) {
        var mData = data
        if (mData == null) {
            print("Please, enter the part: ")
            mData = readLine()!!
        }
        val found = myMap.find(mData)
        if (found.isEmpty())
            println("I can't found keys or values with $mData")
        else
            for (foundWrite in found) {
                print("\"")
                if (foundWrite.isKey)
                    print(foundWrite.key.substring(0, foundWrite.pos) + printBold(mData) +
                            foundWrite.key.substring(foundWrite.pos + mData.length))
                else
                    print(foundWrite.key)
                print("\" : \"")
                if (foundWrite.isKey)
                    print(myMap.get(foundWrite.key))
                else
                    print(myMap.get(foundWrite.key)!!.substring(0, foundWrite.pos) + printBold(mData) +
                            myMap.get(foundWrite.key)!!.substring(foundWrite.pos + mData.length))
                println("\"")
            }
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

    fun onRegex(data: String?) {
        var mData = data
        if (mData == null) {
            print("Please, enter the regex: ")
            mData = readLine()!!
        }
        val found = myMap.findRegex(mData.toRegex())
        if (found.isEmpty())
            println("I can't found keys or values that matches $mData")
        else
            for (foundString in found)
                println("\"${printBold(foundString)}\" : \"${myMap.get(foundString)}\"")
    }

    fun onSet(data: String?) {
        var mData = data
        if (mData == null) {
            print("Please, enter the key: ")
            mData = readLine()!!
        }
        if (myMap.contains(mData)) {
            print("And now, value for this key: ")
            myMap.set(mData, readLine()!!)
            println("Set successfully")
        } else
            println("I haven't this key!")
    }

    fun onShow(data: String?) {
        var mData = data
        if (mData == null) {
            print("Please, enter the key: ")
            mData = readLine()!!
        }
        if (myMap.contains(mData))
            println("\"${printBold(mData)}\" - \"" + myMap.get(mData) + "\"")
        else
            println("I haven't  this key!")
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
