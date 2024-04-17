package com.sinarkelas.ui.videocallgroup.videoutils

import android.Manifest

val PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)

const val MAX_OPPONENTS_COUNT = 50

const val EXTRA_LOGIN_RESULT = "login_result"

const val EXTRA_LOGIN_ERROR_MESSAGE = "login_error_message"

const val EXTRA_LOGIN_RESULT_CODE = 1002

const val EXTRA_IS_INCOMING_CALL = "conversation_reason"

const val MAX_LOGIN_LENGTH = 50

const val MAX_FULLNAME_LENGTH = 25