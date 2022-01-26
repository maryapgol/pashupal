package com.aztechz.probeez.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.forEach
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aztechz.probeez.R
import com.aztechz.probeez.data.Topic
import com.aztechz.probeez.data.topics
import com.aztechz.probeez.databinding.FragmentProfileImageBinding
import com.aztechz.probeez.model.profile.OnInterestSelectListener
import com.aztechz.probeez.model.profile.ProfileData
import com.aztechz.probeez.ui.profile.adapter.InterestAdapter
import com.aztechz.probeez.util.spring
import com.aztechz.probeez.utils.Utility.fontRegular
import com.facebook.internal.Utility
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_personal_profile.*
import kotlinx.android.synthetic.main.fragment_profile_image.*
import kotlinx.android.synthetic.main.imagepicker_dailog.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProfileImageFragment : Fragment() {

    private var arrList: ArrayList<String>? = ArrayList()
    private var arrListTopic: ArrayList<Topic?>? = null
    private val profileImageFragment: ProfileImageFragmentArgs by navArgs()
    private var binding : FragmentProfileImageBinding?=null
    private var profileData: ProfileData? = null
    var strFilePath = ""
    private val GALLERY = 0x1
    private val CAMERA = 0x2
    private var photoFile: File? = null
    private val CAPTURE_IMAGE_REQUEST = 300
    private var mCurrentPhotoPath: String? = null
    private var IMAGE_DIRECTORY_NAME: String? = "PROBEEZ"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentProfileImageBinding.inflate(inflater, container, false).apply {
            /*fab.setOnClickListener {
                findNavController().navigate(R.id.action_onboarding_to_featured)
            }*/
            arrListTopic = ArrayList()
            arrListTopic?.addAll(topics.toList())
            topicGrid.apply {
                adapter = InterestAdapter(activity as Activity, arrListTopic,onItemSelectedListener)
               layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)
                smoothScrollToPositionWithSpeed(topics.size)
                /*addOnScrollListener(
                    OscillatingScrollListener(resources.getDimensionPixelSize(R.dimen.grid_2))
                )*/
            }

            saveNextImage.setOnClickListener {
                arrList?.clear()
                for(topic in arrListTopic as ArrayList)
                {
                    if(topic?.isSelected == true) {
                        arrList?.add(topic.name.toString())
                    }
                }
                val action =
                    ProfileImageFragmentDirections.actionProfileImageFragmentToProfilePersonalFragment(
                        signupTaskEmailInp.editText?.text.toString(),
                        arrList?.toTypedArray()!!,
                        profileData,
                        strFilePath
                    )
                findNavController().navigate(action)
            }

            skip.setOnClickListener {
                val action =
                    ProfileImageFragmentDirections.actionProfileImageFragmentToProfilePersonalFragment(
                        signupTaskEmailInp.editText?.text.toString(),
                        arrList?.toTypedArray()!!,
                        profileData,
                        strFilePath
                    )
                findNavController().navigate(action)
            }

             profileImageView.setOnClickListener {
                 showPictureDialog()
             }


            pdSubtitleText.typeface = com.aztechz.probeez.utils.Utility.fontRegular
            saveNextImage.typeface = com.aztechz.probeez.utils.Utility.fontMedium
            skip.typeface = com.aztechz.probeez.utils.Utility.fontMedium
            pdTitleText.typeface = com.aztechz.probeez.utils.Utility.fontBold
            choose.typeface = com.aztechz.probeez.utils.Utility.fontBold
            signupTaskEmailInp.typeface = com.aztechz.probeez.utils.Utility.fontRegular
            edtSignUp.typeface = com.aztechz.probeez.utils.Utility.fontRegular
        }
        return binding?.root as View
    }

    private fun setProfileData(profileData: ProfileData?) {
        print("Profile data: "+profileData)
        this.profileData = profileData
        if(profileData!=null)
        {
            arrList?.clear()
            arrList?.addAll(profileData.personalDetails?.intersts as Collection<String>)
            binding?.signupTaskEmailInp?.editText?.setText(profileData.personalDetails?.about ?:"")
            for(topic in arrListTopic ?: ArrayList())
            {
               for(courseName in arrList?: ArrayList())
               {
                   if(topic?.name == courseName)
                   {
                       topic.isSelected = true
                       break
                   }
               }
            }
            binding?.topicGrid?.adapter?.notifyDataSetChanged()

            if(!profileData.personalDetails?.about.isNullOrEmpty()) {
                edtSignUp.setText(profileData.personalDetails?.about)
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrList = ArrayList()
        setProfileData(profileImageFragment.profileData)

    }

    private val onItemSelectedListener = object : OnInterestSelectListener {
        override fun onInterestSelected(position: Int) {
            arrListTopic?.get(position)?.isSelected = !((arrListTopic?.get(position)?.isSelected)?:false)
            topic_grid.adapter?.notifyDataSetChanged()
        }

    }


    private fun showPictureDialog() {

        val dialog =
            LayoutInflater.from(activity).inflate(R.layout.imagepicker_dailog, null)
        val bottomSheerDialog = BottomSheetDialog(activity as Activity)
        bottomSheerDialog.setContentView(dialog)
        bottomSheerDialog.setCanceledOnTouchOutside(true)
        bottomSheerDialog.setCancelable(true)

        dialog.imagePickerLayout.setBackgroundColor(Color.TRANSPARENT)

        dialog.txtTitle.typeface = com.aztechz.probeez.utils.Utility.fontRegular
        dialog.txtMessage.typeface = com.aztechz.probeez.utils.Utility.fontThin
        dialog.txtTakePhoto.typeface = com.aztechz.probeez.utils.Utility.fontRegular
        dialog.txtChoosePhoto.typeface = com.aztechz.probeez.utils.Utility.fontRegular
        dialog.txtCancel.typeface = com.aztechz.probeez.utils.Utility.fontSemiBold

        dialog.txtTakePhoto.setOnClickListener {
            takeFromCamera()
            bottomSheerDialog.cancel()
        }

        dialog.txtChoosePhoto.setOnClickListener {
            choosePhotoFromGallery()
            bottomSheerDialog.cancel()
        }

        dialog.txtCancel.setOnClickListener { bottomSheerDialog.dismiss() }

        bottomSheerDialog.show()
        (dialog.parent as View).setBackgroundColor(resources.getColor(android.R.color.transparent))
    }


    private fun takeFromCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            captureImage()
        } else {
            captureImage2()
        }

    }

    private fun captureImage2() {

        try {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = createImageFile4()
            if (photoFile != null) {
                val photoURI = Uri.fromFile(photoFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST)
            }
        } catch (e: Exception) {
            // displayMessage(getBaseContext(),"Camera is not available."+e.toString());
        }
    }

    private fun captureImage() {

        if (ContextCompat.checkSelfPermission(
                activity as Context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(activity?.getPackageManager()!!) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile()
                    Log.i("Path", photoFile?.getAbsolutePath().toString())

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        val photoURI = FileProvider.getUriForFile(
                            activity as Activity,
                            "com.aztechz.probeez.FileProvider",
                            photoFile!!
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
                    }
                } catch (ex: Exception) {
                    // Error occurred while creating the File
                    //displayMessage(getBaseContext(),ex.getMessage().toString());
                }


            } else {
                //displayMessage(getBaseContext(),"Nullll");
            }
        }

    }


    private fun createImageFile(): File {
// Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_";
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun createImageFile4(): File? {
// External sdcard location
        val mediaStorageDir = File(
            Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            IMAGE_DIRECTORY_NAME
        );
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                //displayMessage(getBaseContext(),"Unable to create directory.");
                return null
            }
        }

        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val mediaFile = File(
            mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg"
        )

        return mediaFile
    }

    private fun choosePhotoFromGallery() {

        if (ContextCompat.checkSelfPermission(
                activity as Activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY
            )
        } else {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY)
        }
    }

    private fun displayImage(strBase64: String) {
        val decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        profileImageView.setImageBitmap(decodedByte)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                takeFromCamera()
        } else if (requestCode == GALLERY) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                choosePhotoFromGallery()
        }
    }
    private fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory =
            File((Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(
                wallpaperDirectory,
                ((Calendar.getInstance().getTimeInMillis()).toString() + ".jpg")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(activity, arrayOf(f.getPath()), arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ""
    }

    private fun cropToSquare(bitmap: Bitmap): Bitmap {
        val width: Int = bitmap.getWidth()
        val height: Int = bitmap.getHeight();
        val newWidth = if (height > width) width else height
        val newHeight = if (height > width) (height - (height - width)) else height
        var cropW = (width - height) / 2
        cropW = if (cropW < 0) 0 else cropW
        var cropH = (height - width) / 2
        cropH = if (cropH < 0) 0 else cropH
        val cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight)
        return cropImg
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val bitmap: Bitmap
        when (requestCode) {
            GALLERY -> if (data != null) {
                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                    strFilePath = saveImage(bitmap)
                    profileImageView?.setImageBitmap(bitmap)
                    //strImgProfile = convertToBase64(profileImageView)
                    //displayImage(strImgProfile)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            CAPTURE_IMAGE_REQUEST -> {
                //val myBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
                val options = BitmapFactory.Options()
                options.inSampleSize = 4
                strFilePath = photoFile?.absolutePath.toString()
                val myBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath, options)
                val rotatedBitmap = cropToSquare(rotateImage(myBitmap, photoFile?.absolutePath.toString()))
                profileImageView.setImageBitmap(rotatedBitmap)
                //strImgProfile = convertToBase64(imgProfilePicture)
            }
        }
    }

    private fun rotateImage(bitmap: Bitmap, imagePath: String): Bitmap {
        val ei = ExifInterface(imagePath)
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        );

        var rotatedBitmap: Bitmap? = null
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotatedBitmap = rotateImage(bitmap, 90F)
            }

            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotatedBitmap = rotateImage(bitmap, 180F)
            }

            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotatedBitmap = rotateImage(bitmap, 270F)

            }

            ExifInterface.ORIENTATION_NORMAL -> {
                rotatedBitmap = bitmap

            }
            else -> {
                rotatedBitmap = bitmap

            }
        }

        return rotatedBitmap
    }

    public fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle);
        return Bitmap.createBitmap(
            source, 0, 0, source.getWidth(), source.getHeight(),
            matrix, true
        )
    }

    companion object {
        private val IMAGE_DIRECTORY = ""
    }

}



