package uz.gita.newmobilebanking.data.remote.response.profile

data class ProfileInfoResponse(
    val data: InfoProfile
)

data class InfoProfile(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String
)

/*
 "data": {
        "firstName": "Ruslan",
        "lastName": "Jumatov",
        "phone": "+998917705677",
        "password": "1234567"
    }
 */