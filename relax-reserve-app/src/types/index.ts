export interface User {
    id: string;
    username: string;
    email: string;
    profilePicture?: string;
    points: number;
}

export interface Activity {
    id: string;
    title: string;
    description: string;
    location: string;
    date: string;
    time: string;
    availableSlots: number;
}

export interface Reservation {
    id: string;
    userId: string;
    activityId: string;
    reservedAt: string;
}

export interface CheckIn {
    id: string;
    userId: string;
    activityId: string;
    checkInTime: string;
}

export interface FocusSession {
    id: string;
    userId: string;
    duration: number; // in minutes
    createdAt: string;
}

export interface PointOfInterest {
    id: string;
    name: string;
    description: string;
    location: string;
}

export interface Quest {
    id: string;
    title: string;
    description: string;
    rewards: string[];
}

export interface Reward {
    id: string;
    title: string;
    description: string;
    pointsRequired: number;
}

export interface ScreenTime {
    id: string;
    userId: string;
    duration: number; // in minutes
    date: string;
}

export interface SensoryContent {
    id: string;
    title: string;
    type: string; // e.g., "audio", "video", "image"
    contentUrl: string;
}