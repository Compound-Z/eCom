package vn.ztech.software.ecom.util.extension

import vn.ztech.software.ecom.model.AddressItem

fun AddressItem.getFullAddress(): String{
    return "${this.detailedAddress}, ${this.ward.name}, ${this.district.name}, ${this.province.name}"
}