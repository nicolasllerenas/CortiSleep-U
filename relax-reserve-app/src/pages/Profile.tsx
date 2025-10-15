import React, { useEffect, useState } from 'react';
import { getUserProfile } from '../services/api/profiles';
import { useAuth } from '../hooks/useAuth';

const Profile: React.FC = () => {
    const { user } = useAuth();
    const [profile, setProfile] = useState<any>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchProfile = async () => {
            if (user) {
                try {
                    const userProfile = await getUserProfile(user.id);
                    setProfile(userProfile);
                } catch (error) {
                    console.error('Error fetching profile:', error);
                } finally {
                    setLoading(false);
                }
            }
        };

        fetchProfile();
    }, [user]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!profile) {
        return <div>No profile found.</div>;
    }

    return (
        <div>
            <h1>{profile.name}'s Profile</h1>
            <p>Email: {profile.email}</p>
            <p>Points: {profile.points}</p>
            {/* Additional profile information can be displayed here */}
        </div>
    );
};

export default Profile;