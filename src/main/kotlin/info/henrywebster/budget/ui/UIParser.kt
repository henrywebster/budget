package info.henrywebster.budget.ui

import info.henrywebster.budget.command.Command
import info.henrywebster.budget.core.KeywordManager

class UIParser() {

//    fun parse(s: InputStream) {
//        BufferedReader(InputStreamReader(s)).use {
//
//            var line = it.readLine()
//            while(line != null) {
//                println(line)
//                line = it.readLine()
//            }
//        }
//    }

    // TODO(kotlinify): use the when clause, stop repeating so many args

//    fun parse(s: String) : UIBundle {
//        val strings = s.split(" ")
//
//        if (strings[0].equals("add"))
//            return UIBundle(UIToken.ADD, strings.subList(1, strings.size).toList())
//        else if(strings[0].equals("remove"))
//            return UIBundle(UIToken.REMOVE, strings.subList(1, strings.size).toList())
//        else
//            throw IllegalArgumentException("Not proper keyword: " + s[0])
//    }

    fun parse(input: String): Command {

        // look up first work in hash table
        // if there, return command
        // else return an error command that will print out infomation

        val strings = ArrayList(input.split(" "))

        // TODO(fix): catch exception
        val builder = KeywordManager.get(strings[0])

        strings.remove(strings[0])

        for (str in strings) {
            builder.inputArgument(str)
        }

        return builder.build()
    }

}