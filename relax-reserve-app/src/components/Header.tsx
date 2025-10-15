import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

const Header: React.FC = () => {
    return (
        <header className="header">
            <h1 className="logo">Relax & Reserve</h1>
            <nav className="navigation">
                <ul>
                    <li><Link to="/">Home</Link></li>
                    <li><Link to="/activities">Activities</Link></li>
                    <li><Link to="/reservations">Reservations</Link></li>
                    <li><Link to="/profile">Profile</Link></li>
                    <li><Link to="/checkin">Check In</Link></li>
                    <li><Link to="/focus-session">Focus Session</Link></li>
                    <li><Link to="/points-of-interest">Points of Interest</Link></li>
                    <li><Link to="/quests">Quests</Link></li>
                    <li><Link to="/rewards">Rewards</Link></li>
                    <li><Link to="/screen-time">Screen Time</Link></li>
                    <li><Link to="/sensory-content">Sensory Content</Link></li>
                </ul>
            </nav>
        </header>
    );
};

export default Header;