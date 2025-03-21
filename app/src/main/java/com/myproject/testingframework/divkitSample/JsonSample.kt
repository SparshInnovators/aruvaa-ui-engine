package com.myproject.testingframework.divkitSample

val data = """
    {
  "template": {
    "name": "tutorialCard",
    "margins": {
      "bottom": 6
    },
    "orientation": "horizontal",
    "paddings": {
      "top": 10,
      "bottom": 0,
      "left": 40,
      "right": 40
    },
    "items": [
      {
        "type": "text",
        "font_size": 25,
        "font_weight": "bold",
        "margins": {
          "bottom": 16,
          "top":10
        },
        "#text": "title"
      },
      {
        "type": "text",
        "font_size": 20,
        "margins": {
          "bottom": 16
        },
        "#text": "body"
      },
      {
        "type": "container",
        "#items": "links"
      }
    ],
    "link": {
      "type": "text",
      "action": {
        "url": "link",
        "log_id": "log"
      },
      "font_size": 16,
      "margins": {
        "bottom": 2
      },
      "text_color": "#0000ff",
      "underline": "single",
      "text": "link_text"
    }
  },
  "card": {
    "state_id": 0,
    "items": [
        {
          "type": "image",
          "image_url": "https://yastatic.net/s3/home/divkit/logo.png",
          "margins": {
            "top": 10,
            "right": 60,
            "bottom": 10,
            "left": 60
          }
        },
        {
          "type": "tutorialCard",
          "title": "DivKit",
          "body": "What is DivKit and why did I get here?\n\nDivKit is a new Yandex open source framework that helps speed up mobile development.\n\niOS, Android, Web — update the interface of any applications directly from the server, without publishing updates.\n\nFor 5 years we have been using DivKit in the Yandex search app, Alice, Edadeal, Market, and now we are sharing it with you.\n\nThe source code is published on GitHub under the Apache 2.0 license.",
          "links": [
            {
              "type": "link",
              "link_text": "More about DivKit",
              "link": "https://divkit.tech/",
              "log": "landing"
            },
            {
              "type": "link",
              "link_text": "Documentation",
              "link": "https://divkit.tech/doc/",
              "log": "docs"
            },
            {
              "type": "link",
              "link_text": "News channel",
              "link": "https://t.me/divkit_news",
              "log": "tg_news"
            },
            {
              "type": "link",
              "link_text": "EN Community chat",
              "link": "https://t.me/divkit_community_en",
              "log": "tg_en_chat"
            },
            {
              "type": "link",
              "link_text": "RU Community chat",
              "link": "https://t.me/divkit_community_ru",
              "log": "tg_ru_chat"
            }
          ]
        }
      ]
  }
}
""".trimIndent()
