package vn.ztech.software.ecom.ui.shop.categories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.model.Category
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.repository.IShopRepository

interface IListCategoriesInShopUseCase{
    suspend fun getListCategoriesInShop(shopId: String): Flow<List<Category>>
}

class ListCategoriesInShopUseCase(private val shopRepository: IShopRepository): IListCategoriesInShopUseCase {
    override suspend fun getListCategoriesInShop(shopId: String): Flow<List<Category>> = flow {
        emit(shopRepository.getListCategoriesInShop(shopId))
    }
}