import axios from "axios";
import {getToken} from "./AuthUtils";
import {ENVIRONMENT} from "../properties";

export const axiosInstance = axios.create({
    baseURL: ENVIRONMENT.API_BASE_URL,
})

axiosInstance.interceptors.request.use( config => {

    const token = getToken();

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
})
