import apiClient from '../utils/apiClient';

export const fetchQuests = async () => {
    const response = await apiClient.get('/quests');
    return response.data;
};

export const createQuest = async (questData) => {
    const response = await apiClient.post('/quests', questData);
    return response.data;
};

export const updateQuest = async (questId, questData) => {
    const response = await apiClient.put(`/quests/${questId}`, questData);
    return response.data;
};

export const deleteQuest = async (questId) => {
    const response = await apiClient.delete(`/quests/${questId}`);
    return response.data;
};