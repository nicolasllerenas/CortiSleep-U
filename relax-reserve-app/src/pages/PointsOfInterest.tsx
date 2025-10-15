import React, { useEffect, useState } from 'react';
import { fetchPointsOfInterest } from '../services/api/pointsOfInterest';
import MapView from '../components/MapView';

const PointsOfInterest: React.FC = () => {
    const [pointsOfInterest, setPointsOfInterest] = useState([]);

    useEffect(() => {
        const loadPointsOfInterest = async () => {
            const data = await fetchPointsOfInterest();
            setPointsOfInterest(data);
        };

        loadPointsOfInterest();
    }, []);

    return (
        <div>
            <h1>Points of Interest</h1>
            <MapView pointsOfInterest={pointsOfInterest} />
            <ul>
                {pointsOfInterest.map((poi) => (
                    <li key={poi.id}>{poi.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default PointsOfInterest;