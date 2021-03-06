package com.example.weatherable.utilites

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.weatherable.R
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@RequiresApi(Build.VERSION_CODES.M)
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                return true
            }
        }
    }
    return false
}

@SuppressLint("UnspecifiedImmutableFlag")
fun getPendingSelfIntent(
    context: Context?,
    action: String?,
    classWidget: Class<*>
): PendingIntent? {
    val intent = Intent(context, classWidget)
    intent.action = action
    return PendingIntent.getBroadcast(context, 0, intent, 0)
}

val String.sA: String
    get() = this.substringAfter("data-text=\"")
val String.sB: String
    get() = this.substringBefore("\">")
val String.rep: Int
    get() = this.replace(":", "").toInt()
val String.repPlus: String
    get() = this.replace(",", ".").replace("+", "")
fun MutableList<String>.addItem(value: String) = run {
    this.apply {
        value.apply {
            add(sA.sB)
            add(sA.sA.sB)
            add(sA.sA.sA.sB)
            add(sA.sA.sA.sA.sB)
            add(sA.sA.sA.sA.sA.sB)
            add(sA.sA.sA.sA.sA.sA.sB)
            add(sA.sA.sA.sA.sA.sA.sA.sB)
            add(sA.sA.sA.sA.sA.sA.sA.sA.sB)
        }
    }
}

fun MutableList<String>.addToList(value: String){
    var item = ""
    value.forEach {
        if (it.toString() != " ")
            item += it.toString()
        else {
            this.add(item)
            item = ""
        }
    }
    Thread.sleep(300L)
    this.add(item)
}
fun getIconDayGis(value: String) = when (value) {
    "Переменная облачность, сильные осадки" -> R.drawable.gis_o
    "Пасмурно, снег с дождём" -> R.drawable.gis_e
    "Малооблачно, дождь" -> R.drawable.gis_r
    "Малооблачно, небольшой дождь" -> R.drawable.gis_t
    "Облачно, небольшой дождь" -> R.drawable.gis_k
    "Пасмурно, небольшой дождь" -> R.drawable.gis_n
    "Пасмурно, дождь" -> R.drawable.gis_l
    "Пасмурно, сильный дождь" -> R.drawable.gis_m
    "Облачно, дождь" -> R.drawable.gis_j
    "Переменная облачность, небольшой дождь" -> R.drawable.gis_k
    "Переменная облачность, дождь" -> R.drawable.gis_j
    "Пасмурно, снег" -> R.drawable.gis_h
    "Облачно, снег" -> R.drawable.gis_s
    "Пасмурно" -> R.drawable.gis_i
    "Облачно" -> R.drawable.gis_c
    "Малооблачно" -> R.drawable.gis_g
    "Малооблачно, поземок" -> R.drawable.gis_g
    "Малооблачно, снег" -> R.drawable.gis_x
    "Ясно, поземок" -> R.drawable.gis_b
    "Малооблачно, ливневый снег" -> R.drawable.gis_f
    "Пасмурно, мокрый снег" -> R.drawable.gis_e
    "Переменная облачность" -> R.drawable.gis_c
    "Переменная облачность, небольшой снег" -> R.drawable.gis_d
    "Ясно" -> R.drawable.gis_b
    "Пасмурно, небольшой снег" -> R.drawable.gis_a
    "Малооблачно, небольшой снег" -> R.drawable.gis_p
    else -> R.drawable.logo
}

fun getIconNightGis(value: String) = when (value) {
    "Малооблачно, снег" -> R.drawable.gis_x_n
    "Пасмурно, небольшой снег" -> R.drawable.gis_a
    "Пасмурно, мокрый снег" -> R.drawable.gis_e
    "Пасмурно" -> R.drawable.gis_i
    "Пасмурно, снег" -> R.drawable.gis_h
    "Облачно, снег" -> R.drawable.gis_s_n
    "Пасмурно, снег с дождём" -> R.drawable.gis_e
    "Пасмурно, небольшой дождь" -> R.drawable.gis_n
    "Пасмурно, дождь" -> R.drawable.gis_l
    "Пасмурно, сильный дождь" -> R.drawable.gis_m
    "Малооблачно" -> R.drawable.gis_c_n
    "Малооблачно, поземок" -> R.drawable.gis_c_n
    "Малооблачно, ливневый снег" -> R.drawable.gis_k_n
    "Облачно" -> R.drawable.gis_a_n
    "Переменная облачность" -> R.drawable.gis_a_n
    "Ясно" -> R.drawable.gis_b_n
    "Ясно, поземок" -> R.drawable.gis_b_n
    "Малооблачно, небольшой снег" -> R.drawable.gis_d_n
    "Малооблачно, дождь" -> R.drawable.gis_e_n
    "Облачно, дождь" -> R.drawable.gis_f_n
    "Малооблачно, небольшой дождь" -> R.drawable.gis_t_n
    "Облачно, небольшой дождь" -> R.drawable.gis_h_n
    "Переменная облачность, сильные осадки" -> R.drawable.gis_g_n
    "Переменная облачность, небольшой дождь" -> R.drawable.gis_h_n
    "Переменная облачность, дождь" -> R.drawable.gis_f_n
    "Переменная облачность, небольшой снег" -> R.drawable.gis_j_n
    else -> R.drawable.logo
}

fun getIconNightYan(value: String) = when (value) {
    "Дождь со снегом" -> R.drawable.gis_e
    "Небольшой дождь" -> R.drawable.gis_n
    "Ясно" -> R.drawable.gis_b_n
    "Небольшой снег" -> R.drawable.gis_a
    "Пасмурно" -> R.drawable.gis_i
    "Облачно с прояснениями" -> R.drawable.gis_a_n
    "Малооблачно" -> R.drawable.gis_c_n
    else -> R.drawable.logo
}

fun getIconDayYan(value: String) = when (value) {
    "Дождь со снегом" -> R.drawable.gis_e    "Небольшой дождь" -> R.drawable.gis_n
    "Ясно" -> R.drawable.gis_b
    "Небольшой снег" -> R.drawable.gis_a
    "Пасмурно" -> R.drawable.gis_i
    "Облачно с прояснениями" -> R.drawable.gis_c
    "Малооблачно" -> R.drawable.gis_g
    else -> R.drawable.logo
}

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("H:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun String.asDate(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("dd:MM:yy", Locale.getDefault())
    return timeFormat.format(time)
}

fun getStringWasForChat(wasDate: Long): String {
    val timeWas = wasDate.toString().asTime()
    val dateNow = Calendar.getInstance(Locale.getDefault())
    val monthLitWas = formatDate("MMM", wasDate)
    val monthNumWas = formatDate("MM", wasDate)
    val yearWas = formatDate("yyyy", wasDate)
    val yearWasSmall = formatDate("yy", wasDate)
    val dayOfMonthWas = formatDate("d", wasDate)
    val dayOfYearWas = formatDate("D", wasDate)
    val yearNow = dateNow.get(Calendar.YEAR)
    return when (dateNow.get(Calendar.DAY_OF_YEAR) - dayOfYearWas.toInt()) {
        1 -> timeWas
        2 -> timeWas
        0 -> "$timeWas "
        else -> {
            when (yearNow - yearWas.toInt()) {
                1 -> " $dayOfMonthWas.$monthNumWas.$yearWasSmall в $timeWas"
                0 -> " $dayOfMonthWas $monthLitWas в $timeWas"
                else -> " в $yearWas году"
            }
        }
    }
}
private fun formatDate(value: String, date: Long): String {
    return SimpleDateFormat(value, Locale.getDefault()).format(date)
}