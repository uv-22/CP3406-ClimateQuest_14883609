# ClimateQuest

ClimateQuest is a privacy-first Android education app for Years 7-9 students. It develops learners' ability to interpret weather forecasts, communicate uncertainty, and distinguish short-term weather evidence from longer-term climate context.

## Target learners

Students aged approximately 12-15 (Years 7-9).

## Learning outcomes

Students will be able to:

- interpret temperature, rain probability, wind, and forecast timing;
- explain why a forecast is uncertain rather than guaranteed;
- justify a decision using relevant weather evidence; and
- distinguish weather forecasts from climate evidence.

## Core learning loop

1. A learner manually selects a city.
2. ClimateQuest displays a scenario forecast and optional live city conditions.
3. The learner answers a weather-evidence question.
4. The app gives feedback explaining uncertainty and evidence.
5. The learner's responses are saved locally and shown in Progress.

## Privacy and ethical design

- No GPS permission or precise device-location tracking
- No account, advertising, analytics, or background tracking
- Manual city selection only
- Live conditions are retrieved from Open-Meteo using fixed coordinates for the chosen city
- Selected city and learning progress are stored locally on the device
- Local learner data is excluded from Android cloud backup and device-to-device transfers
- Learners can remove their saved city and clear local learning data
- The app is educational and not emergency or safety advice

See [Ethics and Privacy Design](docs/ETHICS_AND_PRIVACY.md) and the [Gibbs self-reflection](docs/SELF_REFLECTION.md) for details.

## Technology

- Kotlin
- Jetpack Compose and Material 3
- Navigation Compose
- ViewModels and Hilt dependency injection
- DataStore for the selected city
- Room for local mission attempts and progress statistics
- Open-Meteo Forecast API for live city conditions
- Compose UI tests and Gradle verification

## Development status

- [x] Android project bootstrapped and tested on Pixel 9
- [x] ClimateQuest visual identity, colour system, and accessible typography
- [x] Four-tab Navigation Compose shell
- [x] Learner-focused Home and Settings screens
- [x] Manual city selection with DataStore persistence
- [x] Saved-city removal and local learning-data controls
- [x] Three playable learning missions with evidence-based feedback
- [x] Room persistence for mission attempts and Progress statistics
- [x] Live city weather evidence from Open-Meteo
- [x] Compose UI tests for Settings and Progress controls
- [x] Host-side unit test for the local mission catalogue
- [x] Gibbs Reflective Cycle self-reflection