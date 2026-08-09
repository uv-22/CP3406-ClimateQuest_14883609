# ClimateQuest: Ethics and Privacy Design

## Purpose

ClimateQuest helps Years 7-9 learners interpret weather evidence, understand forecast uncertainty, and distinguish weather from climate. It is an educational tool, not emergency or safety advice.

## Learner group

The primary learners are students aged approximately 12-15. The app supports independent learning without collecting unnecessary personal information.

## Privacy-first location design

ClimateQuest uses manual city selection.

- The app does not request GPS, precise location, or background-location permission.
- A learner may choose, change, or remove a saved city at any time.
- The app stores only the selected city name locally.
- Fixed coordinates for supported cities are built into the app solely to request weather information.
- The app does not store raw device coordinates or use location for tracking.

## Live weather data

ClimateQuest retrieves optional live city conditions from the Open-Meteo Forecast API.

- Requests use the fixed coordinates for the city selected by the learner.
- The app does not add an account identifier, advertising identifier, or GPS location to the request.
- Open-Meteo data is shown as model-based weather information, not a guarantee.
- If the service or connection is unavailable, the app clearly shows an error state and keeps the learning mission usable.
- The mission's scenario forecast remains clearly labelled as learning evidence, separate from live conditions.

## Data minimisation

ClimateQuest does not require:

- an account or profile;
- advertising identifiers;
- analytics or behavioural tracking;
- social-media login; or
- background data collection.

The app stores only the data needed for learning:

- the selected city name; and
- local mission responses, correctness, and completion time.

This data stays on the current device in DataStore and Room. ClimateQuest explicitly excludes its private app data from Android cloud backup and device-to-device transfers. It is not sent to a ClimateQuest server.

## Learner control

The Settings screen provides controls to:

- choose, change, or remove the saved city;
- clear all local mission responses and Progress statistics; and
- understand which learning preferences are optional or planned.

Clearing learning data permanently removes the saved Room mission attempts from the device. It does not remove the separately saved city choice.

## Transparency

ClimateQuest clearly communicates:

- that live weather data comes from Open-Meteo;
- that manual city selection is used instead of GPS;
- that forecasts are uncertain estimates rather than guarantees;
- that learning responses stay on the device;
- that Android cloud backup and device-to-device transfers are disabled for learner data; and
- how learners can remove their saved city and local learning data.

## Fair and understandable learning feedback

Learning feedback explains the evidence behind an answer. ClimateQuest avoids shame, streak pressure, manipulative notifications, and misleading certainty.

## Accessibility and inclusion

ClimateQuest supports diverse learners through:

- readable text with scalable typography;
- clear icons paired with text labels;
- colour choices that do not communicate meaning by colour alone;
- sufficient contrast and touch-target sizes; and
- meaningful labels for screen readers.

## Ethical foundation

The design applies privacy by design, learner choice, accessibility, transparency, and trustworthy digital-system principles.