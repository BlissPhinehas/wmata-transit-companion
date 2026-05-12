# 🚌 WMATA Transit Companion

A native Android app that shows live bus arrivals near you using the WMATA API, displays stops on an embedded Google Maps view, lets you save favorite stops, and sends a local notification when your bus is 5 minutes away.

## 📱 Features

- 🗺️ Live Google Maps view centered on Washington DC
- 🚌 Real-time WMATA bus stop markers near you
- ⏱️ Live bus arrival times per stop
- ❤️ Save and manage favorite stops
- 🔔 5-minute bus alert notifications
- 🏗️ Industry-standard MVVM architecture

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Repository Pattern |
| Async | Coroutines + StateFlow |
| Networking | Retrofit + OkHttp |
| Database | Room |
| Maps | Google Maps SDK + Maps Compose |
| Notifications | NotificationCompat |
| CI | GitHub Actions |

## 🚀 Getting Started

### Prerequisites
- Android Studio Panda or later
- Android SDK 27+
- WMATA API key (free at [developer.wmata.com](https://developer.wmata.com))
- Google Maps API key (free at [console.cloud.google.com](https://console.cloud.google.com))

### Setup

1. Clone the repo:
```bash
git clone https://github.com/BlissPhinehas/wmata-transit-companion.git
```

2. Create a `local.properties` file in the root and add your keys:
```
WMATA_API_KEY=your_wmata_key_here
MAPS_API_KEY=your_maps_key_here
```

3. Build and run:
```bash
./gradlew installDebug
```

## 📁 Project Structure

```
app/src/main/java/com/blissphinehas/wmatatransit/
├── data/
│   ├── api/          # Retrofit API service + models
│   ├── db/           # Room database + DAO
│   ├── repository/   # Single source of truth
│   └── service/      # Background alert service
├── di/               # ViewModelFactory
├── model/            # Data classes
├── ui/
│   ├── arrivals/     # Arrivals screen + ViewModel
│   ├── favorites/    # Favorites screen + ViewModel
│   └── map/          # Map screen + ViewModel
└── util/             # Constants + NotificationHelper
```

## 🔑 API Keys

This project uses two free APIs:
- **WMATA API** — bus stops and arrival predictions
- **Google Maps SDK** — map tiles and display

Keys are stored in `local.properties` which is gitignored for security.

## 📸 Screenshots
<img width="597" height="958" alt="image" src="https://github.com/user-attachments/assets/df9ebc5e-69c4-4e96-ac52-d02f549b9674" />
<img width="588" height="946" alt="image" src="https://github.com/user-attachments/assets/be3bb1cf-3772-4228-afcf-fc516b74404a" />


## 📄 License

MIT License — free to use and modify.
