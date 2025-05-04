package com.homecookapp.user.utils


import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateTimeUtils {

    /**
     * get difference between current time and provided timezone
     *
     * @return
     */
    val timeOffset: Long
        get() {
            val currentTime = System.currentTimeMillis()
            val edtOffset = TimeZone.getTimeZone("UTC").getOffset(currentTime)
            val current = TimeZone.getDefault().getOffset(currentTime)
            return (current - edtOffset).toLong()
        }

    /**
     * To convert a date to timestamp
     *
     * @param dateToConvert date to be converted
     * @param dateFormat    format of date entered
     * @return timestamp in milliseconds
     */

    fun convertDateToTimeStamp(dateToConvert: String, dateFormat: String): Long {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        var date: Date? = null
        try {
            date = formatter.parse(dateToConvert)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date!!.getTime()
    }

    /**
     * Convert date from one format to another
     *
     * @param dateToConvert date to be converted
     * @param formatFrom    the format of the date to be converted
     * @param formatTo      the format of date you want the output
     * @return date in string as per the entered formats
     */
    @SuppressLint("SimpleDateFormat")
    fun convertDateOneToAnother(dateToConvert: String, formatFrom: String, formatTo: String): String? {
        var outputDateStr: String? = null
        val inputFormat = SimpleDateFormat(formatFrom)
        val outputFormat = SimpleDateFormat(formatTo)
        val date: Date
        try {
            date = inputFormat.parse(dateToConvert)
            outputDateStr = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return outputDateStr
    }



    companion object {

        private val SECOND_MILLIS = 1000
        private val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private val DAY_MILLIS = 24 * HOUR_MILLIS

        var monthName = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")


        /**
         * get date form timestamp
         *
         * @param timestamp time to be converter
         * @return date in string
         */

        fun getDateFromTimestamp(timestamp: String): String {

            val time = java.lang.Long.parseLong(timestamp) * 1000
            try {
                val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val netDate = Date(time)
                return sdf.format(netDate)
            } catch (ex: Exception) {
                return "xx xxxx xxxx"
            }

        }

        @SuppressLint("SimpleDateFormat")
        fun dateParsing(date: String, hour: Int, minute: Int, sign: String): String {

            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            val d = df.parse(date)
            val gc = GregorianCalendar() as Calendar
            gc.time = d

            if (gc.get(15) >= 0) {
                // gc.add(Calendar.HOUR, -(TimeUnit.HOURS.convert(gc.get(15).toLong(), TimeUnit.MILLISECONDS).toInt()))
                gc.add(Calendar.MINUTE, -(TimeUnit.MINUTES.convert(gc.get(15).toLong(), TimeUnit.MILLISECONDS).toInt()))
            } else {
                gc.add(Calendar.HOUR, (TimeUnit.HOURS.convert(gc.get(15).toLong(), TimeUnit.MILLISECONDS).toInt()))
                gc.add(Calendar.MINUTE, (TimeUnit.MINUTES.convert(gc.get(15).toLong(), TimeUnit.MILLISECONDS).toInt()))
            }

            if (sign == "+") {
                gc.add(Calendar.HOUR, (TimeUnit.HOURS.convert(hour.toLong(), TimeUnit.MILLISECONDS).toInt()))
                gc.add(Calendar.MINUTE, (TimeUnit.MINUTES.convert(minute.toLong(), TimeUnit.MILLISECONDS).toInt()))
            } else {
                gc.add(Calendar.HOUR, -(TimeUnit.HOURS.convert(hour.toLong(), TimeUnit.MILLISECONDS).toInt()))
                gc.add(Calendar.MINUTE, -(TimeUnit.MINUTES.convert(minute.toLong(), TimeUnit.MILLISECONDS).toInt()))
            }
            val formatter = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)

            formatter.format(gc.time)
            return formatter.format(gc.time)
        }

        /**
         * Get current timestamp in seconds
         *
         * @return current device time in seconds
         */
        val timeStampInSeconds: Long
            get() = System.currentTimeMillis() / 1000

        fun getTimeAgo(time: Long?): String? {
            var time = time


            if (time != null) {
                if (time < 1000000000000L) {
                    // if timestamp given in seconds, convert to millis
                    time *= 1000
                }
            }

            val now = System.currentTimeMillis()
            if (time != null) {
                if (time > now || time <= 0) {
                    return "just now"
                }
            }

            // TODO: localize
            val diff = now - time!!
            if (diff < MINUTE_MILLIS) {
                return "just now"
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago"
            } else if (diff < 50 * MINUTE_MILLIS) {
                return (diff / MINUTE_MILLIS).toString() + " minutes ago"
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago"
            } else if (diff < 24 * HOUR_MILLIS) {
                return (diff / HOUR_MILLIS).toString() + " hours ago"
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday"
            } else {
                val calendar = Calendar.getInstance()
                calendar.setTimeInMillis(time)

                val Month = calendar.get(Calendar.MONTH)
                val Date = calendar.get(Calendar.DAY_OF_MONTH)
                val Day = calendar.get(Calendar.DAY_OF_WEEK)
                val Hour = calendar.get(Calendar.HOUR_OF_DAY)
                val Min = calendar.get(Calendar.MINUTE)
                if(Min < 10){
                    return monthName[Month] + " " + Date + " at " + Hour + ":" + "0" +Min
                }
                else{
                    return monthName[Month] + " " + Date + " at " + Hour + ":" + Min
                }
            }
        }

        fun getTimeToday(time: Long?): String? {
            var time = time

            if (time != null) {
                if (time < 1000000000000L) {
                    // if timestamp given in seconds, convert to millis
                    time *= 1000
                }
            }

            val now = System.currentTimeMillis()
            if (time != null) {
                if (time > now || time <= 0) {
                    return "today"
                }
            }

            // TODO: localize
            val diff = now - time!!
            if (diff < MINUTE_MILLIS) {
                return "today"
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "today"
            } else if (diff < 50 * MINUTE_MILLIS) {
                return "today"
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "today"
            } else if (diff < 24 * HOUR_MILLIS) {
                return "today"
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday"
            } else {
                val calendar = Calendar.getInstance()
                calendar.setTimeInMillis(time)

                val Month = calendar.get(Calendar.MONTH)
                val Date = calendar.get(Calendar.DAY_OF_MONTH)
                val Day = calendar.get(Calendar.DAY_OF_WEEK)
                val Hour = calendar.get(Calendar.HOUR_OF_DAY)
                val Min = calendar.get(Calendar.MINUTE)
                if(Min < 10){
                    return monthName[Month] + " " + Date + " at " + Hour + ":" + "0" +Min
                }
                else{
                    return monthName[Month] + " " + Date + " at " + Hour + ":" + Min
                }
            }
        }

//        fun getCalendarTime(time: String): String {
//            val dateTime_Utc = DateTime(time, DateTimeZone.getDefault())  // Specifying a time zone to apply, rather than implicitly assigning the JVM’s current default.
//            val date = dateTime_Utc.toDate() //
//            val timeInMillis = date.time
//            val calendar = Calendar.getInstance()
//            calendar.setTimeInMillis(timeInMillis)
//            val Month = calendar.get(Calendar.MONTH)
//            val Date = calendar.get(Calendar.DAY_OF_MONTH)
//
//            return Date.toString() + " " + monthName[Month]
//
//        }


//        fun timeFormat(date: String, time: String): String {
//            val timeAndDAte = "$date $time"
//
//            val formatter = SimpleDateFormat("EEE dd, MMM yyyy hh:mm aa")
//            val dt = formatter.parse(timeAndDAte)
//
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXX")
//
//            return dateFormat.format(dt)
//        }


    }

}