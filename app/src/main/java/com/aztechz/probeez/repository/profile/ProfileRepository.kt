package com.aztechz.probeez.repository.profile

import android.content.Context
import android.util.Log
import com.aztechz.probeez.model.profile.PersonalDetails
import com.aztechz.probeez.model.profile.ProfileResponseModel
import com.aztechz.probeez.model.profile.ProfileUpdateRequest
import com.aztechz.probeez.model.profile.UpdateProfileResponseModel
import com.aztechz.probeez.retrofit.RetrofitClient
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.utils.DataState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.ObjectOutputStream


class ProfileRepository(private val context: Context, private val retrofitClient: RetrofitClient) {

    suspend fun getProfileDetails(): Flow<DataState<ProfileResponseModel>> = flow {

        try{
            emit(DataState.Loading)
            val dataProcessor = DataProcessor(context)
          val profileDetails = retrofitClient.getProfileDetails(
              "user/getuserbyid/" + dataProcessor.getStr(
                  "user_id"
              )
          )
            emit(DataState.Success(profileDetails))
        }catch (e: Exception)
        {
            emit(DataState.Error(e))
        }

    }

    suspend fun updateProfile(profileUpdateRequest: ProfileUpdateRequest): Flow<DataState<UpdateProfileResponseModel>> = flow  {
        Log.i("ProfileRepository", "On click")

        try{
            emit(DataState.Loading)

           var  fileToUpload: MultipartBody.Part? = null
            val file = java.io.File(profileUpdateRequest.image)

            if(!profileUpdateRequest.image.isNullOrEmpty())
            {
//Create a file object using file path
                val file = java.io.File(profileUpdateRequest.image)
                // Parsing any Media type file
                val requestBody: RequestBody = RequestBody.create(MediaType.parse("*/*"), file)
                 fileToUpload =
                    MultipartBody.Part.createFormData("filename", file.getName(), requestBody)
            }
            val request = Gson().toJson(profileUpdateRequest)
// Here the json data is add to a hash map with key data

            // Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> = HashMap()
            params["data"] = request
            /* RequestBody.create()*/

            //creating request body for file

            //creating request body for file
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("image/*"), file)
            println("ProfileRepository: "+file.absolutePath)
            val descBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json"),
                request
            )
            val mImagePart = prepareFilePart("image", profileUpdateRequest.image)
            val fullName: RequestBody = createPartFromString(profileUpdateRequest.fullname)!!
            val userId: RequestBody = createPartFromString(profileUpdateRequest.userId)!!
           /* val personalDetails: RequestBody = createPartFromObject(
                profileUpdateRequest

            )!!*/
            val data: RequestBody = createPartFromString(
                Gson().toJson(
                    profileUpdateRequest
                )
            )!!
            println("Json string: "+Gson().toJson(
                profileUpdateRequest
            ))
            val fileBody: RequestBody = createPartFromFile(file)!!

          /*  val jsonObject =  JSONObject()
            jsonObject.putOpt("personalDetails", personalDetails)
            val jsonObject1 =  JSONObject()
            jsonObject1.putOpt("professionalDetails", professionalDetails)*/
            val map = HashMap<String, RequestBody>()
           /* map.put("fullname", fullName)
            map.put("userId", userId)*/
            if(!profileUpdateRequest.image.isNullOrEmpty()) {
                map.put("file", requestFile)
            }
            map.put("data", data)


            //map.put("personalDetails",  profileUpdateRequest.personalDetails)
            //map.put("professionalDetails", createPartFromObject(jsonObject1)!!)

            /* map.put("personalDetails", personalDetails)
             map.put("professionalDetails", professionalDetails)*/



            val profileDetails = retrofitClient.updateProfile(map)
            emit(DataState.Success(profileDetails))

        }catch (e: java.lang.Exception)
        {
            emit(DataState.Error(e))

        }

    }

    private fun createPartFromObject(personalDetails: PersonalDetails): RequestBody? {
       // personalDetails.toByt
        return RequestBody.create(MultipartBody.FORM, convertObjectToBytes(personalDetails))

    }

    private fun convertObjectToBytes(obj: Any?): ByteArray? {
        val boas = ByteArrayOutputStream()
        try {
            ObjectOutputStream(boas).use { ois ->
                ois.writeObject(obj)
                return boas.toByteArray()
            }
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
        throw RuntimeException()
    }

    private fun prepareFilePart(partName: String, url: String): MultipartBody.Part? {
        val file = File(url)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData(
            partName, file.name,
            requestFile
        )
    }

    private fun createPartFromString(string: String?): RequestBody? {
        return RequestBody.create(MultipartBody.FORM, string)

    }

    private fun createPartFromObject(jsonObject: JSONObject): RequestBody? {

        val JSON = MediaType.parse("application/json; charset=utf-8")
        val gson = Gson()
        val data = gson.toJson(jsonObject)
        return RequestBody.create(JSON, data)
       // return RequestBody.create(MultipartBody.FORM, string)

    }

    private fun createPartFromFile(file: File): RequestBody? {
        return RequestBody.create(MultipartBody.FORM, file)
    }
}