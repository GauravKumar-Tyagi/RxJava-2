package com.example.demo_rxjava_android.helper.model


class ApiUser {
    var id: Long = 0
    var firstname: String? = null
    var lastname: String? = null

    override fun toString(): String {
        return "ApiUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\''.toString() +
                ", lastname='" + lastname + '\''.toString() +
                '}'.toString()
    }
}
