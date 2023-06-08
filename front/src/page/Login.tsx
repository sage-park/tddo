import classNames from "classnames";
import {useState} from "react";
import {loginApi} from "../api/authentication";
import {Alert} from "@mui/material";
import {AxiosError} from "axios";
import {saveToken} from "../util/AuthUtils";

const Login = () => {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [errorMessage, setErrorMessage] = useState("인증 실패했습니다.");
    const [isError, setIsError] = useState(false);

    const clickLoginButton = async () => {
        console.log(username);
        console.log(password);

        if (!username || !password) {
            setErrorMessage("아이디 또는 비밀번호를 입력해주세요.");
            setIsError(true);
            return;
        }

        try{
            let axiosResponse = await loginApi(username, password);

            console.log(axiosResponse);

            setIsError(false);

            if (axiosResponse.data.token) {
                saveToken(axiosResponse.data.token);
            }

        } catch (e) {
            console.log(e);

            if (
                e instanceof AxiosError
            ) {

                if (e.response?.status === 401) {
                    setErrorMessage("아이디 또는 비밀번호가 다릅니다.");
                    setIsError(true);
                    return;
                }

                if (e.response?.data?.code) {
                    switch (e.response.data.code) {
                        case "ERROR_002":
                            setErrorMessage("아이디 또는 비밀번호를 입력해주세요.");
                            setIsError(true);
                            break;
                    }
                }

            }

        }

    };

    return (
        <div className={classNames("w-screen", "h-screen", "bg-gray-200", 'flex', 'justify-center', 'items-center')}>
            <div className={classNames("w-[30rem]", "bg-white", 'rounded-2xl', 'drop-shadow', 'p-14')}>
                <div className={classNames('flex', 'justify-center')}>
                    LOGIN
                </div>
                <div className={classNames('pt-4')}>
                    <div className={classNames('h-28', 'flex', 'items-center', 'flex-col', 'justify-around')}>
                        <input type='text' placeholder='Username'
                               className={classNames('h-10', 'w-full', 'p-3', 'bg-gray-50')}
                               value={username}
                               onChange={(event) => setUsername(event.target.value)}
                        />
                        <input type='password' placeholder='Password'
                               className={classNames('h-10', 'w-full', 'p-3', 'bg-gray-50')}
                               value={password}
                               onChange={(event) => setPassword(event.target.value)}
                        />
                    </div>
                    <div style={
                        isError
                            ? {display: "block"}
                            : {display: "none"}
                    }>
                        <Alert severity="warning">{errorMessage}</Alert>
                    </div>
                    <div className={classNames('h-16', 'flex', 'items-center')}>
                        <button
                            className={classNames('w-full', 'h-10', 'bg-blue-500', 'text-white')}
                            onClick={clickLoginButton}
                        >
                            Login
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )

}

export default Login;
