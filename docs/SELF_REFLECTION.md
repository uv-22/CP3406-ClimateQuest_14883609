# ClimateQuest Self-Reflection: Gibbs' Reflective Cycle

## 1. Description

For this project, I developed ClimateQuest, a Kotlin Android app for Years 7-9 learners. Its purpose is to help learners interpret weather evidence, understand forecast uncertainty, and distinguish weather from climate.

I used Jetpack Compose and Material 3 for the interface, Navigation Compose for four-tab navigation, Hilt and ViewModels for architecture, DataStore for a manually selected city, Room for local learning progress, and Open-Meteo for live city weather conditions. The final app includes three short learning missions, feedback for correct and incorrect answers, local Progress statistics, city controls, and clear-data controls.

I developed the app through small, focused commits. I tested features through Android Studio, Gradle builds, unit tests, and connected Android UI tests on a Pixel 9 emulator.

## 2. Feelings

At the beginning, I felt confident about making Compose screens but less confident about Android architecture components such as Hilt, DataStore, Room, and ViewModels. The first large architecture commit was challenging because it introduced several new pieces that needed to work together.

I also felt concerned about privacy. Because the target users are young learners, I did not want the app to depend on GPS, accounts, tracking, or advertising. I wanted the app to be clear about what it saves and give learners meaningful control.

As the project progressed, I became more confident because each small commit could be built and tested before continuing. Seeing the mission responses persist in Progress after restarting the app was especially satisfying because it showed that Room was working as intended.

## 3. Evaluation

A positive part of the project was the structure of the app. Separating data, feature, navigation, and dependency-injection packages made the code easier to follow. Repositories gave the ViewModels a clear source of data, while Hilt reduced manual object creation.

The manual city-selection design worked well. It supports live weather conditions without requesting GPS permission. The learner can change or remove the city, and the app clearly explains that the selection is stored locally.

The Progress feature was also successful. It saves mission responses locally, counts evidence-based answers, shows recent activity, and lets the learner permanently clear the data. This made the privacy promise visible in the interface instead of only describing it in documentation.

Some difficulties occurred during development. Hilt ViewModels created inside Navigation destinations caused a runtime crash because the destination did not have the expected Hilt ViewModel factory. I solved this by creating the shared ViewModels at the app/activity level and passing their state into the navigation destinations. I also needed to cold boot the emulator before Gradle could detect it and run connected Android UI tests.

A limitation is that the app currently has a small mission catalogue. The three missions cover the main learning scope, but more scenarios, deeper questions, and richer learner feedback would make the app stronger.

## 4. Analysis

The most important design decision was treating privacy as a feature requirement rather than an extra task. Young learners should not need to provide an account or precise location before they can learn. ClimateQuest therefore uses manual city selection, does not request GPS permission, does not include analytics or advertising, and stores progress only on the device.

Using Open-Meteo introduced an ethical responsibility to be transparent. The app makes clear that live conditions are model-based data, that the selected city is used for the request, and that forecasts are uncertain rather than guaranteed. This is important because learners may otherwise treat a percentage chance of rain as a promise.

The Room database was appropriate because progress needs to remain available after the app restarts. However, persistence can create privacy risks if learners cannot control it. Adding a clear-learning-data confirmation dialog gave learners a practical way to remove saved responses and statistics.

Testing also improved the quality of the project. Compose UI tests covered Settings city controls, the clear-data callback, and Progress states. A host-side unit test verified the local mission catalogue. Connected Android tests were useful because they tested Compose behaviour on an emulator rather than only checking compilation.

## 5. Conclusion

I learned that a successful Android app needs more than attractive screens. Navigation, state, persistence, error handling, testing, and ethical decisions must work together.

ClimateQuest now demonstrates a clean Android architecture with Hilt, repositories, ViewModels, DataStore, Room, and an internet API. It also demonstrates that ethical design can be implemented through real interface controls: manual city choice, local-only progress, source transparency, uncertainty language, and clear-data actions.

If I repeated the project, I would plan the shared ViewModel scopes earlier and create more testable pure functions for weather parsing and progress calculations. This would reduce debugging time and make the code easier to extend.

## 6. Action Plan

For future development, I would:

1. Add more mission scenarios with different cities, forecast types, and climate evidence.
2. Add tests for weather loading, error states, Room repository behaviour, and mission completion.
3. Improve the Progress screen with learner-friendly trends rather than only totals.
4. Add a visible time label for when live weather conditions were retrieved.
5. Review accessibility with larger system font settings and screen-reader testing.
6. Continue documenting privacy decisions whenever a new data source or feature is added.