package vn.ztech.software.ecom.api.request

class SearchProductsOfCategoryInShopRequest (
    var page: Int = 1,
    val shopId: String,
    val searchWordsCategory: String,
)