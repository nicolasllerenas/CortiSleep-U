# Relax Reserve App

Welcome to the Relax Reserve App! This application is designed to help users reserve places for social activities and suggest relaxation activities. 

## Features

- **User Authentication**: Secure login and registration for users.
- **Profile Management**: Users can create and manage their profiles.
- **Activity Reservations**: Users can reserve spots for various social activities.
- **Check-Ins**: Users can check in to activities they attend.
- **Focus Sessions**: Manage and track focus sessions for productivity.
- **Points of Interest**: Discover and manage points of interest in your area.
- **Quests**: Engage in quests that encourage exploration and relaxation.
- **Rewards System**: Earn points and redeem rewards for participation.
- **Screen Time Tracking**: Monitor and manage screen time usage.
- **Sensory Content**: Access sensory content and recommendations for relaxation.

## Project Structure

```
relax-reserve-app
├── public
│   └── index.html
├── src
│   ├── index.tsx
│   ├── App.tsx
│   ├── routes
│   │   └── AppRoutes.tsx
│   ├── pages
│   │   ├── Home.tsx
│   │   ├── Activities.tsx
│   │   ├── Reservations.tsx
│   │   ├── Profile.tsx
│   │   ├── CheckIn.tsx
│   │   ├── FocusSession.tsx
│   │   ├── PointsOfInterest.tsx
│   │   ├── Quests.tsx
│   │   ├── Rewards.tsx
│   │   ├── ScreenTime.tsx
│   │   └── SensoryContent.tsx
│   ├── components
│   │   ├── Header.tsx
│   │   ├── Footer.tsx
│   │   ├── ActivityCard.tsx
│   │   ├── ReservationForm.tsx
│   │   └── MapView.tsx
│   ├── hooks
│   │   └── useAuth.ts
│   ├── context
│   │   └── AuthContext.tsx
│   ├── services
│   │   └── api
│   │       ├── auth.ts
│   │       ├── profiles.ts
│   │       ├── checkins.ts
│   │       ├── focusSessions.ts
│   │       ├── pointsOfInterest.ts
│   │       ├── quests.ts
│   │       ├── rewards.ts
│   │       ├── screenTime.ts
│   │       └── sensoryContent.ts
│   ├── store
│   │   └── index.ts
│   ├── types
│   │   └── index.ts
│   ├── styles
│   │   └── globals.css
│   └── utils
│       └── apiClient.ts
├── package.json
├── tsconfig.json
├── .gitignore
└── README.md
```

## Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/relax-reserve-app.git
   ```
2. Navigate to the project directory:
   ```
   cd relax-reserve-app
   ```
3. Install dependencies:
   ```
   npm install
   ```
4. Start the development server:
   ```
   npm start
   ```

## API Endpoints

The application interacts with the following API endpoints:

- **Authentication**: `/api/auth`
- **Profiles**: `/api/profiles`
- **Check-Ins**: `/api/checkins`
- **Focus Sessions**: `/api/focusSessions`
- **Points of Interest**: `/api/pointsOfInterest`
- **Quests**: `/api/quests`
- **Rewards**: `/api/rewards`
- **Screen Time**: `/api/screenTime`
- **Sensory Content**: `/api/sensoryContent`

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or features.

## License

This project is licensed under the MIT License. See the LICENSE file for details.