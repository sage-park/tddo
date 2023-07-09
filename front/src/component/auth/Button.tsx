import classNames from "classnames";

const Button = ({onClickButton, name}: {onClickButton: () => void, name:string}) => {

    return (
        <button
            className={classNames('w-full', 'h-10', 'bg-blue-500', 'text-white')}
            onClick={onClickButton}
        >
            {name}
        </button>
    )

}

export default Button;
