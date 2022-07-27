package vn.ztech.software.ecom.ui.shop.products

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.Shop
import vn.ztech.software.ecom.repository.IShopRepository

interface IListProductsInShopUseCase{
    suspend fun getListProductsInShop(shopId: String): Flow<PagingData<Product>>
}

class ListProductsInShopUseCase(private val shopRepository: IShopRepository): IListProductsInShopUseCase {
    override suspend fun getListProductsInShop(shopId: String):Flow<PagingData<Product>> {
        return shopRepository.getListProductsInShop(shopId)
    }
}