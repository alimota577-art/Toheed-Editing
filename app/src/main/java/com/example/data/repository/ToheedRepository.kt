package com.example.data.repository

import com.example.data.local.OrderDao
import com.example.data.local.OrderEntity
import com.example.data.local.ServicePriceDao
import com.example.data.local.ServicePriceEntity
import com.example.data.model.OrderItem
import com.example.data.model.OrderStatus
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class ToheedRepository(
    private val orderDao: OrderDao,
    private val servicePriceDao: ServicePriceDao
) {
    companion object {
        const val WHATSAPP_NUMBER = "+923174329392"
        const val WHATSAPP_DISPLAY = "0317 4329392"
        const val BRAND_NAME = "TOHEED EDITS"
        const val EMAIL_CONTACT = "toheed.edits@gmail.com"

        val DEFAULT_SERVICES = listOf(
            ServiceItem(
                id = "logo_design",
                titleEn = "Logo Design & Brand Identity",
                titleUr = "لوگو ڈیزائننگ اور برانڈ شناخت",
                category = ServiceCategory.GRAPHIC,
                descriptionEn = "Unique vector logos with 3 concepts, transparent PNG, 3D mockup & source vector files.",
                descriptionUr = "تین جدید ترین کانسیپٹس، ویکٹر فائلز، 3D موک اپ اور لائف ٹائم ریویژنز۔",
                pricePkr = 2500,
                turnaroundTime = "24-48 Hours",
                popularTag = "Most Popular",
                iconType = "brush"
            ),
            ServiceItem(
                id = "thumbnail_design",
                titleEn = "YouTube Thumbnail (High CTR)",
                titleUr = "یوٹیوب کلک ایبل تھمب نیل",
                category = ServiceCategory.GRAPHIC,
                descriptionEn = "Eye-catching, viral, high CTR thumbnails for tech, gaming, vlogs, and podcasts.",
                descriptionUr = "وائرل اور کلک ایبل تھمب نیلز جو آپ کے ویوز اور کلکس کو کئی گنا بڑھائیں۔",
                pricePkr = 1200,
                turnaroundTime = "12-24 Hours",
                popularTag = "Trending",
                iconType = "image"
            ),
            ServiceItem(
                id = "poster_banner",
                titleEn = "Poster & Flex Banner Design",
                titleUr = "کمرشل پوسٹر اور فلیکس بینر",
                category = ServiceCategory.GRAPHIC,
                descriptionEn = "High-res promotional posters for restaurants, food burgers, footwear, gyms, and sales.",
                descriptionUr = "ریستوران، برگر، شوز، سیلز اور ایونٹس کے لیے ہائی ریزولوشن پوسٹرز۔",
                pricePkr = 2000,
                turnaroundTime = "24 Hours",
                popularTag = "Hot Deal",
                iconType = "campaign"
            ),
            ServiceItem(
                id = "social_media_posts",
                titleEn = "Social Media Posts (Pack of 3)",
                titleUr = "سوشل میڈیا پوسٹس ڈیزائن",
                category = ServiceCategory.GRAPHIC,
                descriptionEn = "Professional Instagram, Facebook & LinkedIn feed posters with modern typography.",
                descriptionUr = "فیس بک اور انسٹاگرام کے لیے خوبصورت اور معیاری پروموشنل ڈیزائن۔",
                pricePkr = 2200,
                turnaroundTime = "24 Hours",
                iconType = "share"
            ),
            ServiceItem(
                id = "business_card",
                titleEn = "Luxury Business Card & Stationery",
                titleUr = "بزنس کارڈ اور دفتری برانڈنگ",
                category = ServiceCategory.GRAPHIC,
                descriptionEn = "Double-sided print-ready business cards, letterhead, and corporate mockups.",
                descriptionUr = "ڈبل سائیڈ پرنٹ ریڈی لگژری کارڈز اور کارپوریٹ سٹیشنری۔",
                pricePkr = 1500,
                turnaroundTime = "24 Hours",
                iconType = "badge"
            ),
            ServiceItem(
                id = "youtube_channel_art",
                titleEn = "YouTube Channel Art & Intro Banner",
                titleUr = "یوٹیوب چینل بینر اور آرٹ",
                category = ServiceCategory.GRAPHIC,
                descriptionEn = "Custom responsive channel banner for mobile, desktop, and TV displays.",
                descriptionUr = "موبائل، پی سی اور ٹی وی کے لیے مکمل ریسپانسو چینل ہیرو آرٹ۔",
                pricePkr = 1800,
                turnaroundTime = "24 Hours",
                iconType = "tv"
            ),
            ServiceItem(
                id = "reels_shorts",
                titleEn = "Reels & Shorts Viral Editing",
                titleUr = "ریلز اور شارٹس ویڈیو ایڈیٹنگ",
                category = ServiceCategory.VIDEO,
                descriptionEn = "Fast cuts, auto-captions/subtitles, sound effects, zooms, and engaging B-roll footage.",
                descriptionUr = "سوشل میڈیا ریلز اور ٹک ٹاک کے لیے ساؤنڈ ایفیکٹس، کیپشنز اور فاسٹ کٹس۔",
                pricePkr = 2500,
                turnaroundTime = "24 Hours",
                popularTag = "Best Seller",
                iconType = "movie"
            ),
            ServiceItem(
                id = "commercial_ads",
                titleEn = "Commercial Ad & Product Video",
                titleUr = "پروڈکٹ پروموشنل ویڈیو ایڈیٹنگ",
                category = ServiceCategory.VIDEO,
                descriptionEn = "High-energy commercial promos for e-commerce, real estate, jewelry, food, and clothes.",
                descriptionUr = "آن لائن اسٹورز، پروڈکٹس اور برانڈز کے لیے ہائی انرجی ویڈیو اشتہارات۔",
                pricePkr = 5000,
                turnaroundTime = "48 Hours",
                popularTag = "Premium",
                iconType = "video"
            ),
            ServiceItem(
                id = "full_video_edit",
                titleEn = "Full YouTube Video Editing",
                titleUr = "مکمل یوٹیوب ویڈیو ایڈیٹنگ",
                category = ServiceCategory.VIDEO,
                descriptionEn = "Complete editing (up to 12 mins) with color grading, audio cleanup, transitions & lower thirds.",
                descriptionUr = "کلر گریڈنگ، آڈیو صفائی، ٹرانزیشنز اور ٹائٹلز کے ساتھ مکمل ایڈیٹنگ۔",
                pricePkr = 6500,
                turnaroundTime = "48-72 Hours",
                iconType = "play"
            ),
            ServiceItem(
                id = "urdu_poetry_status",
                titleEn = "Urdu Poetry & Islamic Status Video",
                titleUr = "اردو شاعری و اسلامی سٹیٹس ویڈیو",
                category = ServiceCategory.VIDEO,
                descriptionEn = "Beautiful InPage/Nastaliq Urdu typography animations, ambient background, and emotional sound design.",
                descriptionUr = "خوبصورت نستعلیق اردو فونٹس، بیک گراؤنڈ میوزک اور دلکش موشن ایفیکٹس۔",
                pricePkr = 1800,
                turnaroundTime = "24 Hours",
                iconType = "font"
            )
        )
    }

    val servicesFlow: Flow<List<ServiceItem>> = servicePriceDao.getAllPrices().map { customPrices ->
        val priceMap = customPrices.associate { it.serviceId to it.pricePkr }
        DEFAULT_SERVICES.map { service ->
            val customPrice = priceMap[service.id]
            if (customPrice != null) {
                service.copy(pricePkr = customPrice)
            } else {
                service
            }
        }
    }

    val allOrdersFlow: Flow<List<OrderItem>> = orderDao.getAllOrders().map { entities ->
        entities.map { it.toModel() }
    }

    fun searchOrders(query: String): Flow<List<OrderItem>> {
        return orderDao.searchOrders(query).map { entities ->
            entities.map { it.toModel() }
        }
    }

    suspend fun getOrderById(orderId: String): OrderItem? {
        return orderDao.getOrderById(orderId)?.toModel()
    }

    suspend fun placeOrder(order: OrderItem) {
        orderDao.insertOrder(order.toEntity())
    }

    suspend fun updateOrderStatus(orderId: String, status: OrderStatus) {
        orderDao.updateOrderStatus(orderId, status.name)
    }

    suspend fun updateServicePrice(serviceId: String, newPricePkr: Int) {
        servicePriceDao.insertOrUpdatePrice(ServicePriceEntity(serviceId, newPricePkr))
    }

    suspend fun deleteOrder(orderId: String) {
        orderDao.deleteOrder(orderId)
    }

    private fun OrderEntity.toModel(): OrderItem {
        val parsedStatus = try {
            OrderStatus.valueOf(status)
        } catch (e: Exception) {
            OrderStatus.RECEIVED
        }
        return OrderItem(
            id = id,
            clientName = clientName,
            clientPhone = clientPhone,
            serviceId = serviceId,
            serviceTitle = serviceTitle,
            pricePkr = pricePkr,
            customNotes = customNotes,
            urgency = urgency,
            photoUri = photoUri,
            status = parsedStatus,
            timestamp = timestamp
        )
    }

    private fun OrderItem.toEntity(): OrderEntity {
        return OrderEntity(
            id = id,
            clientName = clientName,
            clientPhone = clientPhone,
            serviceId = serviceId,
            serviceTitle = serviceTitle,
            pricePkr = pricePkr,
            customNotes = customNotes,
            urgency = urgency,
            photoUri = photoUri,
            status = status.name,
            timestamp = timestamp
        )
    }
}
