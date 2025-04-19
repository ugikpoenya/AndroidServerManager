package com.ugikpoenya.servermanager.model

import com.ugikpoenya.servermanager.tools.HtmlListParser
import java.io.Serializable

class PostModel : Serializable {
    var key: String? = null
    var post_title: String? = null
    var post_content: String? = null

    var post_image: String? = null
    var post_thumb: String? = null

    var category_key: String? = null
    var category_name: String? = null

    var storage_key: String? = null
    var storage_folder: String? = null

    fun getContentList(): List<ListItem> {
        return HtmlListParser().parse(post_content.toString())
    }
}