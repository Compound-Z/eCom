package vn.ztech.software.ecom.ui.shop

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vn.ztech.software.ecom.repository.IShopRepository
import vn.ztech.software.ecom.model.Shop

interface IShopUseCase{
    suspend fun getShopInfo(shopId: String): Flow<Shop>
}

class ShopUseCase(private val shopRepository: IShopRepository): IShopUseCase {
    override suspend fun getShopInfo(shopId: String): Flow<Shop> = flow {
        emit(shopRepository.getShopInfo(shopId))
    }
}