package io.king.learncampfacebook

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun TopBar() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "LearnCampApp",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Blue,
            modifier = Modifier
                .fillMaxWidth(.6f)
        )
        Row(horizontalArrangement = Arrangement.End,modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .clip(CircleShape)
                .background(Color.LightGray)
                .size(40.dp)
                .padding(horizontal = 10.dp)) {
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = null, tint = Color.Black)
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .clip(CircleShape)
                .background(Color.LightGray)
                .size(40.dp)) {
                Icon(modifier = Modifier.size(24.dp),painter = painterResource(id = R.drawable.messenger), contentDescription = null, tint = Color.Black)
            }
        }

    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun ContentView() {
    val pagerState = rememberPagerState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        TableRow(pagerState = pagerState)
        PagerContent(pagerState = pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TableRow(pagerState: PagerState) {

    val tabTextandIcon = listOf(
        "tab1" to R.drawable.home,
        "tab2" to R.drawable.group,
        "tab3" to R.drawable.youtube,
        "tab4" to R.drawable.user,
        "tab5" to R.drawable.bell,
        "tab6" to R.drawable.menu
    )

    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = Color.White,
        contentColor = Color.Black,
        divider = {
            TabRowDefaults.Divider(
                thickness = 4.dp,
                color = Color.White
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPositions),
                height = 3.dp,
                color = Color.Blue
            )
        }
    ) {
        tabTextandIcon.forEachIndexed { index, pair ->
            val selected = pagerState.currentPage == index
            Tab(selected = selected,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                enabled = true
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(40.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier
                        .size(40.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                        Icon(
                            painter = painterResource(id = pair.second),
                            contentDescription = null,
                            modifier = Modifier.size(if (selected){30.dp}else{20.dp}),
                            tint = if (selected){Color.Blue}else{Color.Black}
                        )
                    }
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerContent(pagerState: PagerState) {

    CompositionLocalProvider(LocalOverScrollConfiguration provides null) {

        Box(modifier = Modifier.fillMaxSize()){
            HorizontalPager(count = 6, state = pagerState) { page ->
                when(page){
                    0 -> HomeScreen()
                    1 -> GroupScreen()
                    3 -> VideoScreen()
                    4 -> ProfileScreen()
                    5 -> NotificationScreen()
                    6 -> MenuScreen()
                }
            }
        }

    }

}

