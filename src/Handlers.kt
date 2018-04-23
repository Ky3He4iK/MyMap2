class Handlers (mMap: NewMap){
    val myMap = mMap

    fun isAdd(input: String) : Boolean {
        return input.startsWith("add")
    }

    fun onAdd(input: String) {
        if (input.length > "add ".length) {
            val key = input.substring("add ".length)
            if (myMap.contains(key))
                println("This key already exists!")
            else {
                print("And now, value for this key: ")
                val value = readLine()!!
                myMap.set(key, value)
                println("Added successfully")
            }
        } else
            print("usage: `add <key>`")
    }

    fun isHelp(input: String) : Boolean {
        return input == "help"
    }

    fun onHelp(input: String?) {
        for (com in help) {
            printBold(com.first)
            println(com.second)
        }
    }

    fun isSet(input: String) : Boolean {
        return input.startsWith("set")
    }

    fun onSet(input: String) {
        if (input.length > "set ".length) {
            val key = input.substring("set ".length)
            if (myMap.contains(key)) {
                print("And now, value for this key: ")
                val value = readLine()!!
                myMap.set(key, value)
                println("set successfully")
            } else
                println("I haven't this key!")
        } else
            println("usage: `set <key>`")
    }

    fun isStop(input: String) : Boolean {
        return input == "stop" || input == "exit"
    }

    fun onStop(input: String) {
        myMap.write()
        println("Goodbye...")
    }

    fun isClear(input: String) : Boolean {
        return input == "clear"
    }

    fun onClear(input: String) {
        myMap.clear()
        println("Cleared!")
    }

    fun isList(input: String) : Boolean {
        return input == "list"
    }

    fun onList(input: String) {
        if (!myMap.isEmpty())
            for ((key, value) in myMap.myMap) {
                print("\"")
                printBold(key)
                println("\" : \"" + value + "\"")
            }
        else
            println("I haven't any data yet :(")
    }

    fun isDel(input: String) : Boolean {
        return input.startsWith("del") && !input.startsWith("del_v")
    }

    fun onDel(input: String) {
        if (input.length > "del ".length) {
            val key = input.substring("del ".length)
            if (myMap.contains(key)) {
                myMap.del(key)
                println("Deleted successfully")
            } else
                println("I haven't this key!")
        } else
            print("usage: `del <key>`")
    }

    fun isDelByVal(input: String) : Boolean {
        return input.startsWith("del_v")
    }

    fun onDelByVal(input: String) {
        if (input.length > "del_v ".length) {
            val value = input.substring("del_v ".length)
            if (myMap.containsValue(value)) {
                val delKeys = myMap.delValue(value)
                println("Deleted successfully with keys:")
                for (key in delKeys!!)
                    printBold(key + "\n")
            } else
                println("I haven't key with this value!")
        } else
            print("usage: `del_v <value>`")
    }

    fun isShow(input: String) : Boolean {
        return input.startsWith("show")
    }

    fun onShow(input: String) {
        if (input.length > "show ".length) {
            val key = input.substring("show ".length)
            if (myMap.contains(key)) {
                print("\"")
                printBold(key)
                println("\" - \"" + myMap.get(key) + "\"")
            } else
                println("I haven't  this key!")
        } else
            println("usage: `show <key>`")
    }

    fun isFind(input: String) : Boolean {
        return input.startsWith("find")
    }

    fun onFind(input: String) {
        if (input.length > "find ".length) {
            val part = input.substring("find ".length)
            val found = myMap.find(part)
            if (found.isEmpty())
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
                        print(myMap.get(foundWrite.first))
                    else {
                        print(myMap.get(foundWrite.first)!!.substring(0, foundWrite.second.first))
                        printBold(part)
                        print(myMap.get(foundWrite.first)!!.substring(foundWrite.second.first + part.length))
                    }
                    println("\"")
                }
            }
        } else
            println("usage: `find <part>`")
    }

    fun isShowShort(input: String) : Boolean {
        return input.contains(" = ") && !myMap.contains(input)
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
        else {
            println("I don't know this command or key")
            printBold("help")
            println(" for list of available details")
        }
    }
}