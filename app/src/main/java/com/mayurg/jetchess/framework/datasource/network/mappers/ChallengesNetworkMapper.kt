package com.mayurg.jetchess.framework.datasource.network.mappers

import com.mayurg.jetchess.business.domain.model.Challenge
import com.mayurg.jetchess.business.domain.util.EntityMapper
import com.mayurg.jetchess.framework.datasource.network.model.ChallengeDTO
import javax.inject.Inject

/**
 * Created On 22/11/2021
 * @author Mayur Gajra
 */
class ChallengesNetworkMapper @Inject constructor() : EntityMapper<ChallengeDTO, Challenge> {
    override fun mapFromEntity(entity: ChallengeDTO): Challenge {
        return Challenge(
            id = entity.id,
            fromId = entity.fromId,
            toId = entity.toId
        )
    }

    override fun mapToEntity(domainModel: Challenge): ChallengeDTO {
        return ChallengeDTO(
            id = domainModel.id,
            fromId = domainModel.fromId,
            toId = domainModel.toId
        )
    }
}