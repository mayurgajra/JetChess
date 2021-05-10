package com.mayurg.jetchess.framework.datasource.network.mappers

import com.mayurg.jetchess.business.domain.model.LoginUserModel
import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.business.domain.util.EntityMapper
import com.mayurg.jetchess.framework.datasource.network.model.LoginUserDTO
import com.mayurg.jetchess.framework.datasource.network.model.RegisterUserDTO
import javax.inject.Inject

class LoginNetworkMapper @Inject constructor() : EntityMapper<LoginUserDTO, LoginUserModel> {

    override fun mapFromEntity(entity: LoginUserDTO): RegisterUserModel {
        return RegisterUserModel(
            fullName = entity.fullName,
            mobile = entity.mobile,
            email = entity.email,
            password = entity.password
        )
    }

    override fun mapToEntity(domainModel: RegisterUserModel): LoginUserDTO {
        return LoginUserDTO(
            fullName = domainModel.fullName,
            mobile = domainModel.mobile,
            email = domainModel.email,
            password = domainModel.password
        )
    }
}