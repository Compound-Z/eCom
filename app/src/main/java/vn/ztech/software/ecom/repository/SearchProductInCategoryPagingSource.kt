package vn.ztech.software.ecom.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import vn.ztech.software.ecom.api.ICategoryApi
import vn.ztech.software.ecom.api.request.SearchProductInCategoryRequest
import vn.ztech.software.ecom.model.Product
import java.lang.Exception

class SearchProductInCategoryPagingSource (val category: String, val request: SearchProductInCategoryRequest, private val categoryApi: ICategoryApi): PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            Log.d("xxx","***")
            val position = params.key ?: 1
            request.page = position
            val response = categoryApi.search(category, request)
            val pv = response.prevPage
            val nk = response.nextPage
            Log.d("xxxpv", pv.toString())
            Log.d("xxxpos", position.toString())
            Log.d("xxxnk", nk.toString())

            LoadResult.Page(data = response.docs,
                prevKey = response.prevPage,
                nextKey = response.nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        val key = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
        Log.d("xxxRefreshKey", key.toString())
        return key
    }

}