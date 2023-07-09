import classNames from "classnames";

const Input = ({value, placeholder, setValue}:{value:string, placeholder:string, setValue:(value:string)=>void}) => {

    return (
        <input type='text' placeholder={placeholder}
               className={classNames('h-10', 'w-full', 'p-3', 'bg-gray-50')}
               value={value}
               onChange={(event) => setValue(event.target.value)}
        />
    )

}

export default Input;
