import React from 'react';

interface ActivityCardProps {
    title: string;
    description: string;
    imageUrl: string;
    onReserve: () => void;
}

const ActivityCard: React.FC<ActivityCardProps> = ({ title, description, imageUrl, onReserve }) => {
    return (
        <div className="activity-card">
            <img src={imageUrl} alt={title} className="activity-card-image" />
            <h3 className="activity-card-title">{title}</h3>
            <p className="activity-card-description">{description}</p>
            <button onClick={onReserve} className="activity-card-button">Reserve</button>
        </div>
    );
};

export default ActivityCard;