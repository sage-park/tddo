import {Alert} from "@mui/material";

const ErrorMessage = ({isError, errorMessage}:{isError:boolean, errorMessage:string}) => {
    return (
        <div style={
            isError
                ? {display: "block"}
                : {display: "none"}
        }>
            <Alert severity="warning">{errorMessage}</Alert>
        </div>
    )


}

export default ErrorMessage;
