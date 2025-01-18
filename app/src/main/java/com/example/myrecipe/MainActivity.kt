package com.example.myrecipe

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myrecipe.ui.theme.MyRecipeTheme
import kotlinx.parcelize.Parcelize


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController= rememberNavController()
            MyRecipeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding -> RecipeApp(nvx= navController)
                }
            }
        }
    }
}



@Composable
fun RecipeApp(nvx: NavHostController){
    val recipeViewModel: MainViewModel = viewModel()
    val viewstate by recipeViewModel.categoriesState

    NavHost(navController = nvx, startDestination = "recipescreen"){
        composable(route= "recipescreen"){
            RecipeScreen(viewState= viewstate, navigateToDetail = {
                //This part is responsible for passing the data from current
                //screen to the detail screen.
                //It utilizes the savedStateHandle which is a component
                //of the compose navigation framework
                nvx.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                nvx.navigate("detailscreen")
            })
        }

        composable (route= "detailscreen"){
            val cat= nvx.previousBackStackEntry?.
            savedStateHandle?.get<Category>("cat")?:
            Category("","","","")
            CategoryDetailScreen(cat= cat)
        }
    }
}

@Parcelize
data class Category(val idCategory: String,
                    val strCategory: String,
                    val strCategoryThumb: String,
                    val strCategoryDescription: String): Parcelable

data class CategoriesResponse(val categories: List<Category>)

//"idCategory": "1",
//"strCategory": "Beef",
//"strCategoryThumb": "https://www.themealdb.com/images/category/beef.png",
//"strCategoryDescription"





/*
Here’s a step-by-step logical process of how to make the "My Recipe" app:

1. **Define the App's Purpose:**
   - Your app will fetch a list of food categories from an API (like TheMealDB) and
     display it to the user.
   - On selecting a category, it will navigate to a details screen showing more
     information about that category.

2. **Set Up Dependencies:**
   - Add dependencies in your project’s `build.gradle` file. You will need Retrofit
     for API calls, Gson for JSON parsing, and Compose UI for designing the user
     interface.

3. **Create a Retrofit Service:**
   - Set up a Retrofit service to handle API requests. Use `Retrofit.Builder()` to
     define the base URL of the API and add a converter to handle JSON responses.
   - Define an interface that includes methods for making API calls. Use annotations
     like `@GET` to define the endpoint.

4. **Handle the API Response:**
   - Create data classes to map the JSON response to Kotlin objects. For eg, map the
     `Category` object and a `CategoriesResponse` to hold a list of these categories.
   - Make sure the data classes correspond to the JSON structure returned by the API.

5. **Design the UI with Jetpack Compose:**
   - Use `Composable` functions to design the app layout. For example, create a screen
     (`RecipeScreen`) that shows the categories in a grid using `LazyVerticalGrid`.
   - Display images and text for each category using Jetpack Compose elements like
     `Image` and `Text`.

6. **Implement ViewModel for Data Management:**
   - Create a `ViewModel` to manage the app's data, fetch data from the API, and handle
     the app’s state (loading, success, or error).
   - Use Kotlin coroutines to make asynchronous API calls within the `ViewModel`.

7. **Navigate Between Screens:**
   - Set up a navigation component (NavHostController) to manage navigation between
     the category list and detail screens.
   - Pass data (like the selected category) between screens using `savedStateHandle`.

8. **Display Category Details:**
   - Create a details screen (`CategoryDetailScreen`) that will display information
     about a specific category (like its name, description, and thumbnail).
   - Allow navigation from the category list screen to the details screen by passing
     the selected category object.

9. **Handle Permissions and Manifest Configuration:**
   - If the app requires any special permissions, such as internet access, include
     these in the `AndroidManifest.xml`.
   - Ensure the app has the necessary permissions to fetch data from the API.

10. **Test and Refine:**
    - Test the app by running it on an emulator or physical device. Ensure that data
      is fetched correctly and the navigation flows smoothly.
    - Handle any errors or exceptions, especially if the API call fails.

This high-level process helps you understand the flow without focusing on the actual
code implementation but covers all the key steps to create the app.
 */