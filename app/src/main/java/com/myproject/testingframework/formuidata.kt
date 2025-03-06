package com.myproject.testingframework

val formdata = """
    {
      "template": {
        "name": "formScreen",
        "margins": {
          "bottom": 6
        },
        "orientation": "vertical",
        "paddings": {
          "top": 10,
          "bottom": 0,
          "left": 40,
          "right": 40
        },
        "items": [
          {
            "type": "text-title",
            "font_size": 35,
            "font_weight": "bold",
            "margins": {
              "bottom": 8,
              "top": 10
            },
            "#text": "login-title"
          },
          {
            "type": "text-body",
            "font_size": 18,
            "font_weight": "normal",
            "margins": {
              "bottom": 20
            },
            "#text": "login-body"
          },
          {
            "type": "inputText",
            "keyboardType": "text",
            "font_size": 25,
            "font_weight": "normal",
            "margins": {
              "bottom": 16,
              "top": 10
            },
            "maxLines": 1,
            "suffixIcon": "Person",
            "#text": "usernameText"
          },
          {
            "type": "inputText",
            "keyboardType": "password",
            "font_size": 25,
            "font_weight": "normal",
            "margins": {
              "bottom": 16,
              "top": 10
            },
            "maxLines": 1,
            "suffixIcon": "Lock",
            "#text": "passwordText"
          },
          {
            "type": "button",
            "subtype":"elevated",
            "font_size": 25,
            "font_weight": "normal",
                "action": {
                  "type": "dialog",
                  "subtype":"alert",
                  "title": "Login Successfull!!",
                  "message": "You have successfully logged in."
                },
            "margins": {
              "bottom": 16,
              "top": 10,
              "left": 20,
              "right": 20
            },
            "backgroundColor": "#FF694ced",
            "#text": "buttonText1"
          }
        ]
      },
      "card": {
        "state_id": 0,
        "items": [
          {
            "type": "image",
            "image_url": "https://img.lovepik.com/photo/45009/7677.jpg_wh860.jpg",
            "margins": {
              "top": 10,
              "right": 60,
              "bottom": 10,
              "left": 60
            }
          },
          {
            "login-title": "Login",
            "login-body": "Please Sign in to continue.",
            "type": "formScreen",
            "usernameText": "Enter UserName",
            "passwordText": "Enter Password",
            "buttonText1": "Submit"
          }
        ]
      }
    }
""".trimIndent()


val secondpage = """
    {
      "template": {
        "name": "secondPage",
        "margins": {
          "bottom": 6
        },
        "orientation": "vertical",
        "paddings": {
          "top": 10,
          "bottom": 0,
          "left": 40,
          "right": 40
        },
        "items": [
          {
             "type":"container",
             "align":"center",
             "items": [
                {
                   "type": "text-title",
                   "font_size": 35,
                   "font_weight": "bold",
                   "margins": {
                      "bottom": 8,
                      "top": 10
                   },
                   "#text": "login-title"
                }
             ]
          }
        ]
      },
      "card": {
        "state_id": 0,
        "items": [
          {
            "type":"secondPage",
            "login-title":"Welcome {{userName}}"
          }
        ]
      }
    }
""".trimIndent()


//    "action": {
//    "type": "alert",
//    "title": "Login Successfull!!",
//    "message": "You have successfully logged in."
//},