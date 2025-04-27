package com.ugikpoenya.servermanager.model

import java.io.Serializable

data class ListItem(
    val text: String,
    val children: List<ListItem> = listOf(),
) : Serializable
