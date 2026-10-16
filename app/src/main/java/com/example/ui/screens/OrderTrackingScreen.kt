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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.AppLanguage
import com.example.data.model.OrderStatus
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
import com.example.ui.viewmodel.ToheedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderTrackingScreen(
    viewModel: ToheedViewModel,
    language: AppLanguage
) {
    val context = LocalContext.current
    val searchQuery by viewModel.trackingSearchQuery.collectAsState()
    val trackedOrder by viewModel.trackedOrder.collectAsState()
    val errorMessage by viewModel.trackingErrorMessage.collectAsState()

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
                text = if (language == AppLanguage.URDU) "آرڈر کی لائیو ٹریکنگ" else "Track Your Order",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = ToheedGold
            )
            Text(
                text = if (language == AppLanguage.URDU)
                    "اپنا آرڈر نمبر (مثال: TE-1234) یا فون نمبر درج کر کے کام کی لائیو پروگریس دیکھیں۔"
                else
                    "Enter your Order ID (e.g. TE-1234) or phone number to check current design/video status.",
                fontSize = 12.sp,
                color = ToheedTextSecondary
            )
        }

        // Search Box
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.updateTrackingQuery(it) },
                            placeholder = { Text(if (language == AppLanguage.URDU) "آرڈر کوڈ یا فون نمبر..." else "e.g. TE-4821 or 0317...") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("tracking_input"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ToheedGold,
                                unfocusedBorderColor = ToheedCardBorder,
                                focusedTextColor = ToheedTextPrimary,
                                unfocusedTextColor = ToheedTextPrimary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { viewModel.trackOrder() },
                            modifier = Modifier
                                .height(56.dp)
                                .testTag("track_search_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = ToheedGold),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Track",
                                tint = ToheedDarkBlue
                            )
                        }
                    }

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage ?: "",
                            color = Color(0xFFF87171),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Tracked Order Details
        if (trackedOrder != null) {
            val order = trackedOrder!!
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold),
                    modifier = Modifier.fillMaxWidth().testTag("tracked_order_details")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Order ID: ${order.id}",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ToheedGold
                                )
                                Text(
                                    text = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(order.timestamp)),
                                    fontSize = 11.sp,
                                    color = ToheedTextSecondary
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ToheedCardBg)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (language == AppLanguage.URDU) order.status.labelUr else order.status.labelEn,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (order.status == OrderStatus.COMPLETED || order.status == OrderStatus.DELIVERED) ToheedGold else ToheedElectricBlue
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Visual Step Progress Indicator
                        Text(
                            text = if (language == AppLanguage.URDU) "آرڈر کی لائیو پروگریس:" else "Live Order Progress:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedTextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val allStatuses = OrderStatus.values()
                        val currentStep = order.status.stepIndex

                        allStatuses.forEachIndexed { index, status ->
                            val isCompleted = index <= currentStep
                            val isCurrent = index == currentStep

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isCompleted) ToheedGold else ToheedCardBg
                                        )
                                        .border(
                                            1.dp,
                                            if (isCompleted) ToheedGold else ToheedCardBorder,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isCompleted) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = ToheedDarkBlue,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = if (language == AppLanguage.URDU) status.labelUr else status.labelEn,
                                    fontSize = 12.sp,
                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isCurrent) ToheedGold else if (isCompleted) ToheedTextPrimary else ToheedTextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Client and Service details
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(ToheedCardBg)
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Client: ${order.clientName}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ToheedTextPrimary
                                    )
                                    Text(
                                        text = "Rs. ${order.pricePkr} PKR",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ToheedGold
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Service: ${order.serviceTitle}",
                                    fontSize = 11.sp,
                                    color = ToheedTextSecondary
                                )
                                Text(
                                    text = "Urgency: ${order.urgency}",
                                    fontSize = 11.sp,
                                    color = ToheedElectricBlue
                                )
                                if (order.customNotes.isNotEmpty()) {
                                    Text(
                                        text = "Notes: ${order.customNotes}",
                                        fontSize = 11.sp,
                                        color = ToheedTextSecondary
                                    )
                                }
                            }
                        }

                        // Reference Photo Preview if attached
                        if (order.photoUri != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (language == AppLanguage.URDU) "منسلک ریفرنس تصویر:" else "Attached Reference Image:",
                                fontSize = 11.sp,
                                color = ToheedTextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ToheedCardBg)
                            ) {
                                AsyncImage(
                                    model = order.photoUri,
                                    contentDescription = "Order reference photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Contact WhatsApp for revisions or questions
                        Button(
                            onClick = {
                                val msg = "Assalam-o-Alaikum Toheed Edits!\nI am checking progress on my Order ID: ${order.id}\nService: ${order.serviceTitle}\nPlease update me."
                                viewModel.openWhatsApp(context, msg)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.URDU) "اس آرڈر کے متعلق واٹس ایپ پر بات کریں" else "Discuss Order on WhatsApp",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
