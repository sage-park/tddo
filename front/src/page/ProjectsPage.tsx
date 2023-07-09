import React from "react";
import Header from "../component/Header";
import LeftMenu from "../component/LeftMenu";
import ProjectItem from "../component/projects/ProjectItem";
import AddIcon from '@mui/icons-material/Add';
import CreateProjectForm from "../component/projects/CreateProjectForm";

const ProjectsPage = () => {
    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };

    return (
        <>
            <div className={'flex w-full h-full min-h-[100vh]' }>
                <Header/>
                <LeftMenu/>
                <div className={'mt-[72px] mr-4 p-8 w-full bg-gray-50 rounded-lg'}>
                    <div className={'p-8 bg-white rounded-lg'}>
                        <h2 className={'text-lg font-bold pb-4  text-gray-600'}>
                            Projects
                        </h2>
                        <div className={'flex justify-end pr-4 pb-2 border-b border-gray-100'}>
                            <AddIcon className={'text-gray-500 hover:bg-gray-100 cursor-pointer'}
                                     onClick={handleClickOpen}/>
                        </div>
                        <div className={'flex flex-col p-3 gap-2'}>
                            <ProjectItem title={'project01'}/>
                            <ProjectItem title={'project02'}/>
                            <ProjectItem title={'project03'}/>
                        </div>
                    </div>
                </div>
            </div>
            <CreateProjectForm open={open} setOpen={setOpen} />
        </>
    );

}

export default ProjectsPage;
