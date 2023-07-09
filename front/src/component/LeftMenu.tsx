import {Link} from "react-router-dom";
import React from "react";
import ListIcon from '@mui/icons-material/List';

const LeftMenu = () => {

    return (
        <>
            <nav className={'pt-[80px] pr-3 pl-3 flex-shrink-0 w-48 h-80 box-border'}>
                <Link to={'/'} >
                    <div className={'p-2 flex items-center gap-1 text-gray-500 hover:bg-gray-100 rounded-xl'}>
                        <ListIcon sx={{fontSize: '1rem'}}/>
                        Projects
                    </div>
                </Link>
            </nav>
        </>
    )

}

export default LeftMenu;
