package com.homecookapp.user.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.example.groovyshopping.R
import com.squareup.picasso.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


object MyUtils {

    fun Activity.myToast(msg: String) {
        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(this))

        //view.txtMassage.text = msg

        //view.btnOk.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }
        //view.btnCancel.visibility = View.GONE


        //dialog = AlertDialog.Builder(this)
            //.setView(view.root)
            //.setCancelable(false)
            //.show()

        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


    }


    fun Fragment.myToast(msg: String) {
        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(requireContext()))


        //view.txtMassage.text = msg

        /*view.btnOk.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        //view.btnCancel.visibility = View.GONE


        //dialog = AlertDialog.Builder(requireContext())
            //.setView(view.root)
            //.setCancelable(false)
            //.show()

        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    fun AppCompatActivity.confirmDialog(msg: String, confirm: (AlertDialog?) -> Unit) {
        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(this))


        //view.txtMassage.text = msg

        /*view.btnOk.setOnClickListener {
            confirm.invoke(dialog)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/

        /*view.btnCancel.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        //view.btnCancel.visibility = View.GONE


        //dialog = AlertDialog.Builder(this)
            //.setView(view.root)
            //.setCancelable(false)
            //.show()

        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


    }

    fun Fragment.confirmDialog(msg: String, confirm: (AlertDialog?) -> Unit) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(requireContext()))


        //view.txtMassage.text = msg

        /*view.btnOk.setOnClickListener {
            confirm.invoke(dialog)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        /*view.btnCancel.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/


        //dialog = AlertDialog.Builder(requireContext())
            //.setView(view.root)
            //.setCancelable(false)
            //.show()


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


    }

    fun Activity.myToastAction(msg: String, confirm: (AlertDialog) -> Unit) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(this))


        //view.txtMassage.text = msg

        /*view.btnOk.setOnClickListener {
            confirm.invoke(dialog!!)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        //view.btnCancel.visibility = View.GONE


        //dialog = AlertDialog.Builder(this)
            //.setView(view.root)
            //.setCancelable(false)
            //.show()


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


    }

    fun Activity.myToastAction(
        title: String,
        msg: String,
        showImage: Boolean,
        confirm: (AlertDialog) -> Unit
    ) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(this))


        //view.txtTitle.text = title
        //view.txtMassage.text = msg
        //view.imageWarning.isVisible = showImage

        /*view.btnOk.setOnClickListener {
            confirm.invoke(dialog!!)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        //view.btnCancel.visibility = View.GONE


        //dialog = AlertDialog.Builder(this)
            //.setView(view.root)
            //.setCancelable(false)
            //.show()


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


    }

    fun Fragment.myToastAction(msg: String, confirm: (AlertDialog) -> Unit) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(requireContext()))


        //view.txtMassage.text = msg

        /*view.btnOk.setOnClickListener {
            confirm.invoke(dialog!!)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        //view.btnCancel.visibility = View.GONE


        //dialog = AlertDialog.Builder(requireContext())
            //.setView(view.root)
            //.setCancelable(false)
            //.show()


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    fun Fragment.myToastAction(msg: String, btnText: String, confirm: (AlertDialog) -> Unit) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(requireContext()))

        //view.btnOk.text = btnText
        //view.txtMassage.text = msg

        /*view.btnOk.setOnClickListener {
            confirm.invoke(dialog!!)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        //view.btnCancel.visibility = View.GONE


        //dialog = AlertDialog.Builder(requireContext())
            //.setView(view.root)
            //.setCancelable(false)
            //.show()


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    fun Fragment.myToastAction2Button(msg: String, confirm: (AlertDialog) -> Unit) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(requireContext()))


        //view.txtMassage.text = msg

        /*view.btnOk.setOnClickListener {
            confirm.invoke(dialog!!)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        /*view.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }*/


        //dialog = AlertDialog.Builder(requireContext())
            //.setView(view.root)
            //.setCancelable(false)
            //.show()


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }


    fun Fragment.myToastAction2Button(msg: String,btnText:String, confirm: (AlertDialog) -> Unit) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(requireContext()))

        //view.btnOk.text = btnText

        //view.txtMassage.text = msg

        //view.btnOk.setOnClickListener {
            confirm.invoke(dialog!!)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }
        //view.btnCancel.setOnClickListener {
            //dialog?.dismiss()
        //}


        //dialog = AlertDialog.Builder(requireContext())
            //.setView(view.root)
            //.setCancelable(false)
            //.show()


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    //}

    fun Activity.myToastAction2Button(msg: String, confirm: (AlertDialog) -> Unit) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(this))


        //view.txtMassage.text = msg
