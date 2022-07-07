package vn.ztech.software.ecom.api.request

data class SearchProductInCategoryRequest(
    val searchWordsProduct: String,
    var page: Int = 1
)