package com.sinarkelas.ui.videocallgroup.videoutils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun addFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment, tag: String) {
    fragmentManager.beginTransaction().replace(containerId, fragment, tag).commitAllowingStateLoss()
}

fun addFragmentWithBackStack(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment, tag: String) {
    fragmentManager.beginTransaction().replace(containerId, fragment, tag).addToBackStack(null).commitAllowingStateLoss()
}