import {axiosInstance} from "../util/ApiUtils";


const AUTHENTICATION_SERVICE_URL_PATH = `/authentication-service`

interface LoginResponse {
    token: string
}
export const loginApi = async (username: string, password: string) => {

    return axiosInstance.post<LoginResponse>(
        `${AUTHENTICATION_SERVICE_URL_PATH}/auth/authenticate`,
        {
            username: username,
            password: password,
        },
        {
            headers: {
                "Content-Type": "application/json",
            },
        }
    );

}
