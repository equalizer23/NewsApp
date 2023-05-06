package com.example.newsapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.BottomMenuScreen
import com.example.newsapp.MockData
import com.example.newsapp.components.BottomMenu
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.network.Api
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.screen.Categories
import com.example.newsapp.ui.screen.DetailScreen
import com.example.newsapp.ui.screen.Sources
import com.example.newsapp.ui.screen.TopNews
import java.lang.reflect.Modifier

@Composable
fun NewsApp(viewModel: MainViewModel){
    val navController = rememberNavController()
    val scrollState = rememberScrollState()

    MainScreen(navController = navController, scrollState = scrollState, viewModel)
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(Api.retrofitService),
    paddingValues: PaddingValues,
    viewModel: MainViewModel
){

    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles?: listOf())

    articles?.let{
        NavHost(navController = navController, startDestination = BottomMenuScreen.TopNews.route,
            modifier = androidx.compose.ui.Modifier.padding(paddingValues)){

            val queryState = mutableStateOf(viewModel.query.value)

            bottomNavigation(navController = navController, articles, viewModel, queryState)

            composable("Detail/{index}",
                arguments = listOf(navArgument("index"){type = NavType.IntType})){
                val index = it.arguments?.getInt("index")
                index?.let{
                    if(queryState.value != ""){
                        articles.clear()
                        articles.addAll(viewModel.getSearchedArticles.value.articles?: listOf())
                    }
                    else{
                        articles.clear()
                        articles.addAll(viewModel.newsResponse.value.articles?: listOf())
                    }
                    val article = articles[index]
                    DetailScreen(article, scrollState = scrollState, navController = navController)
                }
            }
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState, viewModel: MainViewModel){
    Scaffold(bottomBar = { BottomMenu(navController = navController)}) {

        Navigation(navController, scrollState, paddingValues = it, viewModel = viewModel)
    }
}


fun NavGraphBuilder.bottomNavigation(
    navController: NavHostController,
    articles: List<TopNewsArticle>,
    viewModel: MainViewModel,
    query: MutableState<String>){

    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController, articles, query, viewModel)
    }

    composable(BottomMenuScreen.Categories.route){

        viewModel.onSelectedCategoryChanged("business")
        viewModel.getArticlesByCategory("business")

        Categories(
            onFetchCategory = {
                viewModel.onSelectedCategoryChanged(it)
                viewModel.getArticlesByCategory(it)
            },
            viewModel = viewModel)
    }

    composable(BottomMenuScreen.Source.route){
        Sources(viewModel)
    }
}
