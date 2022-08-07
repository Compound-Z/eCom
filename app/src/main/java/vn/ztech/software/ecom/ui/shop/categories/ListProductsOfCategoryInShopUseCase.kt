package vn.ztech.software.ecom.ui.shop.categories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.repository.IShopRepository

interface IListProductsOfCategoryInShopUseCase{
    suspend fun getListProductsOfCategoryInShop(shopId: String, categoryName: String): Flow<PagingData<Product>>
    suspend fun searchListProductsOfCategoryInShop(shopId: String, searchWordsCategory: String, searchWordsProduct: String): Flow<PagingData<Product>>

}

class ListProductsOfCategoryInShopUseCase(private val shopRepository: IShopRepository): IListProductsOfCategoryInShopUseCase {
    override suspend fun getListProductsOfCategoryInShop(shopId: String, categoryName: String):Flow<PagingData<Product>> {
        return shopRepository.getListProductsOfCategoryInShop(shopId, categoryName)
    }
    override suspend fun searchListProductsOfCategoryInShop(shopId: String, searchWordsCategory: String, searchWordsProduct: String):Flow<PagingData<Product>> {
        return shopRepository.searchListProductsOfCategoryInShop(shopId,searchWordsCategory,searchWordsProduct)
    }
}