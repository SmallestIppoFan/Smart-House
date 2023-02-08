package com.example.smarthose

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.smarthose.Icons.BathRoomIcon
import com.example.smarthose.Icons.BedroomIcon
import com.example.smarthose.Icons.GuestRoomIcon
import com.example.smarthose.Icons.KitchenIcon
import com.example.smarthose.Icons.LivingRoomIcon
import com.example.smarthose.Icons.OutsideHouseIcon
import com.example.smarthose.navigation.BottomBarScreen
import com.example.smarthose.ui.theme.BackgroundColor

@Composable
fun TopBar(
    modifier: Modifier= Modifier.height(40.dp),
    colors: Color = BackgroundColor,
    elevation: Dp=0.dp,
    name:String="Amir"
           ) {
    TopAppBar(modifier = modifier,
        backgroundColor = colors,
        elevation = elevation
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
                ){
                Text(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(start = 130.dp),
                    text = "Smart House",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color=Color.Black,
                )
                IconButton(
                    modifier = Modifier.weight(0.1f),
                    onClick = {},
                ) {
                    Image(imageVector = Icons.Default.Settings, contentDescription = "Go to settings")
                }
            }
    }
}

@Composable
fun CustomChipGroup( ) {
    val context= LocalContext.current
    val chipList: List<String> = listOf(
        "Bedroom",
        "Bathroom",
        "Kitchen",
        "Living room",
        "Guest room"
    )
    var selected by remember{
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(start = 15.dp)) {
        LazyRow(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            item {
                chipList.forEach { it ->
                    Chip(
                        title = it,
                        selected = selected,
                        onSelected = {
                            selected = it
                            when (it) {
                                "Bedroom" -> Toast.makeText(
                                    context,
                                    "Bedroom selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                                "Guest room" -> Toast.makeText(
                                    context,
                                    "Guest room selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                                "Kitchen" -> Toast.makeText(
                                    context,
                                    "Kitchen selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                                "Living room" -> Toast.makeText(
                                    context,
                                    "Living-room selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                                "Bathroom" -> Toast.makeText(
                                    context,
                                    "Bathroom selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
            }
        }
        ImageOfTheRoom(roomImage = selected)
    }
}

@Composable
fun ImageOfTheRoom(roomImage:String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        color=Color.Transparent
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            when(roomImage){
                "Bedroom" -> Image(painter = painterResource(id = BedroomIcon), contentDescription ="Image of the bedroom")
                "Bathroom"-> Image(painter = painterResource(id = BathRoomIcon), contentDescription ="Image of the bathroom" )
                "Kitchen"-> Image(painter = painterResource(id = KitchenIcon), contentDescription ="Image of the Kitchen")
                "Living room"-> Image(painter = painterResource(id = LivingRoomIcon), contentDescription ="Image of the Living room" )
                "Guest room"-> Image(painter = painterResource(id = GuestRoomIcon), contentDescription ="Image of the Guest Room" )
                else-> Image(painter = painterResource(id = OutsideHouseIcon), contentDescription ="Outside" )
            }
        }
    }

}

@Composable
fun Chip(
    title:String,
    selected:String,
    onSelected: (String) -> Unit
) {
    val isSelected= selected == title
    val background= if(isSelected) Color.Black else Color.White
    val contentColor =if (isSelected) Color.White else BackgroundColor

    Box(modifier = Modifier
        .padding(end = 10.dp)
        .height(35.dp)
        .clip(CircleShape)
        .background(background)
        .clickable(
            onClick = {
                onSelected(title)
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp,end=10.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "check",
                    tint = Color.White
                    )
            }
            Text(
                text = title,
                color=contentColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
                )
        }
    }
}
@Composable
fun TemperatureBlock(
    temperature:String,
    roomName:String,
    backgroundColor:Color,
    icon:Painter
) {
    Card(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(110.dp)
            .padding(start = 15.dp, end = 15.dp, top = 0.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 3.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 10.dp)
            ) {
            Image(painter = icon, contentDescription = "Icon of the temperature")
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = "$temperature °C ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                    )
                Text(
                    text = "Temperature in $roomName",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
fun ControlBlock(
    icon:Int,
    text:String,
    selected:MutableState<Boolean>
    ) {
    Card(modifier = Modifier
        .height(180.dp)
        .width(180.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 3.dp
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 20.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = icon),
                    modifier = Modifier.size(50.dp),
                    contentDescription = "Icon of the service"
                )
                Spacer(modifier = Modifier.width(50.dp))
                Switch(checked = selected.value, onCheckedChange = {
                    selected.value=it
                })
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "$text",
                fontSize=16.sp,
                fontWeight = FontWeight.Bold
                )
        }
    }
}

@Composable
fun BottomNav(navController: NavController) {
    val items= listOf(
        BottomBarScreen.Home,
        BottomBarScreen.User
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    BottomNavigation(
        modifier = Modifier
            .padding(12.dp, 0.dp, 12.dp, 0.dp)
            .height(100.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        items.forEach {
            BottomNavigationItem(
                icon = {
                    it.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = "",
                            modifier = Modifier.size(35.dp),
                            //tint = Color.Gray

                        )
                    }
                },
                selected = currentRoute?.hierarchy?.any { it.route == it.route } == true,
               // selectedContentColor = Color.Black,
                unselectedContentColor = Color.White,
                onClick = {
                    it.route?.let { it1 ->
                        navController.navigate(it1) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState=false
                        }
                    }
                }
            )
        }
    }
}