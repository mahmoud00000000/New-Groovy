package com.homecookapp.user.utils

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.ParseException
import android.net.Uri
import android.os.CountDownTimer
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.example.groovyshopping.R
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import com.bumptech.glide.Glide
import com.homecookapp.user.application.Application
import java.io.File

object BindingAdapters {

    @JvmStatic
    //@BindingAdapter("error")
    fun setError(textInputLayout: TextInputLayout, error: String?) {
        textInputLayout.error = error
    }

    @JvmStatic
    //@BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    //@BindingAdapter("app:cardBg")
    fun cardBg(cardView: CardView, color: String?) {
        color ?: return
        cardView.setCardBackgroundColor(Color.parseColor(color))
    }


    @JvmStatic
    //@BindingAdapter("app:errorText")
    fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
        view.error = errorMessage
    }

    @JvmStatic
    //@BindingAdapter("app:errorText")
    fun setErrorMessage(view: EditText, errorMessage: String?) {
        view.error = errorMessage
    }

    @JvmStatic
    //@BindingAdapter("app:htmlText")
    fun htmlText(textView: TextView, text: String?) {
        text ?: return
        textView.text = Html.fromHtml(text)
    }


    @JvmStatic
    //@BindingAdapter("app:textChangedListener")
    fun pinTextChange(
        editText: PinEntryEditText,
        textWatcher: PinEntryEditText.OnPinEnteredListener
    ) {
        editText.setOnPinEnteredListener(textWatcher)
    }


    @JvmStatic
    //@BindingAdapter("app:rateValue")
    fun setRating(ratingBar: RatingBar, mVoteAverage: Double) {
        mVoteAverage
        ratingBar.rating = mVoteAverage.toFloat()
    }

    @JvmStatic
    //@BindingAdapter("app:setTextColor")
    fun setTextColor(textView: TextView, color: String?) {
        color ?: return
        textView.setTextColor(Color.parseColor(color))
    }

    @JvmStatic
    //@BindingAdapter("app:setBackGroundColor")
    fun setTextColor(view: View, color: String?) {
        color ?: return
        view.setBackgroundColor(Color.parseColor(color))
    }

    @JvmStatic
    //@BindingAdapter("app:setUnderLine")
    fun setUnderLine(textView: TextView, set: Boolean) {
        textView.setPaintFlags(textView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
    }

    @JvmStatic
    //@BindingAdapter("app:setCenterLine")
    fun setCenterLine(textView: TextView, set: Boolean) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

    }

    @JvmStatic
    //@BindingAdapter("app:currency", "app:amount")
    fun setCurrencyAndAmount(
        textView: TextView,
        currency: String,
        amount: Double
    ) {
        textView.text = String.format(Locale.US, "%.2f", amount) + " " + currency
    }


    @JvmStatic
    //@BindingAdapter("app:timer")
    fun timer(textView: TextView, dateString: String?) {

        if (TextUtils.isEmpty(dateString)) {
            textView.visibility = View.GONE
            return
        }

        if (dateString?.contains("visible") == true) {
            return
        }

        val simplreDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = simplreDateFormat.parse(dateString)
        val milliSeconds = date.time - Calendar.getInstance().time.time

        val days = TimeUnit.MILLISECONDS.toDays(milliSeconds)

        if (days > 0) {
            //textView.text = "$days ${textView.context.getString(R.string.left)}"
        } else {
            object : CountDownTimer(milliSeconds, 1000) {
                override fun onFinish() {
                    textView.text = "Expired"
                }

                override fun onTick(tick: Long) {
                    textView.text = String.format(
                        "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(tick),
                        TimeUnit.MILLISECONDS.toMinutes(tick) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(tick) % 60
                    )
                }
            }.start()
        }

    }


    @JvmStatic
    //@BindingAdapter("colorForText")
    fun colorForText(textView: TextView, color: String?) {

        color ?: return

        textView.setTextColor(Color.parseColor(color))
    }

    @JvmStatic
    //@BindingAdapter("app:dateFormat")
    fun dateFormat(textView: TextView, date: String?) {
        date ?: return
        val strDate = date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var convertedDate: Date? = Date()
        try {
            convertedDate = dateFormat.parse(strDate)
            val sdfnewformat = SimpleDateFormat("MMM dd yyyy")
            val finalDateString = sdfnewformat.format(convertedDate)
            textView.text = finalDateString
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }


    @JvmStatic
    //@BindingAdapter(value = ["loadImg", "placeholder"], requireAll = false)
    fun loadImg(imageView: ImageView, url: String?, placeHolder: Drawable?) {
        if (!TextUtils.isEmpty(url)) {
            var placeHolderId = placeHolder

            if (placeHolderId == null) {
//                placeHolderId =
//                    imageView.context.resources.getDrawable(R.drawable.ic_placeholder_square)
            }

//            Picasso.get()
//                .load(url)
//                .error(placeHolderId!!)
//                .placeholder(imageView.context.resources.getDrawable(R.drawable.placeholder))
//                .into(imageView)
            Glide
                .with(imageView.context)
                .load(url)
//                .placeholder(imageView.context.resources.getDrawable(R.drawable.ic_placeholder_square))
                .into(imageView)
        }
    }

    @JvmStatic
    //@BindingAdapter(value = ["loadImgLocal"])
    fun loadImgLocal(imageView: ImageView, url: String?) {
        val imgFile = File(url)

        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            imageView.setImageBitmap(myBitmap)
        }
    }

