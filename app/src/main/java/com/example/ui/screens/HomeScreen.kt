package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.R
import com.example.data.model.AppLanguage
import com.example.ui.components.WhatsAppFloatingBanner
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
fun HomeScreen(
    viewModel: ToheedViewModel,
    language: AppLanguage,
    onNavigate: (Screen) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ToheedDeepNight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Hero Profile Banner with Toheed at his computer
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ToheedCardBorder, RoundedCornerShape(20.dp))
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.toheed_profile_banner),
                            contentDescription = "Toheed Edits Creative Studio",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            ToheedDarkBlue.copy(alpha = 0.85f),
                                            ToheedDarkBlue
                                        ),
                                        startY = 200f
                                    )
                                )
                        )

                        // Top Badge
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.TopStart),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ToheedGold)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "TOHEED EDITS",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ToheedDarkBlue
                                )
                            }
                        }
                    }

                    // Profile Bio Info
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = if (language == AppLanguage.URDU) "اب آپ کا ہر پروجیکٹ ہو گا پروفیشنل !" else "Your Vision, My Design",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ToheedGold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == AppLanguage.URDU)
                                "پروفیشنل گرافک ڈیزائننگ اور ویڈیو ایڈیٹنگ سروسز۔ معیاری کام، مناسب قیمت اور تیز ترین ڈیلیوری۔"
                            else
                                "Professional Graphic Designing & Video Editing in Pakistan. High quality, affordable PKR rates, and fast turnaround.",
                            fontSize = 13.sp,
                            color = ToheedTextSecondary,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Software badges
                        Text(
                            text = if (language == AppLanguage.URDU) "مہارت اور ٹولز:" else "Software Mastery:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ToheedElectricBlue
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf("Ps", "Ai", "Pr", "Ae", "Canva", "InPage").forEach { tag ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(ToheedCardBg)
                                        .border(1.dp, ToheedCardBorder, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 7.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = tag,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (tag in listOf("Ps", "Ai", "Pr", "Ae")) ToheedElectricBlue else ToheedGold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onNavigate(Screen.PlaceOrder) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("home_order_now_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = ToheedGold),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = if (language == AppLanguage.URDU) "آرڈر بک کریں" else "Book Order",
                                    color = ToheedDarkBlue,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            Button(
                                onClick = { viewModel.openWhatsApp(context) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("home_whatsapp_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = if (language == AppLanguage.URDU) "واٹس ایپ" else "WhatsApp",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Pakistani Brand Guarantees
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedNavySurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (language == AppLanguage.URDU) "ہماری خصوصیات و ضمانت" else "Why Choose TOHEED EDITS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ToheedGold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        GuaranteePill(titleUr = "معیاری کام", titleEn = "Quality Work")
                        GuaranteePill(titleUr = "مناسب قیمت", titleEn = "Fair Price")
                        GuaranteePill(titleUr = "وقت پر ڈیلیوری", titleEn = "On Time")
                        GuaranteePill(titleUr = "100% اطمینان", titleEn = "100% Satisfied")
                    }
                }
            }
        }

        // Fast Quick-Feature Access Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Graphic Design Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigate(Screen.Services) }
                        .testTag("quick_card_graphic"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ToheedCardBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(ToheedGold.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Brush,
                                contentDescription = null,
                                tint = ToheedGold,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (language == AppLanguage.URDU) "گرافک ڈیزائن" else "Graphic Design",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedTextPrimary
                        )
                        Text(
                            text = if (language == AppLanguage.URDU) "لوگو، پوسٹر، تھمب نیل" else "Logos, Posters, Banners",
                            fontSize = 11.sp,
                            color = ToheedTextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "From Rs. 1,200",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                    }
                }

                // Video Editing Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigate(Screen.Services) }
                        .testTag("quick_card_video"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ToheedCardBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(ToheedElectricBlue.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Videocam,
                                contentDescription = null,
                                tint = ToheedElectricBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (language == AppLanguage.URDU) "ویڈیو ایڈیٹنگ" else "Video Editing",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedTextPrimary
                        )
                        Text(
                            text = if (language == AppLanguage.URDU) "ریلز، اشتہار، یوٹیوب" else "Reels, Shorts, Ads",
                            fontSize = 11.sp,
                            color = ToheedTextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "From Rs. 2,500",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedElectricBlue
                        )
                    }
                }
            }
        }

        // Interactive Poster Studio Callout
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(Screen.PosterEditor) }
                    .testTag("card_poster_lab"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(ToheedGold, Color(0xFFD97706))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = ToheedDarkBlue,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (language == AppLanguage.URDU) "لائیو پوسٹر پریویو لیب" else "Live Poster Preview Studio",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                        Text(
                            text = if (language == AppLanguage.URDU)
                                "برگر، شوز اور تھمب نیلز پر اپنے نام اور تصویر کے ساتھ لائیو ٹیسٹ کریں!"
                            else
                                "Customize headlines, prices, and upload your photo to preview live posters!",
                            fontSize = 11.sp,
                            color = ToheedTextSecondary
                        )
                    }
                    Button(
                        onClick = { onNavigate(Screen.PosterEditor) },
                        colors = ButtonDefaults.buttonColors(containerColor = ToheedGold),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (language == AppLanguage.URDU) "ٹرائی کریں" else "Try Now",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedDarkBlue
                        )
                    }
                }
            }
        }

        // Visual Showcase Section
        item {
            Column {
                Text(
                    text = if (language == AppLanguage.URDU) "ہمارے حالیہ پروجیکٹس" else "Featured Creative Work",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = ToheedGold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ToheedNavySurface)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.img_graphic_services),
                            contentDescription = "Graphic Design Samples",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4f / 3f),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Burger Ads, Sneakers & Viral Thumbnails",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ToheedTextPrimary
                            )
                            Text(
                                text = "100% Vector Quality",
                                fontSize = 11.sp,
                                color = ToheedGold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ToheedNavySurface)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.img_video_services),
                            contentDescription = "Video Editing Samples",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4f / 3f),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Premiere & After Effects Video Editing",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ToheedTextPrimary
                            )
                            Text(
                                text = "4K / 60fps Reels",
                                fontSize = 11.sp,
                                color = ToheedElectricBlue
                            )
                        }
                    }
                }
            }
        }

        // WhatsApp Floating Banner at bottom
        item {
            WhatsAppFloatingBanner(
                language = language,
                onClick = { viewModel.openWhatsApp(context) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun GuaranteePill(titleUr: String, titleEn: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 2.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = ToheedGold,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = titleUr,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = ToheedPureWhite,
            textAlign = TextAlign.Center
        )
        Text(
            text = titleEn,
            fontSize = 9.sp,
            color = ToheedTextSecondary,
            textAlign = TextAlign.Center
        )
    }
}
