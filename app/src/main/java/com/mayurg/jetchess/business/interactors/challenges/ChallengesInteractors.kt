package com.mayurg.jetchess.business.interactors.challenges

import javax.inject.Inject

/**
 * Created On 22/11/2021
 * @author Mayur Gajra
 */
class ChallengesInteractors @Inject constructor(
    val challengesList: ChallengesList,
    val acceptRejectChallenge: AcceptRejectChallenge
) {

}