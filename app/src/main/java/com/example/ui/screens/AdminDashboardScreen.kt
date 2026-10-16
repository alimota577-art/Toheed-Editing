package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.AppLanguage
import com.example.data.model.OrderItem
import com.example.data.model.OrderStatus
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
import com.example.ui.viewmodel.ToheedViewModel

@Composable
fun AdminDashboardScreen(
    viewModel: ToheedViewModel,
    language: AppLanguage
) {
    val context = LocalContext.current
    val isAuthenticated by viewModel.isAdminAuthenticated.collectAsState()
    val pinInput by viewModel.adminPinInput.collectAsState()
    val pinError by viewModel.adminPinError.collectAsState()
    val allOrders by viewModel.allOrders.collectAsState()
    val services by viewModel.services.collectAsState()

    if (!isAuthenticated) {
        // PIN Authentication Screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ToheedDeepNight)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_login_card")
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(ToheedGold.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = ToheedGold,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (language == AppLanguage.URDU) "محفوظ ایڈمن پورٹل" else "Secure Admin Portal",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ToheedGold
                    )

                    Text(
                        text = if (language == AppLanguage.URDU)
                            "ایڈمن کنٹرول کے لیے 4 ہندسوں کا پن درج کریں (ڈیفالٹ: 7860)"
                        else
                            "Enter master PIN to manage prices and customer orders (Default: 7860)",
                        fontSize = 12.sp,
                        color = ToheedTextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = { viewModel.setAdminPinInput(it) },
                        placeholder = { Text("Enter 4-digit PIN") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_pin_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ToheedGold,
                            unfocusedBorderColor = ToheedCardBorder,
                            focusedTextColor = ToheedTextPrimary,
                            unfocusedTextColor = ToheedTextPrimary
                        )
                    )

                    if (pinError != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = pinError ?: "",
                            color = Color(0xFFF87171),
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.verifyAdminPin() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("admin_unlock_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = ToheedGold),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = if (language == AppLanguage.URDU) "لاگ ان کریں" else "Unlock Dashboard",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedDarkBlue
                        )
                    }
                }
            }
        }
        return
    }

    // AUTHENTICATED ADMIN DASHBOARD
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ToheedDeepNight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (language == AppLanguage.URDU) "ایڈمن ڈیش بورڈ (توحید ایڈیٹس)" else "Admin Dashboard (Toheed Edits)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ToheedGold
                    )
                    Text(
                        text = if (language == AppLanguage.URDU) "قیمتیں تبدیل کریں اور گاہکوں کے آرڈرز کا جائزہ لیں۔" else "Manage PKR rates and customer orders live.",
                        fontSize = 11.sp,
                        color = ToheedTextSecondary
                    )
                }

                OutlinedButton(
                    onClick = { viewModel.adminLogout() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFF87171)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF87171).copy(alpha = 0.5f)),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("admin_logout_btn")
                ) {
                    Text(text = "Lock", fontSize = 11.sp)
                }
            }
        }

        // Summary Statistics
        item {
            val totalRevenue = allOrders.sumOf { it.pricePkr }
            val activeOrdersCount = allOrders.count { it.status != OrderStatus.DELIVERED }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = if (language == AppLanguage.URDU) "کل آرڈرز" else "Total Orders",
                    value = allOrders.size.toString(),
                    color = ToheedGold,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = if (language == AppLanguage.URDU) "جاری کام" else "Active Orders",
                    value = activeOrdersCount.toString(),
                    color = ToheedElectricBlue,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = if (language == AppLanguage.URDU) "کل مالیت" else "Revenue",
                    value = "Rs. ${totalRevenue / 1000}k",
                    color = WhatsAppGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // SECTION: EDITABLE PRICES IN PKR
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder),
                modifier = Modifier.fillMaxWidth().testTag("admin_price_management_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (language == AppLanguage.URDU) "سروسز کی قیمتیں ایڈٹ کریں (PKR)" else "Edit Service Prices (PKR)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                        Text(
                            text = "Auto-Saved to Room DB",
                            fontSize = 10.sp,
                            color = ToheedTextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    services.forEach { service ->
                        EditablePriceRow(
                            service = service,
                            onSaveNewPrice = { newPrice ->
                                viewModel.updateServicePriceAdmin(service.id, newPrice)
                                Toast.makeText(context, "${service.titleEn} updated to Rs. $newPrice PKR", Toast.LENGTH_SHORT).show()
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // SECTION: CUSTOMER ORDERS MANAGEMENT
        item {
            Text(
                text = if (language == AppLanguage.URDU) "گاہکوں کے موصول شدہ آرڈرز (${allOrders.size})" else "Customer Orders (${allOrders.size})",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = ToheedGold
            )
        }

        if (allOrders.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ToheedCardBg),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (language == AppLanguage.URDU) "ابھی تک کوئی آرڈر موصول نہیں ہوا۔" else "No orders placed yet. Customer submissions will appear here live.",
                            color = ToheedTextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
            items(allOrders) { order ->
                AdminOrderCard(
                    order = order,
                    language = language,
                    onUpdateStatus = { newStatus ->
                        viewModel.updateOrderStatusAdmin(order.id, newStatus)
                    },
                    onDeleteOrder = {
                        viewModel.deleteOrderAdmin(order.id)
                    },
                    onWhatsAppCustomer = {
                        val msg = "Assalam-o-Alaikum ${order.clientName}!\nRegarding your Order ID: ${order.id} for ${order.serviceTitle} at Toheed Edits."
                        viewModel.openWhatsApp(context, msg)
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ToheedCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 10.sp, color = ToheedTextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Black, color = color)
        }
    }
}

@Composable
private fun EditablePriceRow(
    service: ServiceItem,
    onSaveNewPrice: (Int) -> Unit
) {
    var priceText by remember(service.pricePkr) { mutableStateOf(service.pricePkr.toString()) }
    var isEditing by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ToheedCardBg)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = service.titleEn,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ToheedTextPrimary
            )
            Text(
                text = service.category.labelEn,
                fontSize = 10.sp,
                color = ToheedTextSecondary
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isEditing) {
                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    singleLine = true,
                    modifier = Modifier.width(90.dp).height(48.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ToheedGold,
                        unfocusedBorderColor = ToheedCardBorder,
                        focusedTextColor = ToheedTextPrimary,
                        unfocusedTextColor = ToheedTextPrimary
                    )
                )
                IconButton(
                    onClick = {
                        val num = priceText.toIntOrNull()
                        if (num != null && num > 0) {
                            onSaveNewPrice(num)
                            isEditing = false
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Save", tint = ToheedGold)
                }
            } else {
                Text(
                    text = "Rs. ${service.pricePkr}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ToheedGold,
                    modifier = Modifier.padding(end = 4.dp)
                )
                IconButton(onClick = { isEditing = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = ToheedTextSecondary, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun AdminOrderCard(
    order: OrderItem,
    language: AppLanguage,
    onUpdateStatus: (OrderStatus) -> Unit,
    onDeleteOrder: () -> Unit,
    onWhatsAppCustomer: () -> Unit
) {
    var statusMenuExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
        border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder),
        modifier = Modifier.fillMaxWidth().testTag("admin_order_${order.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ID: ${order.id}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp,
                        color = ToheedGold
                    )
                    Text(
                        text = "${order.clientName} • ${order.clientPhone}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ToheedTextPrimary
                    )
                }

                // Status Dropdown Button
                Box {
                    Button(
                        onClick = { statusMenuExpanded = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ToheedNavySurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = order.status.labelEn,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                    }

                    DropdownMenu(
                        expanded = statusMenuExpanded,
                        onDismissRequest = { statusMenuExpanded = false },
                        modifier = Modifier.background(ToheedNavySurface)
                    ) {
                        OrderStatus.values().forEach { st ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${st.labelEn} (${st.labelUr})",
                                        color = if (st == order.status) ToheedGold else ToheedTextPrimary,
                                        fontSize = 12.sp
                                    )
                                },
                                onClick = {
                                    onUpdateStatus(st)
                                    statusMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Service: ${order.serviceTitle} • Rs. ${order.pricePkr} PKR (${order.urgency})",
                fontSize = 12.sp,
                color = ToheedElectricBlue
            )

            if (order.customNotes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Notes: ${order.customNotes}",
                    fontSize = 11.sp,
                    color = ToheedTextSecondary
                )
            }

            // Attached photo if present
            if (order.photoUri != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ToheedCardBg)
                ) {
                    AsyncImage(
                        model = order.photoUri,
                        contentDescription = "Customer Photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onWhatsAppCustomer,
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "WhatsApp Client", fontSize = 11.sp, color = Color.White)
                }

                IconButton(onClick = onDeleteOrder) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete order", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
