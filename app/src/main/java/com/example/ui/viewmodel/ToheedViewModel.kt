package com.example.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.OrderItem
import com.example.data.model.OrderStatus
import com.example.data.model.PosterCustomization
import com.example.data.model.PosterTemplateInfo
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceItem
import com.example.data.repository.ToheedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.UUID

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Services : Screen("services")
    object PosterEditor : Screen("poster_editor")
    object PlaceOrder : Screen("place_order")
    object OrderTracking : Screen("order_tracking")
    object AdminDashboard : Screen("admin_dashboard")
    object PlayStoreGuide : Screen("play_store_guide")
    object WebSitePreview : Screen("website_preview")
}

class ToheedViewModel(
    private val repository: ToheedRepository
) : ViewModel() {

    // Language state
    private val _language = MutableStateFlow(AppLanguage.ENGLISH)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    fun toggleLanguage() {
        _language.value = if (_language.value == AppLanguage.ENGLISH) AppLanguage.URDU else AppLanguage.ENGLISH
    }

    fun setLanguage(lang: AppLanguage) {
        _language.value = lang
    }

    // Current navigation screen
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<Screen>(Screen.Home)

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenBackStack.add(screen)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenBackStack.size > 1) {
            screenBackStack.removeAt(screenBackStack.lastIndex)
            _currentScreen.value = screenBackStack.last()
            return true
        }
        return false
    }

    // Category filter in Services tab
    private val _selectedCategory = MutableStateFlow(ServiceCategory.ALL)
    val selectedCategory: StateFlow<ServiceCategory> = _selectedCategory.asStateFlow()

    fun selectCategory(category: ServiceCategory) {
        _selectedCategory.value = category
    }

    // Services list from Room database with live prices
    val services: StateFlow<List<ServiceItem>> = repository.servicesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ToheedRepository.DEFAULT_SERVICES
    )

    // All orders
    val allOrders: StateFlow<List<OrderItem>> = repository.allOrdersFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Tracking screen state
    private val _trackingSearchQuery = MutableStateFlow("")
    val trackingSearchQuery: StateFlow<String> = _trackingSearchQuery.asStateFlow()

    private val _trackedOrder = MutableStateFlow<OrderItem?>(null)
    val trackedOrder: StateFlow<OrderItem?> = _trackedOrder.asStateFlow()

    private val _trackingErrorMessage = MutableStateFlow<String?>(null)
    val trackingErrorMessage: StateFlow<String?> = _trackingErrorMessage.asStateFlow()

    fun updateTrackingQuery(query: String) {
        _trackingSearchQuery.value = query
        _trackingErrorMessage.value = null
    }

    fun trackOrder() {
        val query = _trackingSearchQuery.value.trim()
        if (query.isEmpty()) {
            _trackingErrorMessage.value = if (_language.value == AppLanguage.URDU) "براہ کرم آرڈر کوڈ یا فون نمبر درج کریں" else "Please enter Order ID or Phone number"
            return
        }

        viewModelScope.launch {
            val orders = allOrders.value
            val match = orders.firstOrNull {
                it.id.equals(query, ignoreCase = true) || it.clientPhone.contains(query)
            }
            if (match != null) {
                _trackedOrder.value = match
                _trackingErrorMessage.value = null
            } else {
                _trackedOrder.value = null
                _trackingErrorMessage.value = if (_language.value == AppLanguage.URDU) "کوئی آرڈر نہیں ملا۔ براہ کرم آرڈر کوڈ چیک کریں۔" else "No order found with '$query'. Please verify your Order ID."
            }
        }
    }

    // Order Placement Form State
    private val _orderClientName = MutableStateFlow("")
    val orderClientName: StateFlow<String> = _orderClientName.asStateFlow()

    private val _orderClientPhone = MutableStateFlow("")
    val orderClientPhone: StateFlow<String> = _orderClientPhone.asStateFlow()

    private val _orderSelectedService = MutableStateFlow<ServiceItem?>(null)
    val orderSelectedService: StateFlow<ServiceItem?> = _orderSelectedService.asStateFlow()

    private val _orderUrgency = MutableStateFlow("Standard (2-3 Days)")
    val orderUrgency: StateFlow<String> = _orderUrgency.asStateFlow()

    private val _orderCustomNotes = MutableStateFlow("")
    val orderCustomNotes: StateFlow<String> = _orderCustomNotes.asStateFlow()

    private val _orderPhotoUri = MutableStateFlow<String?>(null)
    val orderPhotoUri: StateFlow<String?> = _orderPhotoUri.asStateFlow()

    private val _isSubmittingOrder = MutableStateFlow(false)
    val isSubmittingOrder: StateFlow<String?> = MutableStateFlow<String?>(null)

    private val _orderSuccessItem = MutableStateFlow<OrderItem?>(null)
    val orderSuccessItem: StateFlow<OrderItem?> = _orderSuccessItem.asStateFlow()

    fun setOrderClientName(name: String) { _orderClientName.value = name }
    fun setOrderClientPhone(phone: String) { _orderClientPhone.value = phone }
    fun setOrderSelectedService(service: ServiceItem?) { _orderSelectedService.value = service }
    fun setOrderUrgency(urgency: String) { _orderUrgency.value = urgency }
    fun setOrderCustomNotes(notes: String) { _orderCustomNotes.value = notes }
    fun setOrderPhotoUri(uri: String?) { _orderPhotoUri.value = uri }

    fun startOrderForService(service: ServiceItem) {
        _orderSelectedService.value = service
        _orderSuccessItem.value = null
        navigateTo(Screen.PlaceOrder)
    }

    fun submitOrder(context: Context, onSuccessOpenWhatsApp: Boolean = true) {
        val name = _orderClientName.value.trim()
        val phone = _orderClientPhone.value.trim()
        val service = _orderSelectedService.value

        if (name.isEmpty()) {
            Toast.makeText(context, if (_language.value == AppLanguage.URDU) "براہ کرم اپنا نام درج کریں" else "Please enter your name", Toast.LENGTH_SHORT).show()
            return
        }
        if (phone.isEmpty()) {
            Toast.makeText(context, if (_language.value == AppLanguage.URDU) "براہ کرم واٹس ایپ یا فون نمبر درج کریں" else "Please enter WhatsApp/Phone number", Toast.LENGTH_SHORT).show()
            return
        }
        if (service == null) {
            Toast.makeText(context, if (_language.value == AppLanguage.URDU) "براہ کرم سروس منتخب کریں" else "Please select a service", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            val randomNum = (1000..9999).random()
            val orderId = "TE-$randomNum"
            val order = OrderItem(
                id = orderId,
                clientName = name,
                clientPhone = phone,
                serviceId = service.id,
                serviceTitle = service.titleEn,
                pricePkr = service.pricePkr,
                customNotes = _orderCustomNotes.value.trim(),
                urgency = _orderUrgency.value,
                photoUri = _orderPhotoUri.value,
                status = OrderStatus.RECEIVED,
                timestamp = System.currentTimeMillis()
            )

            repository.placeOrder(order)
            _orderSuccessItem.value = order
            _trackedOrder.value = order
            _trackingSearchQuery.value = orderId

            if (onSuccessOpenWhatsApp) {
                openWhatsAppForOrder(context, order)
            }
        }
    }

    fun resetOrderForm() {
        _orderClientName.value = ""
        _orderClientPhone.value = ""
        _orderSelectedService.value = null
        _orderCustomNotes.value = ""
        _orderPhotoUri.value = null
        _orderSuccessItem.value = null
    }

    // Poster Customizer / Editor State
    val posterTemplates = listOf(
        PosterTemplateInfo(
            id = "burger_poster",
            nameEn = "Delicious Burger / Food Poster",
            nameUr = "ڈلیشس برگر و فوڈ پوسٹر",
            category = "Food & Restaurant",
            defaultHeadline = "DELICIOUS BURGER",
            defaultSubtitle = "TASTE THE FIRE | SINCE 2024",
            defaultBadge = "HOT & SPICY",
            defaultPriceTag = "Rs. 750",
            defaultFooter = "Order Now • WhatsApp 0317-4329392",
            primaryColorHex = 0xFFFACC15,
            sampleImageRes = R.drawable.img_graphic_services
        ),
        PosterTemplateInfo(
            id = "velocity_kicks",
            nameEn = "Velocity Kicks / Footwear Ad",
            nameUr = "اسپورٹس شوز و فیشن اشتہار",
            category = "Fashion & Sports",
            defaultHeadline = "VELOCITY KICKS",
            defaultSubtitle = "TRAIN FASTER | FLY HIGHER",
            defaultBadge = "NEW ARRIVAL",
            defaultPriceTag = "Rs. 3,499",
            defaultFooter = "Limited Edition • Free Delivery Across Pakistan",
            primaryColorHex = 0xFFFFD700,
            sampleImageRes = R.drawable.img_graphic_services
        ),
        PosterTemplateInfo(
            id = "youtube_thumbnail",
            nameEn = "YouTube Viral Thumbnail (16:9)",
            nameUr = "یوٹیوب وائرل تھمب نیل",
            category = "YouTube & Social",
            defaultHeadline = "TOP 5 GADGETS!",
            defaultSubtitle = "MUST SEE BEFORE BUYING 2024",
            defaultBadge = "VIRAL CTR",
            defaultPriceTag = "100K VIEWS",
            defaultFooter = "TOHEED EDITS • High Click-Through Thumbnail",
            primaryColorHex = 0xFF38BDF8,
            sampleImageRes = R.drawable.img_graphic_services
        ),
        PosterTemplateInfo(
            id = "video_promo",
            nameEn = "Cinematic Video / Reel Banner",
            nameUr = "سنیماٹک ویڈیو و ریلز پوسٹر",
            category = "Video Editing",
            defaultHeadline = "CINEMATIC REEL 2024",
            defaultSubtitle = "PRO SOUND & COLOR GRADING",
            defaultBadge = "4K ULTRA HD",
            defaultPriceTag = "PRO CUT",
            defaultFooter = "Premiere Pro & After Effects Mastered",
            primaryColorHex = 0xFF06B6D4,
            sampleImageRes = R.drawable.img_video_services
        )
    )

    private val _posterState = MutableStateFlow(
        PosterCustomization(
            templateId = "burger_poster",
            headlineText = "DELICIOUS BURGER",
            subtitleText = "TASTE THE FIRE | SINCE 2024",
            badgeText = "HOT & SPICY",
            priceTagText = "Rs. 750",
            footerText = "TOHEED EDITS • WhatsApp 0317-4329392"
        )
    )
    val posterState: StateFlow<PosterCustomization> = _posterState.asStateFlow()

    fun selectPosterTemplate(template: PosterTemplateInfo) {
        _posterState.value = _posterState.value.copy(
            templateId = template.id,
            headlineText = template.defaultHeadline,
            subtitleText = template.defaultSubtitle,
            badgeText = template.defaultBadge,
            priceTagText = template.defaultPriceTag,
            footerText = template.defaultFooter,
            primaryColorHex = template.primaryColorHex
        )
    }

    fun updatePosterHeadline(text: String) { _posterState.value = _posterState.value.copy(headlineText = text) }
    fun updatePosterSubtitle(text: String) { _posterState.value = _posterState.value.copy(subtitleText = text) }
    fun updatePosterBadge(text: String) { _posterState.value = _posterState.value.copy(badgeText = text) }
    fun updatePosterPriceTag(text: String) { _posterState.value = _posterState.value.copy(priceTagText = text) }
    fun updatePosterFooter(text: String) { _posterState.value = _posterState.value.copy(footerText = text) }
    fun updatePosterImageUri(uri: String?) { _posterState.value = _posterState.value.copy(customImageUri = uri) }
    fun updatePosterColor(colorHex: Long) { _posterState.value = _posterState.value.copy(primaryColorHex = colorHex) }

    fun applyPosterToOrder() {
        val notes = "Custom Poster Preview Request:\nHeadline: ${_posterState.value.headlineText}\nSubtitle: ${_posterState.value.subtitleText}\nBadge: ${_posterState.value.badgeText}\nPrice Tag: ${_posterState.value.priceTagText}"
        _orderCustomNotes.value = notes
        _orderPhotoUri.value = _posterState.value.customImageUri
        val posterService = services.value.firstOrNull { it.id == "poster_banner" }
        _orderSelectedService.value = posterService
        navigateTo(Screen.PlaceOrder)
    }

    // Admin Dashboard State
    private val _isAdminAuthenticated = MutableStateFlow(false)
    val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

    private val _adminPinInput = MutableStateFlow("")
    val adminPinInput: StateFlow<String> = _adminPinInput.asStateFlow()

    private val _adminPinError = MutableStateFlow<String?>(null)
    val adminPinError: StateFlow<String?> = _adminPinError.asStateFlow()

    private val adminMasterPin = "7860" // Default secure PIN

    fun setAdminPinInput(pin: String) {
        _adminPinInput.value = pin
        _adminPinError.value = null
    }

    fun verifyAdminPin(): Boolean {
        return if (_adminPinInput.value.trim() == adminMasterPin) {
            _isAdminAuthenticated.value = true
            _adminPinError.value = null
            _adminPinInput.value = ""
            true
        } else {
            _adminPinError.value = if (_language.value == AppLanguage.URDU) "غلط پن کوڈ! دوبارہ کوشش کریں" else "Incorrect PIN! Use default PIN 7860"
            false
        }
    }

    fun adminLogout() {
        _isAdminAuthenticated.value = false
        _adminPinInput.value = ""
    }

    fun updateServicePriceAdmin(serviceId: String, newPrice: Int) {
        viewModelScope.launch {
            repository.updateServicePrice(serviceId, newPrice)
        }
    }

    fun updateOrderStatusAdmin(orderId: String, newStatus: OrderStatus) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus)
            if (_trackedOrder.value?.id == orderId) {
                _trackedOrder.value = _trackedOrder.value?.copy(status = newStatus)
            }
        }
    }

    fun deleteOrderAdmin(orderId: String) {
        viewModelScope.launch {
            repository.deleteOrder(orderId)
            if (_trackedOrder.value?.id == orderId) {
                _trackedOrder.value = null
            }
        }
    }

    // WhatsApp Direct Contact Intent
    fun openWhatsApp(context: Context, customMessage: String = "") {
        val phone = ToheedRepository.WHATSAPP_NUMBER.replace("+", "").replace(" ", "")
        val defaultMsg = if (customMessage.isNotEmpty()) {
            customMessage
        } else {
            "Assalam-o-Alaikum Toheed Edits! I am interested in your Graphic Designing & Video Editing services. Please share details and pricing."
        }
        val url = "https://wa.me/$phone?text=" + URLEncoder.encode(defaultMsg, StandardCharsets.UTF_8.toString())
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp not installed or could not open browser", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsAppForService(context: Context, service: ServiceItem) {
        val msg = "Assalam-o-Alaikum Toheed Edits!\nI want to order: ${service.titleEn}\nCategory: ${service.category.labelEn}\nListed Price: Rs. ${service.pricePkr} PKR\nTurnaround: ${service.turnaroundTime}\nPlease confirm timeline and details."
        openWhatsApp(context, msg)
    }

    fun openWhatsAppForOrder(context: Context, order: OrderItem) {
        val msg = """
            Assalam-o-Alaikum Toheed Edits!
            NEW ORDER SUBMITTED:
            Order ID: ${order.id}
            Client Name: ${order.clientName}
            Phone / WhatsApp: ${order.clientPhone}
            Service: ${order.serviceTitle}
            Price: Rs. ${order.pricePkr} PKR
            Urgency: ${order.urgency}
            Notes: ${order.customNotes.ifEmpty { "None" }}
            Please start working on my design/video!
        """.trimIndent()
        openWhatsApp(context, msg)
    }
}

class ToheedViewModelFactory(
    private val repository: ToheedRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToheedViewModel::class.java)) {
            return ToheedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
