package vn.ztech.software.ecom.api.request

class SearchProductsInShopRequest (
    var page: Int = 1,
    val shopId: String,
)