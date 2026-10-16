package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceItem
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
fun ServicesScreen(
    viewModel: ToheedViewModel,
    language: AppLanguage,
    onNavigate: (Screen) -> Unit
) {
    val context = LocalContext.current
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val allServices by viewModel.services.collectAsState()

    val filteredServices = allServices.filter {
        when (selectedCategory) {
            ServiceCategory.ALL -> true
            ServiceCategory.GRAPHIC -> it.category == ServiceCategory.GRAPHIC
            ServiceCategory.VIDEO -> it.category == ServiceCategory.VIDEO
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
            Spacer(modifier = Modifier.height(6.dp))
            // Header
            Text(
                text = if (language == AppLanguage.URDU) "خدمات و قیمت کی فہرست (PKR)" else "Services & Pricing (PKR)",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = ToheedGold
            )
            Text(
                text = if (language == AppLanguage.URDU)
                    "تمام قیمتیں پاکستانی روپے میں ہیں اور ایڈمن ڈیش بورڈ سے تبدیل کی جا سکتی ہیں۔"
                else
                    "All rates in Pakistani Rupees (PKR). Transparent pricing with unlimited satisfaction revisions.",
                fontSize = 12.sp,
                color = ToheedTextSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Category Filter Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(ServiceCategory.values()) { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectCategory(category) },
                        label = {
                            Text(
                                text = if (language == AppLanguage.URDU) category.labelUr else category.labelEn,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ToheedGold,
                            selectedLabelColor = ToheedDarkBlue,
                            containerColor = ToheedCardBg,
                            labelColor = ToheedTextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) ToheedGold else ToheedCardBorder
                        ),
                        modifier = Modifier.testTag("filter_chip_${category.name}")
                    )
                }
            }
        }

        // Service Cards List
        items(filteredServices) { service ->
            ServiceCard(
                service = service,
                language = language,
                onOrder = {
                    viewModel.startOrderForService(service)
                },
                onWhatsAppInquire = {
                    viewModel.openWhatsAppForService(context, service)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ServiceCard(
    service: ServiceItem,
    language: AppLanguage,
    onOrder: () -> Unit,
    onWhatsAppInquire: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("service_card_${service.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
        border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Badge & Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (service.category == ServiceCategory.GRAPHIC)
                                    ToheedGold.copy(alpha = 0.2f)
                                else
                                    ToheedElectricBlue.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (service.category == ServiceCategory.GRAPHIC) Icons.Default.Brush else Icons.Default.Movie,
                            contentDescription = null,
                            tint = if (service.category == ServiceCategory.GRAPHIC) ToheedGold else ToheedElectricBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (language == AppLanguage.URDU) service.category.labelUr else service.category.labelEn,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ToheedTextSecondary
                    )
                }

                if (service.popularTag != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(ToheedGold)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = service.popularTag,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ToheedDarkBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Service Title & Urdu Translation
            Text(
                text = if (language == AppLanguage.URDU) service.titleUr else service.titleEn,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ToheedTextPrimary
            )
            if (language != AppLanguage.URDU) {
                Text(
                    text = service.titleUr,
                    fontSize = 12.sp,
                    color = ToheedGold.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Description
            Text(
                text = if (language == AppLanguage.URDU) service.descriptionUr else service.descriptionEn,
                fontSize = 12.sp,
                color = ToheedTextSecondary,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price & Delivery Info Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(ToheedCardBg)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (language == AppLanguage.URDU) "قیمت (پاکستانی روپے)" else "Starting Price",
                        fontSize = 10.sp,
                        color = ToheedTextSecondary
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "Rs. ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                        Text(
                            text = String.format("%,d", service.pricePkr),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ToheedGold
                        )
                        Text(
                            text = " PKR",
                            fontSize = 10.sp,
                            color = ToheedTextSecondary,
                            modifier = Modifier.padding(bottom = 2.dp, start = 2.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        tint = ToheedElectricBlue,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = service.turnaroundTime,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = ToheedTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onOrder,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .testTag("order_btn_${service.id}"),
                    colors = ButtonDefaults.buttonColors(containerColor = ToheedGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null,
                        tint = ToheedDarkBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (language == AppLanguage.URDU) "آرڈر دیں" else "Order Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = ToheedDarkBlue
                    )
                }

                OutlinedButton(
                    onClick = onWhatsAppInquire,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .testTag("whatsapp_btn_${service.id}"),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = WhatsAppGreen),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WhatsAppGreen),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = WhatsAppGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (language == AppLanguage.URDU) "واٹس ایپ بات" else "WhatsApp",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = WhatsAppGreen
                    )
                }
            }
        }
    }
}
