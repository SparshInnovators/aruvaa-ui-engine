package com.myproject.testingframework.itemlist

val jsonUi = """
    {
  "template": {
    "name": "listPage",
    "margins": {
      "bottom": 6
    },
    "orientation": "vertical",
    "paddings": {
      "top": 10,
      "bottom": 10,
      "left": 10,
      "right": 10
    },
    "items": [
      {
        "type": "layout",
        "orientation": "vertical",
        "itemCount" : 50,
        "items": [
          {
            "type": "horizontalContainer",
            "items": [
              {
                "type": "image",
                "image_url": "https://gratisography.com/wp-content/uploads/2024/11/gratisography-augmented-reality-800x525.jpg",
                "margins": {
                  "top": 5,
                  "bottom": 5
                }
              },
              {
                "type": "container",
                "items": [
                  {
                    "type": "text-title",
                    "font_size": 20,
                    "font_weight": "normal",
                    "margins": {
                      "bottom": 0,
                      "left":10,
                      "right":5
                    },
                    "#text": "item-title"
                  },
                  {
                    "type": "text-body",
                    "font_size": 18,
                    "font_weight": "normal",
                    "margins": {
                      "bottom": 0,
                      "left":10,
                      "right":5
                    },
                    "#text": "item-body"
                  }
                ]
              },
              {
                "type": "button",
                "subtype": "icon",
                "icon": "share",
                "action": {
                  "type": "toast",
                  "message": "Button clicked",
                  "duration": "short"
                },
                "margins": {
                  "bottom": 16,
                  "top": 10
                }
              }
            ]
          }
        ]
      }
    ]
  },
  "card": {
    "items": [
      {
        "type": "listPage",
        "item-title": "Title",
        "item-body": "Body"
      }
    ]
  }
}
""".trimIndent()