package com.ugikpoenya.servermanager.tools

import com.ugikpoenya.servermanager.model.ListItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class HtmlListParser {
    fun parse(html: String): List<ListItem> {
        val doc = Jsoup.parse(html)
        val rootList = doc.selectFirst("ul, ol") ?: return emptyList()
        return parseList(rootList)
    }

    private fun parseList(element: Element): List<ListItem> {
        val result = mutableListOf<ListItem>()
        for (li in element.select("> li")) {
            val text = li.ownText().trim()
            val nestedList = li.selectFirst("ul, ol")?.let { parseList(it) } ?: listOf()
            result.add(ListItem(text, nestedList))
        }
        return result
    }

    fun print(items: List<ListItem>, indent: String = "") {
        for (item in items) {
            println("$indent- ${item.text}")
            if (item.children.isNotEmpty()) {
                print(item.children, "$indent  ")
            }
        }
    }
}