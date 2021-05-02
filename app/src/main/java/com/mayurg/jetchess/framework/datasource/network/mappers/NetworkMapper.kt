package com.mayurg.jetchess.framework.datasource.network.mappers

import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.business.domain.util.EntityMapper
import com.mayurg.jetchess.framework.datasource.network.model.RegisterUserDTO
import javax.inject.Inject

class NetworkMapper @Inject constructor(): EntityMapper<RegisterUserDTO,RegisterUserModel> {

    override fun mapFromEntity(entity: RegisterUserDTO): RegisterUserModel {
        return RegisterUserModel(
            email = entity.email,
            password = entity.password
        )
    }

    override fun mapToEntity(domainModel: RegisterUserModel): RegisterUserDTO {
        return RegisterUserDTO(
           email = domainModel.email,
           password = domainModel.password
        )
    }
}