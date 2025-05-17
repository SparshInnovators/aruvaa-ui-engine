package com.myproject.testingframework.model.functions

import com.myproject.composeflow.Actions.Button_Actions.ActionType_SimpleDialog

fun AuthenticateUser(userName: String, password: String): Boolean {
    if (userName == "" && password == "") {
        return true
    }
    return false
}