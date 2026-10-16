package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.theme.ToheedCardBg
import com.example.ui.theme.ToheedCardBorder
import com.example.ui.theme.ToheedDarkBlue
import com.example.ui.theme.ToheedDeepNight
import com.example.ui.theme.ToheedElectricBlue
import com.example.ui.theme.ToheedGold
import com.example.ui.theme.ToheedNavySurface
import com.example.ui.theme.ToheedPureWhite
import com.example.ui.theme.ToheedTextPrimary
import com.example.ui.theme.ToheedTextSecondary
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.ToheedViewModel

@Composable
fun PlayStoreAndWebGuideScreen(
    viewModel: ToheedViewModel,
    language: AppLanguage
) {
    val context = LocalContext.current

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
                text = if (language == AppLanguage.URDU) "گوگل پلے اسٹور گائیڈ اور ویب سائٹ" else "Google Play Store & Architecture Guide",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = ToheedGold
            )
            Text(
                text = if (language == AppLanguage.URDU)
                    "اینڈرائیڈ ایپ کی جانچ، پلے اسٹور پر اشاعت، اور مستقبل کی کلاؤڈ سروسز کی تفصیل۔"
                else
                    "How to test, build for Google Play Store release, and backend features roadmap.",
                fontSize = 12.sp,
                color = ToheedTextSecondary
            )
        }

        // Section 1: Google Play Store Release Preparation
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold),
                modifier = Modifier.fillMaxWidth().testTag("play_store_guide_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(ToheedGold.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Smartphone, contentDescription = null, tint = ToheedGold)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "1. Google Play Store Preparation",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    GuideStepItem(
                        step = "Step 1: Application ID & Manifest",
                        detail = "Unique applicationId is configured as 'com.aistudio.toheed.edits' with targetSdk 36. Manifest includes adaptive icon, label 'TOHEED EDITS' (under 30 chars), and least-privilege permissions."
                    )
                    GuideStepItem(
                        step = "Step 2: Generate Signed App Bundle (.aab)",
                        detail = "Run command in terminal:\ngradle :app:bundleRelease\nThe resulting bundle is saved in app/build/outputs/bundle/release/app-release.aab"
                    )
                    GuideStepItem(
                        step = "Step 3: Play Console Store Listing",
                        detail = "Upload the 512x512 app icon, 1024x500 feature graphic, and phone/tablet screenshots. TOHEED EDITS complies with Play Store Zero-Storage permission policies using the Android Photo Picker."
                    )
                    GuideStepItem(
                        step = "Step 4: Privacy Policy & Business Verification",
                        detail = "Provide your business contact (WhatsApp +92 317 4329392, Pakistan) and basic data disclosure regarding customer photo uploads."
                    )
                }
            }
        }

        // Section 2: Local JVM Testing & Quality Assurance
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedNavySurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(ToheedElectricBlue.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Build, contentDescription = null, tint = ToheedElectricBlue)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "2. How to Test the Android App",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedElectricBlue
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "• Local JVM Testing: Run unit tests and Robolectric Critical User Journey tests with:\ngradle :app:testDebugUnitTest\n• Live Interactive Testing: Use the cloud Streaming Emulator in Google AI Studio to test camera uploads, WhatsApp intent launches, and Room database persistence.",
                        fontSize = 12.sp,
                        color = ToheedTextPrimary,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Section 3: Features Requiring Further Implementation
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedDarkBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedCardBorder),
                modifier = Modifier.fillMaxWidth().testTag("roadmap_features_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(ToheedGold.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = ToheedGold)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "3. Features Requiring Further Implementation",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedGold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Beyond the current functional Room database and local storage, production deployment will benefit from the following extensions:",
                        fontSize = 12.sp,
                        color = ToheedTextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    FeatureRoadmapItem(
                        title = "Cloud Multi-Admin Sync",
                        desc = "Connect Firebase Firestore so multiple designers and video editors can view orders synchronized across multiple phones and computers in real-time."
                    )
                    FeatureRoadmapItem(
                        title = "4K / Heavy Video Cloud Storage",
                        desc = "Direct integration with Google Drive, AWS S3, or Cloudflare R2 for customers uploading raw 5GB+ video clips and Premiere Pro project files."
                    )
                    FeatureRoadmapItem(
                        title = "Pakistani Payment Gateways",
                        desc = "Integration with JazzCash, EasyPaisa, or Raast instant bank QR codes for direct in-app PKR advance payment before starting design work."
                    )
                    FeatureRoadmapItem(
                        title = "Automated Push Notifications (FCM)",
                        desc = "Alert customers when their video or logo status moves to 'Completed' or 'Ready for Review'."
                    )
                }
            }
        }

        // Section 4: Responsive Website Suite
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ToheedNavySurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, ToheedGold.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(WhatsAppGreen.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = WhatsAppGreen)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "4. Responsive Website Included",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToheedPureWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "A complete, responsive, mobile-first website for TOHEED EDITS is bundled in the workspace at /web/index.html (and app/src/main/assets/web/index.html) with interactive poster previews, Urdu & English toggles, PKR pricing, and WhatsApp integration.",
                        fontSize = 12.sp,
                        color = ToheedTextSecondary,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.openWhatsApp(context, "Assalam-o-Alaikum Toheed Edits! I am inquiring about the website and Play Store setup.") },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Contact Support on WhatsApp", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun GuideStepItem(step: String, detail: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = step,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = ToheedTextPrimary
        )
        Text(
            text = detail,
            fontSize = 11.sp,
            color = ToheedTextSecondary,
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun FeatureRoadmapItem(title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(ToheedGold)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ToheedGold)
            Text(text = desc, fontSize = 11.sp, color = ToheedTextSecondary, lineHeight = 15.sp)
        }
    }
}