/**
 * Oscillates a [RecyclerView]'s children based on the horizontal scroll velocity.
 */
private const val MAX_OSCILLATION_ANGLE = 6f // ±6º

class OscillatingScrollListener(
    @Px private val scrollDistance: Int
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // Calculate a rotation to set from the horizontal scroll
        val clampedDx = dx.coerceIn(-scrollDistance, scrollDistance)
        val rotation = (clampedDx / scrollDistance) * MAX_OSCILLATION_ANGLE
        recyclerView.forEach {
            // Alter the pivot point based on scroll direction to make motion look more natural
            it.pivotX = it.width / 2f + clampedDx / 3f
            it.pivotY = it.height / 3f
            it.spring(SpringAnimation.ROTATION).animateToFinalPosition(rotation)
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState != RecyclerView.SCROLL_STATE_DRAGGING) {
            recyclerView.forEach {
                it.spring(SpringAnimation.ROTATION).animateToFinalPosition(0f)
            }
        }
    }



}




fun RecyclerView.smoothScrollToPositionWithSpeed(
    position: Int,
    millisPerInch: Float = 100f
) {
    val lm = requireNotNull(layoutManager)
    val scroller = object : LinearSmoothScroller(context) {
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return millisPerInch / displayMetrics.densityDpi
        }
    }
    scroller.targetPosition = position
    lm.startSmoothScroll(scroller)
}