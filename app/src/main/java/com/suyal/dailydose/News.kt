package com.suyal.dailydose


/**
 * Created by Gaurav Suyal on 28-03-2024.
 */

data class News(
    /** Title of the article */
    val title: String,

    /** Section name of the article*/
    val section: String,

    /** Author name in the article */
    val author: String?,

    /** Web publication date of the article */
    val date: String,

    /** Website URL of the article */
    val url: String,

    /** Thumbnail of the article */
    val thumbnail: String?,

    /** TrailText of the article with string type Html */
    val trailTextHtml: String?
)
