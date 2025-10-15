import React from 'react';
import ReservationForm from '../components/ReservationForm';

const Reservations: React.FC = () => {
    return (
        <div>
            <h1>Reserve Your Spot</h1>
            <ReservationForm />
        </div>
    );
};

export default Reservations;