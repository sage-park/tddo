import Background from "../component/auth/Background";
import Paper from "../component/auth/Paper";
import Title from "../component/auth/Title";
import {TextField} from "@mui/material";
import classNames from "classnames";
import Input from "../component/auth/Input";
import {useState} from "react";
import Button from "../component/auth/Button";
import {registerMemberApi} from "../api/authentication";
import {AxiosError} from "axios";
import ErrorMessage from "../component/auth/ErrorMessage";

const RegisterMember = () => {

    const [id, serId] = useState('');
    const [password, serPassword] = useState('');
    const [name, setName] = useState('');
    const [errorMessage, setErrorMessage] = useState("회원가입 실패했습니다.");
    const [isError, setIsError] = useState(false);


    const clickButton = async () => {
        console.log(id);
        console.log(password);
        console.log(name);

        try{
            let axiosResponse = await registerMemberApi(id, password, name);

            setIsError(false);

            console.log(axiosResponse);
        } catch (e) {
            if (e instanceof AxiosError) {

                if (e.response?.data?.code) {
                    switch (e.response.data.code) {
                        case "ERROR_001":
                            setErrorMessage("아이디가 이미 존재합니다.");
                            setIsError(true);
                            break;
                        default:
                            setErrorMessage("회원가입 실패했습니다.");
                            setIsError(true);
                            break;
                    }
                }

            } else {
                console.error(e);
            }

        }

    }

    return (
        <Background >
            <Paper>
                <Title title={"SIGN UP"}/>
                <div className={classNames('pt-4', 'space-y-2')}>
                    <div>
                        <Input value={id} placeholder={'Id'} setValue={serId} />
                    </div>
                    <div>
                        <Input value={password} placeholder={'Password'} setValue={serPassword}/>
                    </div>
                    <div>
                        <Input value={name} placeholder={'Name'} setValue={setName}/>
                    </div>
                    <ErrorMessage isError={isError} errorMessage={errorMessage}/>
                </div>
                <div className={classNames('mt-4')}>
                    <Button name={'SUBMIT'} onClickButton={clickButton}/>
                </div>
            </Paper>
        </Background>
    )

}

export default RegisterMember;
