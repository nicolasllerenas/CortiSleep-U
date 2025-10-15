import React, { useEffect, useState } from 'react';
import { fetchSensoryContent } from '../services/api/sensoryContent';

const SensoryContent: React.FC = () => {
    const [content, setContent] = useState([]);

    useEffect(() => {
        const getContent = async () => {
            try {
                const data = await fetchSensoryContent();
                setContent(data);
            } catch (error) {
                console.error('Error fetching sensory content:', error);
            }
        };

        getContent();
    }, []);

    return (
        <div>
            <h1>Sensory Content</h1>
            <ul>
                {content.map((item) => (
                    <li key={item.id}>{item.title}</li>
                ))}
            </ul>
        </div>
    );
};

export default SensoryContent;