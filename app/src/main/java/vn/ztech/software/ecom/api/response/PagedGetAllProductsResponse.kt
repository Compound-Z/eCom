package vn.ztech.software.ecom.api.response

import vn.ztech.software.ecom.model.Product

data class PagedGetAllProductsResponse(
    val docs: List<Product>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Int?,
    val page: Int,
    val pagingCounter: Int,
    val prevPage: Int?,
    val totalDocs: Int,
    val totalPages: Int
)