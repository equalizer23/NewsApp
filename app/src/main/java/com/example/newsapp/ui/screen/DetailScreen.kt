package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.NewsData
import com.example.newsapp.R
import com.example.newsapp.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState, navController: NavController){
    
    Scaffold(topBar = { DetailTopBar(onBackPressed = {navController.popBackStack()})}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally){

            Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
            CoilImage(imageModel = article.urlToImage,
                contentDescription = "Image",
                contentScale = ContentScale.Crop)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween){

                InfoWithIcon(image = Icons.Default.Edit, info = article.author?: "Not available")
                InfoWithIcon(image = Icons.Default.DateRange,
                    info = MockData.stringToDate(article.publishedAt!!).getTimeAgo())
            }

            Text(text = article.title?: "Not available", fontWeight = FontWeight.Bold)
            Text(text = article.description?: "Not available", modifier = Modifier.padding(top = 16.dp))
        }
    }
    

}

@Composable
fun InfoWithIcon(image: ImageVector, info: String){
    Row(){
        Icon(image, contentDescription = "Author", modifier = Modifier.padding(end = 8.dp),
        colorResource(id = R.color.purple_500))
        
        Text(text = info)
    }
}

@Composable
fun DetailTopBar(onBackPressed: ()-> Unit = {}){
    TopAppBar(
        title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)},
        navigationIcon = {
            IconButton(onClick = {onBackPressed}){
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(){
    
}