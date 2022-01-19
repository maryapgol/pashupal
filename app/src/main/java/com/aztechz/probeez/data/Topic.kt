
package com.aztechz.probeez.data

import androidx.recyclerview.widget.DiffUtil

data class Topic(
    val name: String,
    val courses: Int,
    val imageUrl: String,
    var isSelected: Boolean = false
)

object TopicDiff : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic) = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: Topic, newItem: Topic) = oldItem == newItem
}

val topics = listOf(
    Topic("Architecture", 58, "https://source.unsplash.com/RFDP7_80v5A",false),
    Topic("Arts & Crafts", 121, "https://source.unsplash.com/Tq4YjCa2BSc",false),
    Topic("Business", 78, "https://source.unsplash.com/MYbhN8KaaEc",false),
    Topic("Culinary", 118, "https://source.unsplash.com/uB7q7aipU2o",false),
    Topic("Design", 423, "https://source.unsplash.com/cXkrqY2wFyc",false),
    Topic("Fashion", 92, "https://source.unsplash.com/omgRZCmTvUM",false),
    Topic("Film", 165, "https://source.unsplash.com/9lTUAlNB87M",false),
    Topic("Gaming", 164, "https://source.unsplash.com/xITnxxlzGAE",false),
    Topic("Illustration", 326, "https://source.unsplash.com/lxoq0zppH5w",false),
    Topic("Lifestyle", 305, "https://source.unsplash.com/DzIt-fTYv4E",false),
    Topic("Music", 212, "https://source.unsplash.com/LoppUA_9F1w",false),
    Topic("Painting", 172, "https://source.unsplash.com/FwF_fKj5tBo",false),
    Topic("Photography", 321, "https://source.unsplash.com/8nXKXYdO-Wk",false),
    Topic("Technology", 118, "https://source.unsplash.com/MxVkWPiJALs",false)
)
