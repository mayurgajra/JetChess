package com.mayurg.jetchess.framework.datasource.network.mappers

import com.mayurg.jetchess.business.domain.model.User
import com.mayurg.jetchess.business.domain.util.EntityMapper
import com.mayurg.jetchess.framework.datasource.network.model.UserDTO
import javax.inject.Inject

/**
 * Created On 17/11/2021
 * @author Mayur Gajra
 */
class UserNetworkMapper @Inject constructor() : EntityMapper<UserDTO, User> {
    override fun mapFromEntity(entity: UserDTO): User {
        return User(
            id = entity.id,
            fullName = entity.fullName,
            mobile = entity.mobile,
            email = entity.email
        )
    }

    override fun mapToEntity(domainModel: User): UserDTO {
       return UserDTO(
           id = domainModel.id,
           fullName = domainModel.fullName,
           mobile = domainModel.mobile,
           email = domainModel.email
       )
    }

}