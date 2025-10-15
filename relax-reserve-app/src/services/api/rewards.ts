import apiClient from '../utils/apiClient';

export const fetchRewardsBalance = async (userId) => {
    const response = await apiClient.get(`/rewards/balance/${userId}`);
    return response.data;
};

export const redeemReward = async (userId, rewardId) => {
    const response = await apiClient.post(`/rewards/redeem`, { userId, rewardId });
    return response.data;
};

export const fetchAvailableRewards = async () => {
    const response = await apiClient.get('/rewards/available');
    return response.data;
};