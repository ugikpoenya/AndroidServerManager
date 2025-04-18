package com.ugikpoenya.servermanager.model

import java.io.Serializable

class ApiResponseModel : Serializable {
    var status: Boolean? = null
    var name: String? = null
    var categories: ArrayList<CategoryModel>? = null
    var item: ItemModel? = null
}