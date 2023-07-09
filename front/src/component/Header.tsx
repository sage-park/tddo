import React, {useState} from "react";
import {Link} from "react-router-dom";
import {Avatar, Menu, MenuItem} from "@mui/material";

const Header = () => {

    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const handleClick = (event: React.MouseEvent<HTMLDivElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };


    return (
        <>
            <div className={'flex w-full flex-shrink-0 fixed top-0 left-auto right-0 bg-white z-50'}>
                <div className={'flex p-4 w-full h-[72px] items-center justify-between box-border'}>
                    <div>
                        <Link to={"/"} className={'text-xl font-bold text-gray-700'}>TDDO</Link>
                    </div>
                    <div
                        onClick={handleClick}
                    >
                        <Avatar
                            className={'cursor-pointer w-4'}
                            sx={{width: '30px', height: '30px', fontSize: '1rem'}}
                        >
                            H
                        </Avatar>
                    </div>
                    <Menu
                        id="basic-menu"
                        anchorEl={anchorEl}
                        open={open}
                        onClose={handleClose}
                        MenuListProps={{
                            'aria-labelledby': 'basic-button',
                        }}
                    >
                        <MenuItem onClick={handleClose}>Profile</MenuItem>
                        <MenuItem onClick={handleClose}>My account</MenuItem>
                        <MenuItem onClick={handleClose}>Logout</MenuItem>
                    </Menu>
                </div>
            </div>
        </>
    )

}

export default Header;
