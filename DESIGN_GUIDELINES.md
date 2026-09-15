# Rippin Android UI/UX Design Guidelines & Specifications

> **Handoff Document for UI Development**  
> This specification documents all visual patterns, design preferences, Material 3 conventions, color definitions, and architectural decisions established for the Rippin Android app. Any AI agent or developer extending the UI must adhere strictly to these guidelines to preserve visual consistency and design integrity.

---

## 1. Core Design Philosophy
- **Dark Mode Native**: The application is designed for high-contrast dark environments suited for motorcyclists and motorsport enthusiasts. Primary background is `#141212` with layered dark surface containers.
- **Material Design 3 (Expressive)**: Leverage Material 3 Expressive components (`ToggleButton`, `ButtonGroup`, `ShortNavigationBar`, `CardDefaults`, `LinearWavyProgressIndicator`).
- **iOS Feature Parity with Android Material Polish**: Match the visual hierarchy and layout of the iOS reference app while utilizing native Material Design 3 tokens and components instead of replicating non-native iOS controls.

---

## 2. Card Styling & Outline Rules (CRITICAL)

### The Golden Rule for Card Outlines:
- **NEUTRAL / DARK GRAY CARDS MUST HAVE NO OUTLINES**:
  - Do NOT apply `border = BorderStroke(...)` or `outlineVariant` strokes to standard dark gray, surface-colored, or neutral cards.
  - Neutral cards rely strictly on elevation and background container differentiation (`surfaceContainer`, `surfaceContainerHigh`).
- **ONLY THEMED / COLORED CARDS KEEP OUTLINES**:
  - Outlines are reserved exclusively as color accents for themed feature cards (e.g., Live Status green glow, Points Shop amber, Leaderboard red, MotoGP cyan, Segments checkered white border, Upcoming Meets green border).
  - Badge chips with distinctive statuses (Live red bar, Squad live green pill, P1/P2/P3 podiums) maintain their respective colored borders.

---

## 3. Button Groups & Navigation Elements

### Connected Material 3 Button Groups:
- **Wherever custom tab rows, filter chips, or segment controls are used, implement native Material 3 Connected Button Groups**:
  - Use `ButtonGroup` with `ToggleButton`.
  - Apply `ButtonGroupDefaults.connectedLeadingButtonShapes()`, `connectedMiddleButtonShapes()`, and `connectedTrailingButtonShapes()`.
  - Apply `ButtonGroupDefaults.ConnectedSpaceBetween`.
  - Role accessibility: `.semantics { role = Role.RadioButton }`.
- **Icons Are Mandatory**:
  - Every button inside a button group MUST include an appropriate icon paired with the text label (e.g., `Row { Icon(...); Text(...) }`).
- **Where Applied**:
  1. **Explore Screen Filters**: Horizontally scrollable row containing:
     - `Popular` (`Icons.Default.LocalFireDepartment`)
     - `All` (`Icons.Default.Explore`)
     - `Turf` (`BattleIcon`)
     - `Segments` (`Icons.Default.Route`)
     - `Local Roads` (`Icons.Default.AltRoute`)
     - `Action` (`Icons.Default.Bolt`)
     - `Meets` (`Icons.Default.Groups`)
     - `Tracks` (`Icons.Default.TwoWheeler`)
     - `Layers` (`Icons.Default.Layers`)
  2. **Profile Tabs**: Connected button group with `Rides` (`Icons.Default.Book`), `Garage` (`Icons.Default.Build`), `Badges` (`Icons.Default.EmojiEvents`), `Settings` (`Icons.Default.Settings`).
  3. **Squads Navigation**: `Feed` (`DynamicFeed`), `Stats` (`BarChart`), `Members` (`Groups`), `Poker` (`Spade`).
  4. **Squads Sub-tabs**: `Posts` (`Article`), `Rides` (`TwoWheeler`), `Photos 6` (`PhotoLibrary`).
  5. **Community Feed Filters**: `Status` (`ChatBubbleOutline`), `Rides` (`TwoWheeler`), `Events` (`CalendarMonth`).

### Top Bar Actions:
- **Clean Action Icons**: Top app bars must NOT use pill or chip backgrounds behind buttons (e.g., remove tinted chip surfaces around search, notifications, or add buttons). Use clean, unboxed icon actions (`IconButton` or tinted `Icon`).

---

## 4. Color Palette & Themed Cards System

