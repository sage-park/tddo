import classNames from "classnames";
import {useState} from "react";
import {loginApi} from "../api/authentication";
import {Alert} from "@mui/material";
import {AxiosError} from "axios";
import {saveToken} from "../util/AuthUtils";
import {Link} from "react-router-dom";
import Background from "../component/auth/Background";
import Paper from "../component/auth/Paper";
import Title from "../component/auth/Title";
import Button from "../component/auth/Button";
import ErrorMessage from "../component/auth/ErrorMessage";

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
        <Background>
            <Paper>
                <Title title={"LOGIN"}/>
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
                    <ErrorMessage isError={isError} errorMessage={errorMessage}/>
                    <div className={classNames('h-16', 'flex', 'items-center')}>
                        <Button onClickButton={clickLoginButton} name={'Login'}/>
                    </div>
                    <div className={classNames('text-right', 'text-gray-500', 'text-xs')}>
                        <Link to="/register-member">SIGN UP</Link>
                    </div>
                </div>
            </Paper>
        </Background>
    )

}

export default Login;
