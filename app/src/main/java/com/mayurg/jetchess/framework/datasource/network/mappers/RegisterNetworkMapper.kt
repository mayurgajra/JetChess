package com.mayurg.jetchess.framework.datasource.network.mappers

import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.business.domain.util.EntityMapper
import com.mayurg.jetchess.framework.datasource.network.model.RegisterUserDTO
import javax.inject.Inject

class RegisterNetworkMapper @Inject constructor() : EntityMapper<RegisterUserDTO, RegisterUserModel> {

    override fun mapFromEntity(entity: RegisterUserDTO): RegisterUserModel {
        return RegisterUserModel(
            fullName = entity.fullName,
            mobile = entity.mobile,
            email = entity.email,
            password = entity.password
        )
    }

    override fun mapToEntity(domainModel: RegisterUserModel): RegisterUserDTO {
        return RegisterUserDTO(
            fullName = domainModel.fullName,
            mobile = domainModel.mobile,
            email = domainModel.email,
            password = domainModel.password
        )
    }
}