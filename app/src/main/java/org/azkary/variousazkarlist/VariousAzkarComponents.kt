package org.azkary.variousazkarlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.azkary.R
import org.azkary.data.model.Zikr


@Composable
fun VariousAzkarList(
    variousAzkar: List<Zikr>,
    modifier: Modifier = Modifier,
    onClickListener: (Int) -> Unit
) {

    LazyColumn(
        modifier
            .fillMaxSize()
    ) {
        itemsIndexed(variousAzkar) { index, zikr ->
            if (index == variousAzkar.lastIndex) {
                VariousZikrItem(zikr, showDivider = false) {
                    onClickListener(index)
                }
            } else {
                VariousZikrItem(zikr, showDivider = true) {
                    onClickListener(index)
                }
            }
        }
    }
}

@Composable
private fun VariousZikrItem(zikr: Zikr, showDivider: Boolean, modifier: Modifier = Modifier, onClickListener: () -> Unit) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClickListener()
            }
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {

        // Create references for the composables to constrain
        val (zikrText, image) = createRefs()

        Text(
            text = zikr.name,
            textAlign = TextAlign.Right,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.constrainAs(zikrText) {
                start.linkTo(parent.start)
                end.linkTo(image.start, 8.dp)
                width = Dimension.fillToConstraints
            }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_points_to_layout_end),
            contentDescription = "Go to Azkar Details.",
            modifier = Modifier
                .constrainAs(image) {
                    end.linkTo(parent.absoluteLeft)
                    centerVerticallyTo(parent)
                }
                .rotate(180f)
                .height(25.dp)
        )
    }
    if (showDivider) {
        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 2.dp
        )
    }
}