package com.myproject.testingframework.mvvm_Arc.model.functions

fun replacedynamicData(contentId: String, index: Int): String {
    val titles = arrayOf(
        "Introduction to Kotlin",
        "Mastering Compose",
        "Advanced Coroutines",
        "Jetpack Navigation",
        "State Management",
        "Room Database Basics",
        "Android UI Design",
        "Networking with Retrofit",
        "Dependency Injection",
        "Unit Testing in Android"
    )

    val descriptions = arrayOf(
        "Learn the basics of Kotlin programming.",
        "Build modern UI with Jetpack Compose.",
        "Master async programming with coroutines.",
        "Implement seamless navigation in apps.",
        "Manage UI state effectively in Compose.",
        "Store and manage data with Room DB.",
        "Create beautiful UIs with Compose.",
        "Integrate APIs using Retrofit library.",
        "Implement DI using Hilt and Dagger.",
        "Write and run tests for Android apps."
    )

    val details = arrayOf(
        "Introduction to Kotlin: Learn the fundamentals of Kotlin programming language, including syntax, data types, control structures, functions, and object-oriented programming concepts. Ideal for beginners transitioning from Java or starting fresh with Kotlin.",
        "Mastering Compose: Explore Jetpack Compose, Google's modern UI toolkit for Android. Learn about Composable functions, state management, layouts, animations, and building dynamic user interfaces with ease.",
        "Advanced Coroutines: Deep dive into Kotlin Coroutines for efficient asynchronous programming. Understand Coroutine scopes, context switching, structured concurrency, and best practices for managing background tasks.",
        "Jetpack Navigation: Master the Jetpack Navigation component to simplify app navigation. Learn how to implement navigation graphs, pass data between destinations, and handle deep linking efficiently.",
        "State Management: Discover effective state management techniques in Android apps using tools like StateFlow, MutableState, and LiveData. Learn how to manage UI states and improve performance in Compose.",
        "Room Database Basics: Learn to implement Room, a powerful SQLite abstraction library in Android. Understand database creation, DAO (Data Access Object) patterns, migrations, and efficient data handling.",
        "Android UI Design: Dive into modern Android UI design principles. Learn about Material Design guidelines, responsive layouts, typography, and creating visually appealing interfaces.",
        "Networking with Retrofit: Understand how to integrate Retrofit, a powerful HTTP client, to manage API requests in Android. Learn about REST API consumption, error handling, and response parsing using Gson or Moshi.",
        "Dependency Injection: Master Dependency Injection with Dagger Hilt or Koin to improve modularization and scalability in Android applications. Learn best practices for injecting dependencies efficiently.",
        "Unit Testing in Android: Learn how to write and run unit tests in Android using JUnit and Mockito. Understand testing strategies, test-driven development (TDD), and how to ensure robust, bug-free code."
    )

    return if (contentId.startsWith("@")) {
        val key = contentId.removePrefix("@")

        when (key) {
            "titles" -> titles.getOrNull(index) ?: "Title not found"
            "descriptions" -> descriptions.getOrNull(index) ?: "Description not found"
            "details" -> details.getOrNull(index) ?: "Details not found"
            else -> key
        }
    } else {
        contentId
    }
}


