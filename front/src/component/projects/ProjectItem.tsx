import AssignmentIcon from "@mui/icons-material/Assignment";
import React, {useState} from "react";
import MenuIcon from '@mui/icons-material/Menu';
import {Avatar, Menu, MenuItem} from "@mui/material";

type ProjectItemParam = { title: string };
const ProjectItem = ({title}:ProjectItemParam) => {

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
            <div className={'flex p-1 items-center text-gray-600 hover:bg-gray-50 rounded-lg cursor-pointer justify-between'}>
                <div className={'flex items-center gap-3'}>
                    <AssignmentIcon sx={{fontSize: '0.9rem'}}/>
                    {title}
                </div>
                <div
                    onClick={handleClick}
                    className={'hover:bg-gray-100'}
                >
                    <MenuIcon className={'p-1'}/>
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
                    <MenuItem onClick={handleClose}>Update</MenuItem>
                    <MenuItem onClick={handleClose}>Delete</MenuItem>
                </Menu>
            </div>
        </>
    )

}

export default ProjectItem;
