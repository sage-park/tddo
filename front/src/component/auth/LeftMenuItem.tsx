import classNames from "classnames";
import {Link} from "react-router-dom";

type LeftMenuItemParam = { title: string, to: string };
const LeftMenuItem = ({title, to}:LeftMenuItemParam) => {

    return (
        <Link
            className={classNames('w-20', 'h-20', 'flex', 'justify-center', 'items-center', 'cursor-pointer', 'border-b-2')}
            to={'/projects'}
        >
            {title}
        </Link>
    )

}

export default LeftMenuItem;