//    @JvmStatic
//    @BindingAdapter(value = ["loadVideo"])
//    fun loadVideo(imageView: BetterVideoPlayer, url: String?) {
//        if (!TextUtils.isEmpty(url)) {
//            imageView.setCaptions(Uri.parse(url), CaptionsView.SubMime.SUBRIP)
//        }
//    }

    @JvmStatic
    //@BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    //@BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: String?) {
        if (imageUri == null) {
            view.setImageURI(null)
        } else {
            view.setImageURI(Uri.parse(imageUri))
        }
    }

    @JvmStatic
    //@BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: Uri?) {
        view.setImageURI(imageUri)
    }

    @JvmStatic
    //@BindingAdapter("android:src")
    fun setImageDrawable(view: ImageView, drawable: Drawable?) {
        view.setImageDrawable(drawable)
    }


    //@BindingAdapter("app:priceOfferLine")
    fun priceOfferLine(textView: TextView, bool: Boolean) {
        if (bool) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

    }

    //@BindingAdapter("setMarquee")
    fun setMarquee(textView: TextView, selected: Boolean) {
        textView.ellipsize = TextUtils.TruncateAt.MARQUEE
        textView.isSingleLine = true
        textView.marqueeRepeatLimit = -1
        textView.setHorizontallyScrolling(true)
        textView.isSelected = selected
    }


    @JvmStatic
    //@BindingAdapter(value = ["setTintColor"])
    fun setTintColor(view: View, color: String) {
        if (color.isNullOrEmpty().not())
            view.background.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP)
    }

    @JvmStatic
    //@BindingAdapter(value = ["setTintColor"])
    fun setTintColor(view: View, color: Int) {
        view.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    @JvmStatic
    //@BindingAdapter(value = ["setTextViewDrawableColor"])
    fun setTextViewDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(textView.context, color),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
    }

    @JvmStatic
    //@BindingAdapter(value = ["setTextDrawableTintColor"])
    fun setTextDrawableTintColor(textView: TextView, @ColorRes color: Int) {
        for (drawable in textView.compoundDrawables) {
            if (drawable != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drawable.colorFilter =
                        PorterDuffColorFilter(
                            textView.context.getColor(color),
                            PorterDuff.Mode.SRC_IN
                        )
                    drawable.setTint(textView.context.getColor(color))
                }
            }
        }
    }

    @JvmStatic
    //@BindingAdapter(value = ["setTextDrawableTintColor"])
    fun setTextDrawableTintColor(textView: TextView, color: String) {
        for (drawable in textView.compoundDrawables) {
            if (drawable != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drawable.colorFilter =
                        PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN)
                    drawable.setTint(Color.parseColor(color))
                }
            }
        }
    }

    @JvmStatic
    //@BindingAdapter(value = ["changeStrokeColor"])
    fun changeStrokeColor(view: View, color: String) {
        val myGrad: GradientDrawable = view.background as GradientDrawable
        myGrad.setStroke(2, Color.parseColor(color))
    }

    @JvmStatic
    //@BindingAdapter(value = ["changeBackgroundDrawableColor"])
    fun changeBackgroundDrawableColor(view: View, color: String) {
        val backgroundColor = if (color.contains("#")) {
            color
        } else {
            "#$color"
        }

        //val drawable: Drawable = view.resources.getDrawable(R.drawable.border_top_end_with_status)
        //drawable.mutate().setColorFilter(Color.parseColor(backgroundColor), PorterDuff.Mode.SRC_IN)
        //view.background = drawable
    }

    @JvmStatic
    //@BindingAdapter(value = ["setTextBold"], requireAll = true)
    fun setTextBold(textView: TextView, setTextSpannable: String) {
        val spannableString = SpannableString("${textView.text} $setTextSpannable")
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(
            boldSpan,
            textView.length(),
            textView.text.length + setTextSpannable.length + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannableString
    }

    @JvmStatic
    //@BindingAdapter("isRequired")
    fun isRequired(textView: TextView, isRequired: Boolean) {
        if (isRequired) {
            val text = if (textView.text.toString().contains("*")) {
                textView.text.toString()
            } else {
                textView.text.toString() + " * "
            }

            textView.changePartTextColor(
                text,
                "*",
                Color.parseColor("#FF0000")
            )
        }

    }


    @JvmStatic
    //@BindingAdapter("setFont")
    fun setFont(textView: TextView, type: String = "r") {
        /*
                  1- r -> regular
                  2- m -> medium
                  3- s -> semiBold
                  4- b -> bold
              */
        lateinit var face: Typeface

        if (Application.language == "ar") {
            when (type) {
                "r" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.regular)!!
                }

                "m" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.medium)!!
                }

                "s" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.medium)!!
                }

                "b" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.bold)!!
                }

                "e" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.extrabold)!!
                }
            }
        } else {
            when (type) {
                "r" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.regular)!!
                }

                "m" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.medium)!!
                }

                "s" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.medium)!!
                }

                "b" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.bold)!!
                }

                "e" -> {
                    face = ResourcesCompat.getFont(textView.context, R.font.extrabold)!!
                }
            }
        }

        textView.typeface = face

    }
    @JvmStatic
    //@BindingAdapter("setDrawableTint")
    fun setDrawableTint(textView: TextView,colorRes: Int) {
        val colorStateList = try {
            ColorStateList.valueOf(colorRes)
        } catch (e: Resources.NotFoundException) {
            // If it's not a resource ID, directly use it as a resolved color
            ColorStateList.valueOf(colorRes)
        }

        TextViewCompat.setCompoundDrawableTintList(textView, colorStateList)
    }

}