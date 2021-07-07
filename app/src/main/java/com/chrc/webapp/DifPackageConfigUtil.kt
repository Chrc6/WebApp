package com.chrc.webapp

import android.app.Activity
import android.content.Context

/**
 *    @author : chrc
 *    date   : 2021/7/4  3:55 PM
 *    desc   :
 */
class DifPackageConfigUtil {

    companion object {
        const val APP_SUNSHINE = "com.chrc.webapp"
        const val APP_LUCKY = "com.lucky.abc"


        fun getPackageName(activity: Activity): String {
            return activity.packageName
        }

        fun getUrl(): String {
            return when(BuildConfig.APPLICATION_ID) {
                APP_SUNSHINE -> {
                    "https://msnbfc.in/#/home?is_android_apk=true"
                }
                APP_LUCKY -> {
                    "https://luckyabc.in/"
                }
                else -> {
                    "https://www.baidu.com"
                }
            }
        }

        fun getJsKey(): String {
            return when(BuildConfig.APPLICATION_ID) {
                APP_SUNSHINE -> {
                    SunWebJavaInterface.JAVA_KEY
                }
                APP_LUCKY -> {
                    "demo"
                }
                else -> {
                    "demo"
                }
            }
        }

        fun getJsObj(context: Context): Any {
            return when(BuildConfig.APPLICATION_ID) {
                APP_SUNSHINE -> {
                    SunWebJavaInterface(context)
                }
                APP_LUCKY -> {
                    "demo"
                }
                else -> {
                    "demo"
                }
            }
        }

        fun getUa(): String {
            return when(BuildConfig.APPLICATION_ID) {
                APP_SUNSHINE -> {
                    "/sunshine_apk_ua/Android_Webview/"
                }
                APP_LUCKY -> {
                    "/luckyabc_apk_ua/Android_Webview/"
                }
                else -> {
                    ""
                }
            }
        }
    }
}