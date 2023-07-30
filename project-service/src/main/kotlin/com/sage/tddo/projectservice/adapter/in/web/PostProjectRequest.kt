package com.sage.tddo.projectservice.adapter.`in`.web

data class PostProjectRequest (val name:String) {
    init {
        require(name.isNotBlank()) {"Name is Blank"}
    }
}
