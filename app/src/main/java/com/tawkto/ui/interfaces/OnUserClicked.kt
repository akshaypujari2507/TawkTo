package com.tawkto.ui.interfaces

import com.tawkto.data.local.entities.Users

interface OnUserClicked {
    fun onItemClicked(user: Users)
}