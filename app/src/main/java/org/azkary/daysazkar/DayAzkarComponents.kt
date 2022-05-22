package org.azkary.daysazkar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import org.azkary.R
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.azkary.data.model.Category


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DayAzkarList(
    azkarCategories: List<Category>,
    modifier: Modifier = Modifier,
    onClickListener: (Category) -> Unit
) {

    LazyVerticalGrid(
        cells = GridCells.Adaptive(150.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(azkarCategories) { index, category ->
            if (index == azkarCategories.lastIndex) {
                DayZikrItem(category) {
                    onClickListener(azkarCategories[index])
                }
            } else {
                DayZikrItem(category) {
                    onClickListener(azkarCategories[index])
                }
            }
        }
    }
}

@Composable
private fun DayZikrItem(
    category: Category,
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary, shape = RoundedCornerShape(8.dp))
            .clickable {
                onClickListener()
            }
            .padding(vertical = 5.dp)
    ) {

        // Create references for the composables to constrain
        val (categoryName, image) = createRefs()


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.categoryImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Go to Azkar Details.",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(categoryName.top)
                    centerHorizontallyTo(parent)
                }
        )

        Text(
            text = category.categoryName,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 2,
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(categoryName) {
                bottom.linkTo(parent.bottom)
                centerHorizontallyTo(parent)
            }
        )
    }
}