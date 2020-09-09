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

        rule["father_of"(v("X"), v("Y"))](
            "male"(v("X")),
            "parent_of"(v("X"), v("Y"))
        )

        rule["mother_of"(v("X"), v("Y"))](
            "female"(v("X")),
            "parent_of"(v("X"), v("Y"))
        )

        rule["grandfather_of"(v("X"), v("Y"))](
            "father_of"(v("X"), v("Z")),
            "parent_of"(v("Z"), v("Y"))
        )

        rule["grandmother_of"(v("X"), v("Y"))](
            "mother_of"(v("X"), v("Z")),
            "parent_of"(v("Z"), v("Y"))
        )

        rule["sister_of"(v("X"), v("Y"))](
            "female"(v("X")),
            "parent_of"(v("F"), v("X")),
            "parent_of"(v("F"), v("Y")),
            CommonLib["!="(v("X"), v("Y"))]
        )

        rule["brother_of"(v("X"), v("Y"))](
            "male"(v("X")),
            "parent_of"(v("F"), v("X")),
            "parent_of"(v("F"), v("Y")),
            CommonLib["!="(v("X"), v("Y"))]
        )

        rule["ancestor_of"(v("X"), v("Y"))](
            "parent_of"(v("X"), v("Y"))
        )

        rule["ancestor_of"(v("X"), v("Y"))](
            "parent_of"(v("X"), v("Z")),
            "ancestor_of"(v("Z"), v("Y"))
        )
    }

    println(knowledgeBase)
    println()

    for ((parent, child) in knowledgeBase["parent_of"(v("P"), v("C"))].atoms2<String, String>()) {
        println("$parent is parent of $child")
    }

    for ((brother, x) in knowledgeBase["brother_of"(v("b"), v("x"))].atoms2<String, String>()) {
        println("$brother is brother of $x")
    }

    for ((sister, x) in knowledgeBase["sister_of"(v("b"), v("x"))].atoms2<String, String>()) {
        println("$sister is sister of $x")
    }

}