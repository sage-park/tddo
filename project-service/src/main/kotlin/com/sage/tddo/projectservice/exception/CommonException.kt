package com.sage.tddo.projectservice.exception

import com.sage.tddo.projectservice.adapter.`in`.web.ErrorCode

class CommonException: RuntimeException{

    constructor() : super()
    constructor(cause: Throwable) : super(cause)
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(errorCode: ErrorCode, message: String, cause: Throwable) : super(message, cause)


}
