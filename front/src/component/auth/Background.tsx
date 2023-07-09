import classNames from "classnames";
import {ReactElement, ReactNode} from "react";

const Background = ({children}: {children: ReactNode}) => {

    return (
        <div className={classNames("w-screen", "h-screen", "bg-gray-200", 'flex', 'justify-center', 'items-center')}>
            {children}
        </div>

    )

}

export default Background;
