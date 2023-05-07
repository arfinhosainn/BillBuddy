package com.example.util

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.EntertainmentBG
import com.example.ui.theme.FoodBG
import com.example.ui.theme.GroceryBG
import com.example.ui.theme.OthersBG
import com.example.ui.theme.TravelBG
import com.example.ui.theme.VideoGamesBG

enum class ExpenseCategoryIcon(val title: String, val icon: Int, val bgRes: Color) {
    SHOPPING("Shopping", R.drawable.onlineshopping, TravelBG),
    ENTERTAINMENT("Entertainment", R.drawable.entertainment, EntertainmentBG),
    VIDEOGAMES("Video Games", R.drawable.videogames, VideoGamesBG),
    FOOD("Food & Drink", R.drawable.food, FoodBG),
    GROCERY("Grocery", R.drawable.grocery, GroceryBG),
    ADDNEW("Others", R.drawable.addnew, OthersBG)
}
