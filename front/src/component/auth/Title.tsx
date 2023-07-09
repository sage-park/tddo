import classNames from "classnames";

const Title = ({title}:{title:string}) => {

    return (
        <div className={classNames('flex', 'justify-center')}>
            {title}
        </div>
    )

}

export default Title;
