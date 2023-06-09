package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.R
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.models.getAllArticleCategory
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Categories(
    onFetchCategory: (String) -> Unit,
    viewModel: MainViewModel){

    val tabsItems = getAllArticleCategory()
    Column() {
        LazyRow{
            items(tabsItems.size){
                val category = tabsItems[it]
                CategoryTab(
                    category = category.categoryName,
                    isSelected = viewModel.selectedCategory.collectAsState().value == category,
                    onFetchCategory = onFetchCategory)
            }
        }
        
        ArticleContent(articles = viewModel.getArticleByCategory.collectAsState().value.articles?: listOf())
    }
}

@Composable
fun CategoryTab(category: String, isSelected: Boolean, onFetchCategory: (String) -> Unit){

    val background =
        if(isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.purple_700)
    
    Surface(modifier = Modifier
        .padding(horizontal = 4.dp, vertical = 16.dp)
        .clickable { onFetchCategory(category) },
        shape = MaterialTheme.shapes.small,
        color = background) {
        
        Text(text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(8.dp))
        
    }

}

@Composable
fun ArticleContent(
    articles: List<TopNewsArticle>,
    modifier: Modifier = Modifier){

    LazyColumn{
        items(articles){
            item ->
            Card(
                modifier.padding(8.dp),
                border = BorderStroke(2.dp,
                    color = colorResource(id = R.color.purple_500))) {

                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    CoilImage(
                        imageModel = item.urlToImage,
                        modifier.size(100.dp),
                        placeHolder = painterResource(id = R.drawable.breaking_news),
                        error = painterResource(id = R.drawable.breaking_news))
                    
                    Column(modifier.padding(8.dp)) {
                        Text(
                            text = item.title ?: "Not available",
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis)

                        Text(
                            text = item.author?: "Not Available",
                            modifier.fillMaxWidth(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)

                        Text(
                            text = MockData.stringToDate(item.publishedAt?: "2021-11-10T14:25:20Z").getTimeAgo(),
                            modifier.fillMaxWidth(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)

                    }
                }
            }
        }
    }
}