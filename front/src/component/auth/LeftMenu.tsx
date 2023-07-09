import classNames from "classnames";
import LeftMenuItem from "./LeftMenuItem";

const LeftMenu = () => {

    return (
        <div className={classNames('fixed', 'top-0', 'left-0', 'h-screen', 'bg-gray-50', 'flex', 'justify-start', 'items-center', 'flex-col')}>
            <LeftMenuItem title={'PROJECT'} to={'/projects'}/>
        </div>
    );

}

export default LeftMenu;
