# LearnQuest

**LearnQuest** is a collaborative Android study aid, developed as a third-year university project. Built in Java and leveraging Supabase for backend operations, LearnQuest is designed to empower students by helping them set goals, track progress, and share resources — all from an intuitive mobile app.

## Project Overview

This project was a learning journey for our team. We dove deep into the world of Android development, mastering new skills in a short timeframe and growing from novice app developers to collaborative problem solvers. 

LearnQuest enables:
- **Flashcard Creation & Sharing:** Students can create and share customizable flashcards to prepare for tests and exams together.
- **Exam and Mark Management:** A group administrator manages the setup of tests/exams, while members submit and track their marks — enabling real-time feedback on academic goals.
- **Shared Filing System:** Effortlessly upload and access files relevant to your study group, promoting resource sharing and group success.

We used a modern relational database backend powered by [Supabase](https://supabase.com/), chosen because it bypassed university firewall restrictions and delivered easy CRUD and advanced RPC support.

## Key Features

- **Collaborative flashcards:** Create, tag, and share flashcards with others.
- **Real-time performance insights:** Visualize your academic trajectory, get feedback on what you need to score in upcoming assessments to reach your goals.
- **Group administration:** Admin users set up assessments, while all members can submit and track marks.
- **File sharing:** Drag, drop, and share resources with your study group.
- **Supabase-powered backend:** Fast, reliable, and scalable backend for data storage and retrieval. Includes custom Remote Procedure Calls for seamless many-to-many operations.
- **Modern Android architecture:** Gradle-based project management and experience gained in dependency management with external libraries.

## Technical Stack

- **Platform:** Android
- **Language:** Java
- **Database:** Supabase (relational core, custom RPCs for complex queries)
- **Build:** Gradle/Kotlin DSL
- **Other:** Custom proguard rules, Google Services integration

## Lessons Learned & Future Aspirations

Looking back, there are clear ways to take this app further with modern technologies and best practices:
- Embrace Kotlin and Jetpack Compose for a modern, reactive UI
- Multi-module project structure for robust separation of concerns
- Incorporate both REST and GraphQL APIs for richer data operations
- Add Supabase authentication for enhanced security and personalized experience
- Use Dagger Hilt for dependency injection
- Leverage Kotlin Flows for efficient, reactive data streams
- Taking advantage of Kotlin's functional programming style, and used sealed classes for UI-state control
- Adoped The Repository Pattern, where implementations are wrapped in interfaces, to ease testing and swapping out implementations.

These lessons reflect a rapid learning progression and a passion to grow as a developer — valuable traits for future challenges and opportunities.

## Project Structure

```
├── app/
│   ├── build.gradle.kts
│   ├── google-services.json
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/          # Main source code for LearnQuest app
│       ├── androidTest/   # Instrumented tests
│       └── test/          # Unit tests
├── build.gradle.kts
├── gradle.properties
├── gradlew{,.bat}
├── idea/
├── settings.gradle.kts
├── local.properties
```

## How to Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Maartens-Mathew/LearnQuest.git
   ```
2. **Requirements:**
   - Android Studio (latest, with Java 8+ support)
   - An Android device or emulator
   - [Supabase](https://supabase.com/) backend with project credentials (adjust configs as needed)
3. **Build & Deploy:**
   - Open the project in Android Studio.
   - Sync Gradle and resolve dependencies.
   - Run on an emulator or device.

## Acknowledgements

- Developed as a collaborative third-year Capstone project, I must acknowledge my partners, Narsi Nisha and Granville Daniels for their committed work.
- Special thanks to Supabase for simplifying database ops under university network constraints.
- Appreciation for the open-source ecosystem that made this learning possible.

## License

This project is open for review, demonstration, and further learning. For inquiries or details, please contact the project owner.

---

> Created by [Maartens-Mathew](https://github.com/Maartens-Mathew) and team, with passion for innovation and learning.
