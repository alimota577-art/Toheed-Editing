package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.AppLanguage
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceOrderScreen(
    viewModel: ToheedViewModel,
    language: AppLanguage,
    onNavigate: (Screen) -> Unit
) {
    val context = LocalContext.current
    val services by viewModel.services.collectAsState()
    val clientName by viewModel.orderClientName.collectAsState()
    val clientPhone by viewModel.orderClientPhone.collectAsState()
    val selectedService by viewModel.orderSelectedService.collectAsState()
    val urgency by viewModel.orderUrgency.collectAsState()
    val customNotes by viewModel.orderCustomNotes.collectAsState()
    val photoUri by viewModel.orderPhotoUri.collectAsState()
    val successOrder by viewModel.orderSuccessItem.collectAsState()

    var serviceDropdownExpanded by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setOrderPhotoUri(uri.toString())
            Toast.makeText(context, if (language == AppLanguage.URDU) "فائل یا تصویر اپلوڈ ہو گئی" else "Reference file attached!", Toast.LENGTH_SHORT).show()
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
                text = if (language == AppLanguage.URDU) "نیا آرڈر درج کریں" else "Place an Order",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = ToheedGold
            )
            Text(
                text = if (language == AppLanguage.URDU)
                    "اپنی معلومات، سروس کا انتخاب اور ریفرنس فائل اپلوڈ کریں۔ آرڈر کوڈ خود بخود تیار ہو جائے گا۔"
                else
                    "Select service, upload your product/reference photos, and submit. An instant Order ID is created.",
                fontSize = 12.sp,
                color = ToheedTextSecondary
            )
        }

        // Success Card if submitted
        if (successOrder != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("order_success_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                    border = androidx.compose.foundation.BorderStroke(2.dp, ToheedGold)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(WhatsAppGreen.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = WhatsAppGreen,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (language == AppLanguage.URDU) "آرڈر کامیابی سے محفوظ ہو گیا!" else "Order Successfully Created!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )

                        Text(
                            text = "Order ID: ${successOrder?.id}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ToheedPureWhite,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Text(
                            text = if (language == AppLanguage.URDU)
                                "ہم نے آرڈر کی تفصیلات واٹس ایپ پر تیار کر دی ہیں۔ آپ کسی بھی وقت اپنے آرڈر کوڈ سے ٹریک کر سکتے ہیں۔"
                            else
                                "Your order is logged. You can track progress anytime using your Order ID.",
                            fontSize = 12.sp,
                            color = ToheedTextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    successOrder?.let { viewModel.openWhatsAppForOrder(context, it) }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .testTag("success_whatsapp_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = if (language == AppLanguage.URDU) "واٹس ایپ کھولیں" else "Send on WhatsApp",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }

                            OutlinedButton(
                                onClick = { onNavigate(Screen.OrderTracking) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .testTag("success_track_btn"),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = ToheedGold),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = if (language == AppLanguage.URDU) "آرڈر ٹریک کریں" else "Track Order",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = ToheedGold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { viewModel.resetOrderForm() },
                            colors = ButtonDefaults.buttonColors(containerColor = ToheedCardBg),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = if (language == AppLanguage.URDU) "ایک اور آرڈر دیں" else "Place Another Order",
                                fontSize = 11.sp,
                                color = ToheedTextSecondary
                            )
                        }
                    }
                }
            }
        } else {
            // ORDER FORM
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Service Dropdown
                        Text(
                            text = if (language == AppLanguage.URDU) "1. سروس منتخب کریں *" else "1. Select Service *",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Box(modifier = Modifier.fillMaxWidth()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { serviceDropdownExpanded = true }
                                    .testTag("service_dropdown_trigger"),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = ToheedCardBg),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = selectedService?.titleEn ?: (if (language == AppLanguage.URDU) "سروس منتخب کرنے کے لیے ٹیپ کریں" else "Tap to choose a service..."),
                                            fontSize = 14.sp,
                                            fontWeight = if (selectedService != null) FontWeight.Bold else FontWeight.Normal,
                                            color = if (selectedService != null) ToheedTextPrimary else ToheedTextSecondary
                                        )
                                        if (selectedService != null) {
                                            Text(
                                                text = "Rate: Rs. ${selectedService?.pricePkr} PKR • ${selectedService?.turnaroundTime}",
                                                fontSize = 11.sp,
                                                color = ToheedGold
                                            )
                                        }
                                    }
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = ToheedGold
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = serviceDropdownExpanded,
                                onDismissRequest = { serviceDropdownExpanded = false },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .background(ToheedNavySurface)
                            ) {
                                services.forEach { service ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = service.titleEn,
                                                        fontWeight = FontWeight.Bold,
                                                        color = ToheedTextPrimary,
                                                        fontSize = 13.sp
                                                    )
                                                    Text(
                                                        text = service.titleUr,
                                                        color = ToheedTextSecondary,
                                                        fontSize = 11.sp
                                                    )
                                                }
                                                Text(
                                                    text = "Rs. ${service.pricePkr}",
                                                    fontWeight = FontWeight.Bold,
                                                    color = ToheedGold,
                                                    fontSize = 13.sp
                                                )
                                            }
                                        },
                                        onClick = {
                                            viewModel.setOrderSelectedService(service)
                                            serviceDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Customer Photo & Reference Upload
                        Text(
                            text = if (language == AppLanguage.URDU) "2. ریفرنس تصویر یا پروڈکٹ اپلوڈ کریں" else "2. Upload Reference Photo / Product / Logo",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        if (photoUri != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(ToheedCardBg)
                                    .border(1.dp, ToheedGold, RoundedCornerShape(12.dp))
                            ) {
                                AsyncImage(
                                    model = photoUri,
                                    contentDescription = "Uploaded reference",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                IconButton(
                                    onClick = { viewModel.setOrderPhotoUri(null) },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(6.dp)
                                        .clip(CircleShape)
                                        .background(Color.Black.copy(alpha = 0.6f))
                                        .testTag("remove_upload_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remove photo",
                                        tint = Color.Red
                                    )
                                }
                            }
                        } else {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                    .testTag("upload_reference_card"),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = ToheedCardBg),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddPhotoAlternate,
                                        contentDescription = null,
                                        tint = ToheedGold,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = if (language == AppLanguage.URDU) "گیلری سے تصویر منتخب کریں" else "Select Photo from Gallery",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = ToheedTextPrimary
                                        )
                                        Text(
                                            text = if (language == AppLanguage.URDU) "شوز، برگر، برانڈ لوگو یا ویڈیو کا خاکہ" else "Shoes, burger, company logo, or concept image",
                                            fontSize = 11.sp,
                                            color = ToheedTextSecondary
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Client Details
                        Text(
                            text = if (language == AppLanguage.URDU) "3. رابطہ کی معلومات *" else "3. Contact Details *",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = clientName,
                            onValueChange = { viewModel.setOrderClientName(it) },
                            label = { Text(if (language == AppLanguage.URDU) "آپ کا نام *" else "Your Full Name *") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("order_input_name"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ToheedGold,
                                unfocusedBorderColor = ToheedCardBorder,
                                focusedTextColor = ToheedTextPrimary,
                                unfocusedTextColor = ToheedTextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = clientPhone,
                            onValueChange = { viewModel.setOrderClientPhone(it) },
                            label = { Text(if (language == AppLanguage.URDU) "واٹس ایپ یا فون نمبر (مثال: 0317 4329392) *" else "WhatsApp or Phone Number *") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("order_input_phone"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ToheedGold,
                                unfocusedBorderColor = ToheedCardBorder,
                                focusedTextColor = ToheedTextPrimary,
                                unfocusedTextColor = ToheedTextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Urgency Selection
                        Text(
                            text = if (language == AppLanguage.URDU) "4. کام کی رفتار (Urgency):" else "4. Urgency & Delivery Speed:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        listOf(
                            "Standard (2-3 Days)" to "معیاری (2 سے 3 دن)",
                            "Urgent (24 Hours)" to "ارجنٹ (24 گھنٹے)",
                            "Express (Same Day)" to "ایکسپریس (اسی دن)"
                        ).forEach { (urgOption, urgUrdu) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.setOrderUrgency(urgOption) }
                                    .padding(vertical = 2.dp)
                            ) {
                                RadioButton(
                                    selected = urgency == urgOption,
                                    onClick = { viewModel.setOrderUrgency(urgOption) },
                                    colors = RadioButtonDefaults.colors(selectedColor = ToheedGold)
                                )
                                Text(
                                    text = if (language == AppLanguage.URDU) urgUrdu else urgOption,
                                    fontSize = 12.sp,
                                    color = if (urgency == urgOption) ToheedGold else ToheedTextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Custom Notes
                        Text(
                            text = if (language == AppLanguage.URDU) "5. اضافی ہدایات و ضروری تفصیلات:" else "5. Project Requirements & Custom Text:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = customNotes,
                            onValueChange = { viewModel.setOrderCustomNotes(it) },
                            label = { Text(if (language == AppLanguage.URDU) "پوسٹر کا عنوان، آڈیو گانا، یا ضروری ہدایات..." else "Details, colors, custom text, song name, etc...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(95.dp)
                                .testTag("order_input_notes"),
                            maxLines = 4,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ToheedGold,
                                unfocusedBorderColor = ToheedCardBorder,
                                focusedTextColor = ToheedTextPrimary,
                                unfocusedTextColor = ToheedTextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Summary & Submit Button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(ToheedCardBg)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (language == AppLanguage.URDU) "کل متوقع قیمت:" else "Estimated Cost:",
                                fontSize = 13.sp,
                                color = ToheedTextSecondary
                            )
                            Text(
                                text = "Rs. ${selectedService?.pricePkr ?: 0} PKR",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black,
                                color = ToheedGold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.submitOrder(context, onSuccessOpenWhatsApp = true) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("submit_order_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.URDU) "آرڈر جمع کروائیں اور واٹس ایپ کھولیں" else "Submit & Send Order to WhatsApp",
                                fontSize = 13.sp,
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