//        view.txtTitle.text = resources.getString(R.string.note)

        //view.btnOk.setOnClickListener {
            confirm.invoke(dialog!!)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }
        //view.btnCancel.setOnClickListener {
            //dialog?.dismiss()
        //}


        /*dialog = AlertDialog.Builder(this)
            .setView(view.root)
            .setCancelable(false)
            .show()*/


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    //}

    fun Activity.myToast(
        title: String,
        msg: String,
        btn1: String,
        btn2: String,
        confirm: (AlertDialog) -> Unit
    ) {

        var dialog: AlertDialog? = null

        //var view = DialogConfirmBinding.inflate(LayoutInflater.from(this))


        //view.txtMassage.text = msg
        //view.txtTitle.text = title

        //view.btnOk.text = btn1
        //view.btnCancel.text = btn2
        /*view.btnOk.setOnClickListener {
            confirm.invoke(dialog!!)
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }*/
        /*view.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }*/


        /*dialog = AlertDialog.Builder(this)
            .setView(view.root)
            .setCancelable(false)
            .show()*/


        //dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }


    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }


    fun ImageView.loadImg(url: String) {
        if (!TextUtils.isEmpty(url))
            Picasso.get().load(url).into(this)
    }

    fun TextView.changePartTextColor(
        text: String,
        split: String,
        color: Int,
        spanClick: Boolean = false,
        textClick: () -> Unit = {}
    ) {

        val startIndex = text.indexOf(split)

        if (startIndex == -1)
            return

        val span = SpannableString(text)

        if (spanClick) {
            span.setSpan(
                object : ClickableSpan() {
                    override fun onClick(view: View) {
                        textClick.invoke()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.isUnderlineText = false
                    }
                }, text.indexOf(split), text.length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            movementMethod = LinkMovementMethod.getInstance();
        }

        span.setSpan(
            ForegroundColorSpan(color), text.indexOf(split), text.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        this.text = span
    }

    fun AppCompatCheckBox.changePartTextColorBox(
        text: String,
        split: String,
        color: Int,
        textClick: (View) -> Unit = {}
    ) {

        val startIndex = text.indexOf(split)

        if (startIndex == -1)
            return

        val span = SpannableString(text)

        span.setSpan(
            object : ClickableSpan() {
                override fun onClick(view: View) {
                    textClick.invoke(view)
                }
            }, text.indexOf(split), text.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        movementMethod = LinkMovementMethod.getInstance();


        span.setSpan(
            ForegroundColorSpan(color), text.indexOf(split), text.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        this.text = span

        this.setOnClickListener {
            textClick.invoke(it)
        }
    }

    fun TextView.changeWordColor(word: String, color: Int, click: () -> Unit) {

        val startIndex = text.indexOf(word)

        if (startIndex == -1)
            return

        val span = SpannableString(text)

        span.setSpan(
            object : ClickableSpan() {
                override fun onClick(view: View) {
                    click.invoke()
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }
            }, startIndex, startIndex + word.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            ForegroundColorSpan(color), startIndex, startIndex + word.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )


        this.text = span

        movementMethod = LinkMovementMethod.getInstance();

    }

    fun Activity.makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                } else {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                statusBarColor = Color.TRANSPARENT
            }
        }
    }

    fun Activity.makeStatusBarVisible() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility = 0
                statusBarColor = resources.getColor(R.color.black)
            }
        }
    }


    fun String.openImageGallery(context: Context) {
        try {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(Uri.parse(this), "image/*")
            intent.setPackage("com.google.android.apps.photos")
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(Uri.parse(this), "image/*")
            context.startActivity(intent)
        }
    }

    suspend fun File.createThumb(): Bitmap {

        return CoroutineScope(Dispatchers.IO).async {
            Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path), 100, 100, false)
        }.await()
    }

    inline fun <reified T> SharedPreferences.getObject(key: String): T? {

        val objectJson = getString(key, "")

        if (!objectJson.isNullOrEmpty()) {
            return Gson().fromJson(objectJson, T::class.java)
        }

        return null
    }

    fun <T> SharedPreferences.saveObject(key: String, objectToSave: T) {

        CoroutineScope(Dispatchers.Default).launch {
            val objectJson = Gson().toJson(objectToSave)

            if (!objectJson.isNullOrEmpty())
                edit().putString(key, objectJson).apply()
        }

    }

    fun Activity.openLocationMap(lat: String, lng: String) {
        openMap(lat, lng, this)
    }

    fun Fragment.openLocationMap(lat: String, lng: String) {
        openMap(lat, lng, this.requireActivity())
    }

    private fun openMap(lat: String, lng: String, context: Context) {
        if (appInstalledOrNot("com.google.android.apps.maps", context)) {
            val gmmIntentUri = Uri.parse("geo:${lat},${lng}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        } else {
            val uri = "http://maps.google.com/maps?q=loc:$lat,$lng"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            context.startActivity(intent)
            Log.d("openMap", "google map not found")
        }
    }

    private fun appInstalledOrNot(uri: String, context: Context): Boolean {
        val pm: PackageManager = context.packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }


    fun Activity.openActivity(open: Class<*>, data: Intent? = null) {
        var intent = Intent(this, open)
        if (data != null) {
            intent.putExtras(data)
        }
        startActivity(intent)
        //overridePendingTransition(R.anim.slide_up, R.anim.no_change)
    }

    fun Fragment.openActivity(open: Class<*>, data: Intent? = null) {
        var intent = Intent(requireContext(), open)
        if (data != null) {
            intent.putExtras(data)
        }
        startActivity(intent)
        //requireActivity().overridePendingTransition(R.anim.slide_up, R.anim.no_change)
    }

    fun Activity.shareContent(shareData: String, shareImg: String = "") {
        if (shareImg.isNullOrEmpty()) {
            shareContentOperator(this, shareData)
        } else {
            shareContentWithImgOperator(this, shareData, shareImg)
        }
    }

    fun Fragment.shareContent(shareData: String, shareImg: String = "") {
        if (shareImg.isNullOrEmpty()) {
            shareContentOperator(requireActivity(), shareData)
        } else {
            shareContentWithImgOperator(requireActivity(), shareData, shareImg)
        }

    }

    private fun shareContentWithImgOperator(
        activity: Activity,
        shareData: String,
        shareImg: String
    ) {
        Glide.with(activity).asBitmap().load(shareImg)
            .into(object : CustomTarget<Bitmap>() {

                override fun onLoadCleared(placeholder: Drawable?) {
                    // do your stuff, you can load placeholder image here
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {


                    val cachePath = File(activity.cacheDir, "images")
                    cachePath.mkdirs() // don't forget to make the directory
                    val stream =
                        FileOutputStream(cachePath.toString() + "/image.png") // overwrites this image every time
                    resource.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    stream.close()

                    val imagePath = File(activity.cacheDir, "images")
                    val newFile = File(imagePath, "image.png")
                    val contentUri: Uri = FileProvider.getUriForFile(
                        activity,
                        "${BuildConfig.APPLICATION_ID}.provider",
                        newFile
                    )

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "image/*"
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        shareData
                    )
                    intent.putExtra(Intent.EXTRA_STREAM, contentUri)
                    activity.startActivity(Intent.createChooser(intent, "Choose..."))

                }
            })

    }

    private fun rateApp() {

    }

    fun askRatings(activity: Activity) {
        val appPackageName: String = activity.packageName // from Context

        try {
            try {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        } catch (anfe: ActivityNotFoundException) {
            val intent = Intent("com.huawei.appmarket.intent.action.guidecomment")
            intent.setPackage("com.huawei.appmarket")
            activity.startActivityForResult(intent, 101)
        }
    }

    private fun shareContentOperator(activity: Activity, shareData: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            shareData
        )
        sendIntent.type = "text/plain"
        activity.startActivity(sendIntent)
    }

    fun Activity.shareApp() {
        shareAppOperator(this)
    }

    fun Fragment.shareApp() {
        shareAppOperator(requireActivity())
    }

    private fun shareAppOperator(activity: Activity) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                activity.resources.getString(R.string.app_name)
            )
            val shareMsg = ""
            val appLink =
                "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            shareIntent.putExtra(Intent.EXTRA_TEXT, "\n\n" + shareMsg + "\n\n" + appLink)
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun setRecyclerAnimation(mRecycler: RecyclerView) {
        //mRecycler.layoutAnimation =
            //AnimationUtils.loadLayoutAnimation(mRecycler.context, R.anim.item_recycler_anim)
        //mRecycler.scheduleLayoutAnimation()
        (mRecycler.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    fun isAppInstalled(packageName: String, context: Context): Boolean {
        return try {
            val packageManager = context.packageManager
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun Activity.copyText(text: String) {
        copyTextOp(text, this)
    }

    fun Fragment.copyText(text: String) {
        copyTextOp(text, requireContext())
    }

    fun copyTextOp(text: String, context: Context) {
        val clipboard: ClipboardManager? =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("", text)
        clipboard?.setPrimaryClip(clip)
    }

    fun Activity.openBrowser(target: String) {
        if (target.isNullOrEmpty().not()) {
            openBrowserEngine(target, this)
        } else {
            //myToast(resources.getString(R.string.invalid_link))
        }

    }

    fun Activity.openBrowser(target: Uri) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, target)
        startActivity(browserIntent)
    }

    fun Fragment.openBrowser(target: String) {
        if (target.isNullOrEmpty().not()) {
            openBrowserEngine(target, this.activity)
        } else {
            //myToast(resources.getString(R.string.invalid_link))
        }

    }

    private fun openBrowserEngine(targetUrl: String, activity: Activity?) {
        var url = targetUrl
        if (!targetUrl.startsWith("http://") && !targetUrl.startsWith("https://")) {
            url = "http://$targetUrl"
        }

        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity?.startActivity(browserIntent)
    }

    fun Activity.phoneCall(phoneNum: String?) {
        if (phoneNum != null)
            phoneCallEngine(phoneNum, this)
    }

    fun Fragment.phoneCall(phoneNum: String) {
        if (phoneNum != null)
            phoneCallEngine(phoneNum, requireActivity())
    }

    private fun phoneCallEngine(phoneNum: String, activity: Activity?) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phoneNum}")
        activity?.startActivity(intent)
    }

    fun Activity.sendEmail(email: String) {
        if (email != null)
            sendEmailEngine(email, this)
    }

    fun Fragment.sendEmail(email: String) {
        if (email != null)
            sendEmailEngine(email, requireActivity())
    }


    private fun sendEmailEngine(email: String, activity: Activity?) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        i.putExtra(Intent.EXTRA_SUBJECT, " ")
        i.putExtra(Intent.EXTRA_TEXT, " ")
        try {
            activity?.startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                activity,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    fun Activity.openWhatsWithNum(phoneNum: String) {
        if (phoneNum != null)
            openWhatsAppWithNumEngine(phoneNum, this)
    }

    fun Fragment.openWhatsWithNum(phoneNum: String) {
        if (phoneNum != null)
            openWhatsAppWithNumEngine(phoneNum, requireActivity())
    }

    private fun openWhatsAppWithNumEngine(phoneNum: String, activity: Activity?) {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNum&text= ")

        val sendIntent = Intent(Intent.ACTION_VIEW, uri)

        activity?.startActivity(sendIntent)
    }

    fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }

    fun generateTransactionID(): String {
        val sdf = SimpleDateFormat("ddMyyyyhhmmss", Locale.US)
        val currentDate = sdf.format(Date())

        return currentDate + Random.nextInt(111111, 999999)

    }

    fun getRandomPostCode(): String {
        return Random.nextInt(11111, 49999).toString()
    }

    /**
    change part of string color
    how used :

    toastText?.text = getString(
    R.string.dialog_confirm_transfer,
    edPrice.text.toString())

    toastText.append(getColoredString(edPhoneNum.text.toString(), ContextCompat.getColor(this@TransformBalanceActivity, R.color.blue_white)))

     */
    fun getColoredString(text: CharSequence?, color: Int): Spannable {
        val spannable: Spannable = SpannableString(text)
        spannable.setSpan(
            ForegroundColorSpan(color),
            0,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }


    fun Activity.openPdf(url: String?) {
        if (url.isNullOrEmpty().not())
            openPdfEngine(url!!, this)
    }

    fun Fragment.openPdf(url: String?) {
        if (url.isNullOrEmpty().not())
            openPdfEngine(url!!, this.activity)
    }

    fun openPdfEngine(string: String, activity: Activity?) {
        val pdfIntent = Intent(Intent.ACTION_VIEW)
        pdfIntent.setDataAndType(Uri.parse(string), "application/pdf")
        pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        activity?.startActivity(pdfIntent)
    }

    /*fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }*/


    fun checkTextIsEmptyOrNot(text: String?): Boolean {
        return text.isNullOrEmpty() || text.lowercase().contains("null")
    }

    fun checkValidationCode(text: String?): Boolean {
        return text.isNullOrEmpty() || text.length < 4
    }

    fun checkPassword(text: String?): Boolean {
        return text.isNullOrBlank() || text.length < 6
    }

    fun checkMatchedPassword(text: String?, text1: String?): Boolean {
        return text.isNullOrBlank() || text.length < 6 || (text == text1).not()
    }

    fun emailNotValid(email: String?): Boolean {

        return email.isNullOrEmpty() || android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            .not()
    }