Defined in [`app.rippin.android.home.HomeColors`](file:///run/media/sab/DataLinux/Projects/saboooor/Rippin/app/src/main/java/app/rippin/android/home/HomeColors.kt):

| Feature / Card | Container Color | Accent / Border Color | Border Specs |
|---|---|---|---|
| **Daily Ride Report** | `Color(0xFF0C130E)` | `Color(0xFF55D889)` (`RideReportAccent`) | `BorderStroke(1.dp, RideReportAccent.copy(alpha = pulseGlow))` |
| **Points Shop** | `Color(0xFF13110C)` | `Color(0xFFFFD21F)` (`ShopAccent`) | `BorderStroke(1.dp, ShopAccent.copy(alpha = 0.28f))` |
| **Leaderboard** | `Color(0xFF150D0F)` | `Color(0xFFFF6B70)` (`LeaderboardAccent`) | `BorderStroke(1.dp, LeaderboardAccent.copy(alpha = 0.30f))` |
| **Leaderboard Podiums** | `Color(0xFF1F1215)` | Gold (`#FFD54F`), Silver (`#CFD8DC`), Bronze (`#FFAB91`) | `BorderStroke(1.dp, borderColor)` |
| **Segments Stat Card** | `Color(0xFF111317)` | White Grid Alpha | `BorderStroke(1.dp, Color.White.copy(alpha = 0.12f))` + checkered Canvas |
| **Upcoming Meets** | `Color(0xFF0D1611)` | `Color(0xFF55D889)` | `BorderStroke(1.dp, RideReportAccent.copy(alpha = 0.25f))` |
| **Moto Events / MotoGP** | Deep Blue Gradient (`0xFF0B1928` -> `0xFF0A1520`) | Bright Cyan `Color(0xFF40C4FF)` | `BorderStroke(1.dp, Color(0xFF40C4FF).copy(alpha = 0.28f))` |
| **Live Riding Pill (Squads)**| `Color(0xFF2C1418)` | Red `Color(0xFFE53935)` | `BorderStroke(1.dp, Color(0xFFE53935).copy(alpha = 0.35f))` |
| **Squad LIVE Badge** | `Color(0xFF1B2E20)` | Green `Color(0xFF00E676)` | `BorderStroke(1.dp, Color(0xFF00E676).copy(alpha = 0.4f))` |

---

## 5. Map & Explore Screen Specifications

### Google Maps Dark Mode Integration:
- Uses Google Maps Android SDK via Jetpack Compose (`GoogleMap`).
- Fully styled with dark JSON styling (`DARK_MAP_STYLE` in [`ExploreScreen.kt`](file:///run/media/sab/DataLinux/Projects/saboooor/Rippin/app/src/main/java/app/rippin/android/explore/ExploreScreen.kt)).
- Includes a fallback dark vector canvas (`MapSurface.kt`) when no API key is provided, preventing visual breakages during offline or demo usage.

### Top Floating Map Controls:
- **Poker Button**: Floating pill placed on the **top-left** with spade/poker icon.
- **Turf Button**: Floating pill placed on the **top-right** (separate from Poker) using the custom crossed-swords [`BattleIcon`](file:///run/media/sab/DataLinux/Projects/saboooor/Rippin/app/src/main/java/app/rippin/android/explore/BattleIcon.kt).

### Floating Action Buttons:
- Center-Right alignment: `MyLocation` target button above the primary `REC` action button.
- `REC` button triggers full-screen recording mode.

---

## 6. Full-Screen Recording Experience

When the user taps `REC`, the Explore tab transitions into an immersive recording view:
- **Bottom Navigation Bar**: Automatically hides (`hideBottomBar = isRecordingActive && selected == Destination.Explore`).
- **Top Bar**: Replaced by a floating pill `▲ Recording` on the top-left with a red indicator.
- **Center Map**: Displays a pulsating animated live beacon marker ([`RecordingCenterMarker.kt`](file:///run/media/sab/DataLinux/Projects/saboooor/Rippin/app/src/main/java/app/rippin/android/explore/RecordingCenterMarker.kt)).
- **Right Floating Control Deck** ([`RecordingRightControls.kt`](file:///run/media/sab/DataLinux/Projects/saboooor/Rippin/app/src/main/java/app/rippin/android/explore/RecordingRightControls.kt)):
  - Vertical pill panel with circular action buttons:
    - Navigation / Recenter
    - Share
    - SOS / Emergency (`LocalHospital` in red)
    - Pause / Resume (toggle state)
    - Stop (yellow rounded-square button, halts recording)
    - Fullscreen toggle
    - Grid / Overlays
    - Poker quick-action
- **Bottom Deck** ([`RecordingBottomDeck.kt`](file:///run/media/sab/DataLinux/Projects/saboooor/Rippin/app/src/main/java/app/rippin/android/explore/RecordingBottomDeck.kt)):
  - Compact stat readouts: **Distance (mi)** and **Elapsed Time**.
  - Embeds the shared [`MediaPlayer`](file:///run/media/sab/DataLinux/Projects/saboooor/Rippin/app/src/main/java/app/rippin/android/components/MediaPlayer.kt) directly inside the deck.

---

## 7. Media Player Component Architecture

- **Reusable Component**: Single shared component in [`app/rippin/android/components/MediaPlayer.kt`](file:///run/media/sab/DataLinux/Projects/saboooor/Rippin/app/src/main/java/app/rippin/android/components/MediaPlayer.kt) (aliased to `MusicCard`).
- **Single Source of Truth**: Used in both:
  1. Explore Screen bottom sheet.
  2. Full-Screen Recording bottom deck.
  *(Never create screen-specific duplicates of the media player).*
- **Design & Layout**:
  - Matches Android System Media Notification Player styling.
  - Album art background with soft dark overlay and subtle scrim.
  - Device badge (e.g., `📱 This phone`).
  - Track Title (bold, single line, auto-ellipsize) + Artist subtitle.
  - Material 3 `LinearWavyProgressIndicator` for playback scrub position.
  - Media Controls Row: Previous, Play/Pause toggle (large circular button), Next, Favorite toggle, Shuffle toggle.
  - Connected to live system `MediaPlayback` / `MediaSessionManager` via `MediaAccessService`.

---

## 8. Screen Directory & Modular Architecture

All code is strictly divided into feature packages under `app/src/main/java/app/rippin/android/`:

```text
app/src/main/java/app/rippin/android/
├── MainActivity.kt                  # Activity entry point & permissions
├── RippinApp.kt                     # Root Compose scaffold & tab navigation
├── Theme.kt                         # Material 3 & Expressive theme definitions
├── Widgets.kt                       # Shared interactive modifiers & press effects
├── MediaPlayback.kt                 # System notification listener & playback state
│
├── navigation/
│   └── Destination.kt               # App tab destination enum
│
├── components/                      # Shared cross-screen UI components
│   ├── MediaPlayer.kt               # System-connected media notification player
│   ├── MusicCard.kt                 # Component alias
│   └── WhatIsHappeningComposer.kt   # Social status composer card
│
├── home/                            # Home dashboard & widgets
│   ├── HomeScreen.kt
│   ├── HomeColors.kt
│   ├── StatusCard.kt
│   ├── PointsShopCard.kt
│   ├── LeaderboardCard.kt
│   ├── LeaderboardPodiumRow.kt
│   ├── PodiumRiderItem.kt
│   ├── SegmentsStatCard.kt
│   ├── UpcomingMeetsStatCard.kt
│   ├── MotoEventsCard.kt
│   ├── RiderTipsCard.kt
│   ├── UpcomingRidesSection.kt
│   └── RouteMapPreview.kt
│
├── explore/                         # Map & live ride recording
│   ├── ExploreScreen.kt
│   ├── MapSurface.kt
│   ├── BattleIcon.kt
│   ├── RecordingCenterMarker.kt
│   ├── RecordingRightControls.kt
│   └── RecordingBottomDeck.kt
│
├── squads/                          # Squads & group ride tracking
│   └── SquadsScreen.kt
│
├── community/                       # Social feed & community highlights
│   ├── CommunityScreen.kt
│   └── WeekStatBox.kt
│
└── profile/                         # User profile, garage, badges & stats
    └── ProfileScreen.kt
```

---

## 9. Guidelines for Future AI Agents & Developers
1. **Never re-add borders to neutral cards**: If adding a new card, check if it is explicitly a highlighted/themed card (like MotoGP or Live status). If it's a regular content card, leave `border = null`.
2. **Keep button groups unified**: When creating a group of 2 or more mutually exclusive or filtered options, always use `ButtonGroup` with `ToggleButton` and include an icon with each label.
3. **Keep functions in their own files**: Do not add new top-level composable screens or large cards into `MainActivity.kt` or existing multi-hundred line files. Place new components in their respective feature directory (`home/`, `explore/`, `components/`, etc.).
4. **Preserve system media player integration**: Do not mock media player state if extending music features; continue using `MediaPlayback.nowPlaying` flow from `MediaPlayback.kt`.
5. **Always verify compilation**: Run `gradle assembleDebug` before concluding any UI task to ensure all inter-package imports and Compose compiler checks pass.

