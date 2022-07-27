package vn.ztech.software.ecom.ui.shop

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.repository.IShopRepository
import vn.ztech.software.ecom.model.Product
import vn.ztech.software.ecom.model.Shop

interface IShopUseCase{
    suspend fun getShopInfo(shopId: String): Flow<Shop>
//    suspend fun getListProducts(): Flow<PagingData<Product>>
//    suspend fun search(searchWords: String): Flow<PagingData<Product>>
//    suspend fun getOneProduct(productId: String): Flow<Product>

}

class ShopUseCase(private val shopRepository: IShopRepository): IShopUseCase {
    //    override suspend fun getListProducts(): Flow<PagingData<Product>> {
//        return shopRepository.getListProducts()
//    }
//
//    override suspend fun search(searchWords: String): Flow<PagingData<Product>> {
//        return shopRepository.search(searchWords)
//    }
//    override suspend fun getOneProduct(productId: String): Flow<Product> = flow {
//        emit(shopRepository.getOneProduct(productId))
//    }
    override suspend fun getShopInfo(shopId: String): Flow<Shop> = flow {
        emit(shopRepository.getShopInfo(shopId))
    }


}