package vn.ztech.software.ecom.domain.use_case.get_list_product

import vn.ztech.software.ecom.data.repository.IProductRepository
import java.util.concurrent.Flow

interface IGetListProductUseCase{
    suspend fun getListProductUseCase(): Flow<>
}