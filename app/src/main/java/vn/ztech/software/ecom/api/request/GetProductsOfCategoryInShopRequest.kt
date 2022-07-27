package vn.ztech.software.ecom.api.request

class GetProductsOfCategoryInShopRequest (
    var page: Int = 1,
    val categoryName: String,
)