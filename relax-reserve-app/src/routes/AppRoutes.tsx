import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from '../pages/Home';
import Activities from '../pages/Activities';
import Reservations from '../pages/Reservations';
import Profile from '../pages/Profile';
import CheckIn from '../pages/CheckIn';
import FocusSession from '../pages/FocusSession';
import PointsOfInterest from '../pages/PointsOfInterest';
import Quests from '../pages/Quests';
import Rewards from '../pages/Rewards';
import ScreenTime from '../pages/ScreenTime';
import SensoryContent from '../pages/SensoryContent';

const AppRoutes: React.FC = () => {
    return (
        <Router>
            <Switch>
                <Route path="/" exact component={Home} />
                <Route path="/activities" component={Activities} />
                <Route path="/reservations" component={Reservations} />
                <Route path="/profile" component={Profile} />
                <Route path="/check-in" component={CheckIn} />
                <Route path="/focus-session" component={FocusSession} />
                <Route path="/points-of-interest" component={PointsOfInterest} />
                <Route path="/quests" component={Quests} />
                <Route path="/rewards" component={Rewards} />
                <Route path="/screen-time" component={ScreenTime} />
                <Route path="/sensory-content" component={SensoryContent} />
            </Switch>
        </Router>
    );
};

export default AppRoutes;