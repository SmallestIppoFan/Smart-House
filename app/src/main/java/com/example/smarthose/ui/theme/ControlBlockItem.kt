package com.example.smarthose.ui.theme

import com.example.smarthose.R

sealed class ControlBlockItem(var title:String,var icon:Int){
    object LightningBlock: ControlBlockItem("Room \nLightning", R.drawable.ceiling_lamp)
    object DoorBlock: ControlBlockItem("Door \nControl", R.drawable.door_handle)
}

