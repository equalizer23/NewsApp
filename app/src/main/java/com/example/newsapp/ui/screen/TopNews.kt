package com.example.newsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.NewsData
import com.example.newsapp.components.SearchBar
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(
    navController: NavController,
    articles: List<TopNewsArticle>,
    query: MutableState<String>,
    viewModel: MainViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar(query = query, viewModel)

        val searchedText = query.value
        val resultsList = mutableListOf<TopNewsArticle>()
        if (searchedText != ""){
            resultsList.addAll(viewModel.getSearchedArticles.collectAsState().value.articles ?: articles)
        }
        else{
            resultsList.addAll(articles)
        }

        LazyColumn{
            items(articles.size){ index ->
                TopNewsItem(
                    article = resultsList[index],
                    onNewsClick = {navController.navigate("Detail/$index")})
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClick: ()-> Unit){
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }){
        CoilImage(imageModel = article.urlToImage,
            contentDescription = "Image",
            contentScale = ContentScale.Crop
        )
        
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween) {

            article.publishedAt?.let{
                Text(text = MockData.stringToDate(article.publishedAt).getTimeAgo(),
                    color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(80.dp))

            article.title?.let{
                Text(text = article.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold)

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview(){

}