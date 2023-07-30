package com.sage.tddo.projectservice.adapter.out.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "project")
class ProjectJpaEntity (id:String, name:String) {

    @Id
    var id: String? = id

    var name: String = name

}
