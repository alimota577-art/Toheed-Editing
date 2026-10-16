package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush as UiBrush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToheedTopAppBar(
    language: AppLanguage,
    onToggleLanguage: () -> Unit,
    onOpenWhatsApp: () -> Unit,
    onOpenAdmin: () -> Unit,
    onOpenGuide: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { }
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            UiBrush.linearGradient(
                                colors = listOf(ToheedGold, Color(0xFFB45309))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "TE",
                        fontWeight = FontWeight.Black,
                        fontSize = 17.sp,
                        color = ToheedDarkBlue
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "TOHEED EDITS",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            color = ToheedGold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(ToheedCardBorder)
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "PK",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ToheedElectricBlue
                            )
                        }
                    }
                    Text(
                        text = if (language == AppLanguage.URDU) "گرافک و ویڈیو ڈیزائننگ" else "Graphic & Video Studio",
                        fontSize = 11.sp,
                        color = ToheedTextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        actions = {
            // Language Switcher
            OutlinedButton(
                onClick = onToggleLanguage,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .height(34.dp)
                    .testTag("lang_toggle_btn"),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ToheedGold
                ),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold.copy(alpha = 0.6f)),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = "Language",
                    modifier = Modifier.size(15.dp),
                    tint = ToheedGold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (language == AppLanguage.ENGLISH) "اردو" else "English",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Quick WhatsApp Button
            IconButton(
                onClick = onOpenWhatsApp,
                modifier = Modifier.testTag("top_whatsapp_btn")
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(WhatsAppGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "WhatsApp",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Guide Button
            IconButton(
                onClick = onOpenGuide,
                modifier = Modifier.testTag("top_guide_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.HelpOutline,
                    contentDescription = "Play Store & Web Guide",
                    tint = ToheedTextSecondary
                )
            }

            // Admin Button
            IconButton(
                onClick = onOpenAdmin,
                modifier = Modifier.testTag("top_admin_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = "Admin Dashboard",
                    tint = ToheedGold
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ToheedDarkBlue,
            titleContentColor = ToheedTextPrimary
        )
    )
}

@Composable
fun ToheedBottomNavigationBar(
    currentScreen: Screen,
    language: AppLanguage,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = ToheedDarkBlue,
        contentColor = ToheedTextPrimary,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple(Screen.Home, Icons.Default.Home, if (language == AppLanguage.URDU) "ہوم" else "Home"),
            Triple(Screen.Services, Icons.Default.DesignServices, if (language == AppLanguage.URDU) "خدمات" else "Services"),
            Triple(Screen.PosterEditor, Icons.Default.Brush, if (language == AppLanguage.URDU) "پوسٹر لیب" else "Poster Lab"),
            Triple(Screen.PlaceOrder, Icons.Default.ShoppingBag, if (language == AppLanguage.URDU) "آرڈر دیں" else "Order"),
            Triple(Screen.OrderTracking, Icons.Default.TrackChanges, if (language == AppLanguage.URDU) "ٹریکنگ" else "Track")
        )

        items.forEach { (screen, icon, label) ->
            val isSelected = currentScreen == screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ToheedDarkBlue,
                    unselectedIconColor = ToheedTextSecondary,
                    selectedTextColor = ToheedGold,
                    unselectedTextColor = ToheedTextSecondary,
                    indicatorColor = ToheedGold
                ),
                modifier = Modifier.testTag("nav_item_${screen.route}")
            )
        }
    }
}

@Composable
fun WhatsAppFloatingBanner(
    phoneDisplay: String = "0317 4329392",
    language: AppLanguage,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("whatsapp_action_banner"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = WhatsAppGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "WhatsApp",
                        tint = WhatsAppGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (language == AppLanguage.URDU) "ابھی واٹس ایپ پر رابطہ کریں" else "Chat Directly on WhatsApp",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "$phoneDisplay • Quick Reply in 5 Mins",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Open Chat",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
