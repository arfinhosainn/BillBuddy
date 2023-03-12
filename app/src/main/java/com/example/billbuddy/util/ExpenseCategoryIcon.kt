package com.example.billbuddy.util

import androidx.compose.ui.graphics.Color
import com.example.billbuddy.R
import com.example.billbuddy.ui.theme.*

enum class ExpenseCategoryIcon(val title: String,val icon: Int, val bgRes: Color) {
    SHOPPING("Online Shopping",R.drawable.onlineshopping, TravelBG  ),
    ENTERTAINMENT("Entertainment", R.drawable.entertainment, EntertainmentBG ),
    VIDEOGAMES("Video Games",R.drawable.videogames, VideoGamesBG),
    FOOD("Food & Drink",R.drawable.food, FoodBG ),
    GROCERY("Grocery and Necessary",R.drawable.grocery, GroceryBG),
    ADDNEW("Others",R.drawable.addnew, OthersBG)
}

