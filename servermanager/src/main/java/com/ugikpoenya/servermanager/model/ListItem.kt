package com.ugikpoenya.servermanager.model

data class ListItem(
    val text: String,
    val children: List<ListItem> = listOf()
)
