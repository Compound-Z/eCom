package vn.ztech.software.ecom.domain.model

data class ProductDetails (
    var id: String="-1",
    var description: String = "",
    var providerInfo: String = "", //should be another object Provider, will be fixed later
    var images: List<String> = emptyList()
)