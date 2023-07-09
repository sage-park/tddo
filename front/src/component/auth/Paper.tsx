import classNames from "classnames";
import {ReactElement} from "react";

const Paper = ({children}: {children: ReactElement | any}) => {

    return (
        <div className={classNames("w-[30rem]", "bg-white", 'rounded-2xl', 'drop-shadow', 'p-14')}>
            {children}
        </div>

    )

}

export default Paper;
