package com.example.data.model

data class ServiceItem(
    val id: String,
    val titleEn: String,
    val titleUr: String,
    val category: ServiceCategory,
    val descriptionEn: String,
    val descriptionUr: String,
    val pricePkr: Int,
    val turnaroundTime: String,
    val popularTag: String? = null,
    val iconType: String = "design"
)

enum class ServiceCategory(val labelEn: String, val labelUr: String) {
    ALL("All Services", "تمام خدمات"),
    GRAPHIC("Graphic Design", "گرافک ڈیزائننگ"),
    VIDEO("Video Editing", "ویڈیو ایڈیٹنگ")
}

data class OrderItem(
    val id: String,
    val clientName: String,
    val clientPhone: String,
    val serviceId: String,
    val serviceTitle: String,
    val pricePkr: Int,
    val customNotes: String,
    val urgency: String,
    val photoUri: String? = null,
    val status: OrderStatus = OrderStatus.RECEIVED,
    val timestamp: Long = System.currentTimeMillis()
)

enum class OrderStatus(val labelEn: String, val labelUr: String, val stepIndex: Int) {
    RECEIVED("Order Received", "آرڈر موصول ہو گیا", 0),
    IN_PROGRESS("In Progress / Designing", "کام جاری ہے", 1),
    REVISION("Review & Revisions", "نظرثانی جاری ہے", 2),
    COMPLETED("Ready for Delivery", "تیار ہو چکا ہے", 3),
    DELIVERED("Delivered", "مکمل ڈیلیور کر دیا گیا", 4)
}

enum class AppLanguage {
    ENGLISH,
    URDU
}
