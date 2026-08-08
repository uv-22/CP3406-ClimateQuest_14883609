# ClimateQuest

ClimateQuest is a privacy-first Android education app for Years 7–9 students. It develops learners’ ability to interpret weather forecasts, communicate uncertainty, and distinguish short-term weather evidence from longer-term climate context.

## Target learners

Students aged approximately 12–15 (Years 7–9).

## Learning outcomes

Students will be able to:

- interpret temperature, rain probability, wind, and forecast timing;
- explain why a forecast is uncertain rather than guaranteed;
- justify a decision using relevant weather evidence; and
- distinguish weather forecasts from climate evidence.

## Core learning loop

1. A learner manually selects a city.
2. ClimateQuest presents clearly labelled weather evidence.
3. The learner answers a scenario-based question.
4. The app gives evidence-based feedback.
5. Local progress helps guide the next learning activity.

## Ethical design commitments

- Manual city selection; no GPS permission by default
- No account, advertising, analytics, or background tracking
- Clear data source, forecast time, and uncertainty language
- Accessible text, icons, contrast, and touch targets
- Local learner data will be controllable and erasable
- The app is educational and not emergency or safety advice

## Planned technology

Kotlin, Jetpack Compose, Material 3, Navigation, ViewModels, Hilt, Open-Meteo, Room, DataStore, and automated tests.

## Development status

- [x] Android project bootstrapped and tested on Pixel 9
- [x] ClimateQuest project identity established
- [ ] Visual design system and landing screen
- [ ] Learning mission, weather API, persistence, statistics, and tests