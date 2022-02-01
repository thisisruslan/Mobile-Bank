package uz.gita.newmobilebanking.data.remote.request.auth

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String,
    val status: Int = 0
)

/*
{
    "firstName":"User1 firstname",
    "lastName":"User1 lastname",
    "phone":"+998000000000",
    "password":"123456789",
    "status":"0"
}
 */
