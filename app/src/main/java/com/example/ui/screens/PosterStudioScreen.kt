package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.PosterTemplateInfo
import com.example.ui.theme.ToheedCardBg
import com.example.ui.theme.ToheedCardBorder
import com.example.ui.theme.ToheedDarkBlue
import com.example.ui.theme.ToheedDeepNight
import com.example.ui.theme.ToheedElectricBlue
import com.example.ui.theme.ToheedGold
import com.example.ui.theme.ToheedGoldBright
import com.example.ui.theme.ToheedNavySurface
import com.example.ui.theme.ToheedPureWhite
import com.example.ui.theme.ToheedTextPrimary
import com.example.ui.theme.ToheedTextSecondary
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.ToheedViewModel

@Composable
fun PosterStudioScreen(
    viewModel: ToheedViewModel,
    language: AppLanguage,
    onNavigate: (Screen) -> Unit
) {
    val context = LocalContext.current
    val posterState by viewModel.posterState.collectAsState()
    val templates = viewModel.posterTemplates

    // Photo Picker for poster photo
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updatePosterImageUri(uri.toString())
            Toast.makeText(context, if (language == AppLanguage.URDU) "تصویر منتخب ہو گئی" else "Photo attached to poster preview!", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ToheedDeepNight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (language == AppLanguage.URDU) "پوسٹر پریویو و لائیو ایڈیٹر" else "Poster Studio & Live Preview",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = ToheedGold
            )
            Text(
                text = if (language == AppLanguage.URDU)
                    "ہیڈ لائن، سب ٹائٹل اور قیمت تبدیل کریں اور اپنی تصویر لگا کر لائیو پریویو دیکھیں۔"
                else
                    "Edit text, upload customer photo, adjust badges, and preview your custom design live.",
                fontSize = 12.sp,
                color = ToheedTextSecondary
            )
        }

        // Template Selector
        item {
            Text(
                text = if (language == AppLanguage.URDU) "ٹیمپلیٹ کا انتخاب کریں:" else "Select Poster Style:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ToheedTextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(templates) { template ->
                    val isSelected = posterState.templateId == template.id
                    Card(
                        modifier = Modifier
                            .clickable { viewModel.selectPosterTemplate(template) }
                            .testTag("template_${template.id}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) ToheedGold else ToheedCardBg
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) ToheedGold else ToheedCardBorder
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = if (language == AppLanguage.URDU) template.nameUr else template.nameEn,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) ToheedDarkBlue else ToheedTextPrimary
                            )
                            Text(
                                text = template.category,
                                fontSize = 10.sp,
                                color = if (isSelected) ToheedDarkBlue.copy(alpha = 0.8f) else ToheedTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // LIVE RENDERED POSTER CANVAS PREVIEW
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("poster_canvas_preview"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF070B14)),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(posterState.primaryColorHex)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Poster Header / Watermark Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(posterState.primaryColorHex)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "TE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ToheedDarkBlue
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = posterState.brandName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(posterState.primaryColorHex)
                            )
                        }

                        // Sale / Promo Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(posterState.primaryColorHex))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = posterState.badgeText.ifEmpty { "EXCLUSIVE" },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = ToheedDarkBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Poster Main Visual Showcase Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 10f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(ToheedNavySurface)
                            .border(1.dp, ToheedCardBorder, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (posterState.customImageUri != null) {
                            AsyncImage(
                                model = posterState.customImageUri,
                                contentDescription = "Customer Custom Poster Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            val sampleImg = when (posterState.templateId) {
                                "video_promo" -> R.drawable.img_video_services
                                else -> R.drawable.img_graphic_services
                            }
                            Image(
                                painter = painterResource(id = sampleImg),
                                contentDescription = "Default Showcase",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Gradient Shade
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )

                        // Floating Price Tag Badge
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(posterState.primaryColorHex))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = posterState.priceTagText.ifEmpty { "Rs. 999" },
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = ToheedDarkBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Poster Headlines & Subtext
                    Text(
                        text = posterState.headlineText.ifEmpty { "POSTER HEADLINE" },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(posterState.primaryColorHex),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = posterState.subtitleText.ifEmpty { "Poster Subtitle & Tagline" },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ToheedPureWhite,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Footer / WhatsApp Hotline Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ToheedCardBg)
                            .padding(vertical = 6.dp, horizontal = 10.dp)
                    ) {
                        Text(
                            text = posterState.footerText.ifEmpty { "TOHEED EDITS • WhatsApp 0317-4329392" },
                            fontSize = 10.sp,
                            color = ToheedTextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // TEXT EDITING CONTROLS
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (language == AppLanguage.URDU) "ٹیکسٹ اور تصویر تبدیل کریں:" else "Customize Poster Content:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ToheedGold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Upload Custom Photo Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                                .testTag("poster_upload_photo_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = ToheedNavySurface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                tint = ToheedGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.URDU) "اپنی تصویر لگائیں" else "Upload Custom Photo",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ToheedGold
                            )
                        }

                        if (posterState.customImageUri != null) {
                            IconButton(
                                onClick = { viewModel.updatePosterImageUri(null) },
                                modifier = Modifier.testTag("poster_clear_photo_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove photo",
                                    tint = Color.Red
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Headline Input
                    OutlinedTextField(
                        value = posterState.headlineText,
                        onValueChange = { viewModel.updatePosterHeadline(it) },
                        label = { Text(if (language == AppLanguage.URDU) "مین ہیڈ لائن" else "Main Headline") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("poster_input_headline"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ToheedGold,
                            unfocusedBorderColor = ToheedCardBorder,
                            focusedTextColor = ToheedTextPrimary,
                            unfocusedTextColor = ToheedTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Subtitle Input
                    OutlinedTextField(
                        value = posterState.subtitleText,
                        onValueChange = { viewModel.updatePosterSubtitle(it) },
                        label = { Text(if (language == AppLanguage.URDU) "سب ٹائٹل" else "Subtitle / Slogan") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("poster_input_subtitle"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ToheedGold,
                            unfocusedBorderColor = ToheedCardBorder,
                            focusedTextColor = ToheedTextPrimary,
                            unfocusedTextColor = ToheedTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Badge Tag
                        OutlinedTextField(
                            value = posterState.badgeText,
                            onValueChange = { viewModel.updatePosterBadge(it) },
                            label = { Text(if (language == AppLanguage.URDU) "بیج" else "Badge") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("poster_input_badge"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ToheedGold,
                                unfocusedBorderColor = ToheedCardBorder,
                                focusedTextColor = ToheedTextPrimary,
                                unfocusedTextColor = ToheedTextPrimary
                            )
                        )

                        // Price Tag
                        OutlinedTextField(
                            value = posterState.priceTagText,
                            onValueChange = { viewModel.updatePosterPriceTag(it) },
                            label = { Text(if (language == AppLanguage.URDU) "قیمت" else "Price Tag") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("poster_input_price_tag"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ToheedGold,
                                unfocusedBorderColor = ToheedCardBorder,
                                focusedTextColor = ToheedTextPrimary,
                                unfocusedTextColor = ToheedTextPrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Color theme selector
                    Text(
                        text = if (language == AppLanguage.URDU) "پوسٹر کا رنگ:" else "Poster Accent Color:",
                        fontSize = 11.sp,
                        color = ToheedTextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        listOf(
                            0xFFFACC15 to "Gold",
                            0xFF38BDF8 to "Electric Blue",
                            0xFFEF4444 to "Flame Red",
                            0xFF10B981 to "Emerald",
                            0xFFFFFFFF to "White"
                        ).forEach { (colorHex, name) ->
                            val isSelected = posterState.primaryColorHex == colorHex
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(colorHex))
                                    .border(
                                        2.dp,
                                        if (isSelected) ToheedPureWhite else Color.Transparent,
                                        CircleShape
                                    )
                                    .clickable { viewModel.updatePosterColor(colorHex) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action: Apply to Order Form
                    Button(
                        onClick = {
                            viewModel.applyPosterToOrder()
                            Toast.makeText(context, if (language == AppLanguage.URDU) "پوسٹر کی معلومات آرڈر فارم میں منتقل ہو گئیں" else "Poster details copied to Order Form!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("poster_apply_order_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = ToheedGold),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = ToheedDarkBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (language == AppLanguage.URDU) "اس ڈیزائن کا آرڈر دیں" else "Order This Custom Poster",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedDarkBlue
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
