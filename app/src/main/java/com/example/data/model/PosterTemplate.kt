package com.example.data.model

data class PosterCustomization(
    val templateId: String = "burger_poster",
    val brandName: String = "TOHEED EDITS",
    val headlineText: String = "DELICIOUS BURGER",
    val subtitleText: String = "Fresh & Crispy | Order Now",
    val badgeText: String = "HOT & SPICY",
    val priceTagText: String = "Rs. 750",
    val footerText: String = "Free Delivery in 30 Mins • WhatsApp 0317-4329392",
    val customImageUri: String? = null,
    val primaryColorHex: Long = 0xFFFACC15,
    val accentColorHex: Long = 0xFF0A1128,
    val textColorHex: Long = 0xFFFFFFFF
)

data class PosterTemplateInfo(
    val id: String,
    val nameEn: String,
    val nameUr: String,
    val category: String,
    val defaultHeadline: String,
    val defaultSubtitle: String,
    val defaultBadge: String,
    val defaultPriceTag: String,
    val defaultFooter: String,
    val primaryColorHex: Long,
    val sampleImageRes: Int? = null
)
