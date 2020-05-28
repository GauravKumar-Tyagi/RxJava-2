package com.example.demo_rxjava_android.helper.model

class UserDetail {
    var id: Long = 0
    var firstname: String? = null
    var lastname: String? = null

    override fun toString(): String {
        return "UserDetail{" +
                "id=" + id +
                ", firstname='" + firstname + '\''.toString() +
                ", lastname='" + lastname + '\''.toString() +
                '}'.toString()
    }
}