package com.example.demo_rxjava_android.helper


import com.example.demo_rxjava_android.helper.model.ApiUser
import com.example.demo_rxjava_android.helper.model.User


import java.util.ArrayList

object Utils {

    val userList: List<User>
        get() {

            val userList = ArrayList<User>()

            val userOne = User()
            userOne.firstname = "Gaurav"
            userOne.lastname = "Tyagi"
            userList.add(userOne)

            val userTwo = User()
            userTwo.firstname = "Garv"
            userTwo.lastname = "Tyagi"
            userList.add(userTwo)

            val userThree = User()
            userThree.firstname = "Mr. Garv"
            userThree.lastname = "Kumar Tyagi"
            userList.add(userThree)

            return userList
        }

    val apiUserList: List<ApiUser>
        get() {

            val apiUserList = ArrayList<ApiUser>()

            val apiUserOne = ApiUser()
            apiUserOne.firstname = "Gaurav"
            apiUserOne.lastname = "Tyagi"
            apiUserList.add(apiUserOne)

            val apiUserTwo = ApiUser()
            apiUserTwo.firstname = "Garv"
            apiUserTwo.lastname = "Tyagi"
            apiUserList.add(apiUserTwo)

            val apiUserThree = ApiUser()
            apiUserThree.firstname = "Mr. Garv"
            apiUserThree.lastname = "Kumar Tyagi"
            apiUserList.add(apiUserThree)

            return apiUserList
        }


    val userListWhoLovesCricket: List<User>
        get() {

            val userList = ArrayList<User>()

            val userOne = User()
            userOne.id = 1
            userOne.firstname = "Gaurav"
            userOne.lastname = "Tyagi"
            userList.add(userOne)

            val userTwo = User()
            userTwo.id = 2
            userTwo.firstname = "Garv"
            userTwo.lastname = "Tyagi"
            userList.add(userTwo)

            return userList
        }


    val userListWhoLovesFootball: List<User>
        get() {

            val userList = ArrayList<User>()

            val userOne = User()
            userOne.id = 1
            userOne.firstname = "Gaurav"
            userOne.lastname = "Tyagi"
            userList.add(userOne)

            val userTwo = User()
            userTwo.id = 3
            userTwo.firstname = "Mr. Garv"
            userTwo.lastname = "Kumar Tyagi"
            userList.add(userTwo)

            return userList
        }

    fun convertApiUserListToUserList(apiUserList: List<ApiUser>): List<User> {

        val userList = ArrayList<User>()

        for (apiUser in apiUserList) {
            val user = User()
            user.firstname = apiUser.firstname +" : Transformation"
            user.lastname = apiUser.lastname +" : Transformation"
            userList.add(user)
        }

        return userList
    }

    fun convertApiUserListToApiUserList(apiUserList: List<ApiUser>): List<ApiUser> {
        return apiUserList
    }


    fun filterUserWhoLovesBoth(cricketFans: List<User>, footballFans: List<User>): List<User> {
        val userWhoLovesBoth = ArrayList<User>()
        for (cricketFan in cricketFans) {
            for (footballFan in footballFans) {
                if (cricketFan.id == footballFan.id) {
                    userWhoLovesBoth.add(cricketFan)
                }
            }
        }
        return userWhoLovesBoth
    }



}// This class in not publicly instantiable.
