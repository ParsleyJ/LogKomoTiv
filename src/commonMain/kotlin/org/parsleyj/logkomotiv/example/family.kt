package org.parsleyj.logkomotiv.example
import org.parsleyj.logkomotiv.*
import org.parsleyj.logkomotiv.nativeFacts.NativeFacts.COMMON_LIB
import org.parsleyj.logkomotiv.backtracking.get
import org.parsleyj.logkomotiv.nativeFacts.NativeFacts.CommonLib


fun familyExample(){
    val knowledgeBase = knowledgeBase {
        fact["male"(+"jack")]
        fact["male"(+"oliver")]
        fact["male"(+"ali")]
        fact["male"(+"james")]
        fact["male"(+"simon")]
        fact["male"(+"harry")]

        fact["female"(+"helen")]
        fact["female"(+"sophie")]
        fact["female"(+"jess")]
        fact["female"(+"lily")]

        fact["parent_of"(+"jack", +"jess")]
        fact["parent_of"(+"jack", +"lily")]
        fact["parent_of"(+"helen", +"jess")]
        fact["parent_of"(+"helen", +"lily")]
        fact["parent_of"(+"oliver", +"james")]
        fact["parent_of"(+"sophie", +"james")]
        fact["parent_of"(+"jess", +"simon")]
        fact["parent_of"(+"ali", +"simon")]
        fact["parent_of"(+"lily", +"harry")]
        fact["parent_of"(+"james", +"harry")]

        rule["father_of"(V("X"), V("Y"))](
            "male"(V("X")),
            "parent_of"(V("X"), V("Y"))
        )

        rule["mother_of"(V("X"), V("Y"))](
            "female"(V("X")),
            "parent_of"(V("X"), V("Y"))
        )

        rule["grandfather_of"(V("X"), V("Y"))](
            "father_of"(V("X"), V("Z")),
            "parent_of"(V("Z"), V("Y"))
        )

        rule["grandmother_of"(V("X"), V("Y"))](
            "mother_of"(V("X"), V("Z")),
            "parent_of"(V("Z"), V("Y"))
        )

        rule["sister_of"(V("X"), V("Y"))](
            "female"(V("X")),
            "parent_of"(V("F"), V("X")),
            "parent_of"(V("F"), V("Y")),
            CommonLib["!="(V("X"), V("Y"))]
        )

        rule["brother_of"(V("X"), V("Y"))](
            "male"(V("X")),
            "parent_of"(V("F"), V("X")),
            "parent_of"(V("F"), V("Y")),
            CommonLib["!="(V("X"), V("Y"))]
        )

        rule["ancestor_of"(V("X"), V("Y"))](
            "parent_of"(V("X"), V("Y"))
        )

        rule["ancestor_of"(V("X"), V("Y"))](
            "parent_of"(V("X"), V("Z")),
            "ancestor_of"(V("Z"), V("Y"))
        )
    }

    println(knowledgeBase)
    println()

    for ((parent, child) in knowledgeBase["parent_of"(V("P"), V("C"))].atoms2<String, String>()) {
        println("$parent is parent of $child")
    }

    for ((brother, x) in knowledgeBase["brother_of"(V("b"), V("x"))].atoms2<String, String>()) {
        println("$brother is brother of $x")
    }

    for ((sister, x) in knowledgeBase["sister_of"(V("b"), V("x"))].atoms2<String, String>()) {
        println("$sister is sister of $x")
    }

}