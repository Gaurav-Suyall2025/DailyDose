package com.suyal.dailydose.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.preference.PreferenceManager
import android.text.Html
import android.text.format.DateUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suyal.dailydose.News
import com.suyal.dailydose.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Gaurav Suyal on 28-03-2024.
 */


class NewsAdapter(private val mContext: Context, private var mNewsList: MutableList<News>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private lateinit var sharedPrefs: SharedPreferences

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_card)
        val sectionTextView: TextView = itemView.findViewById(R.id.section_card)
        val authorTextView: TextView = itemView.findViewById(R.id.author_card)
        val dateTextView: TextView = itemView.findViewById(R.id.date_card)
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnail_image_card)
        val shareImageView: ImageView = itemView.findViewById(R.id.share_image_card)
        val trailTextView: TextView = itemView.findViewById(R.id.trail_text_card)
        val cardView: CardView = itemView.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_card_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mNewsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext)

        setColorTheme(holder)
        setTextSize(holder)

        val currentNews = mNewsList[position]

        holder.titleTextView.text = currentNews.title
        holder.sectionTextView.text = currentNews.section

        if (currentNews.author == null) {
            holder.authorTextView.visibility = View.GONE
        } else {
            holder.authorTextView.visibility = View.VISIBLE
            holder.authorTextView.text = currentNews.author
        }

        holder.dateTextView.text = getTimeDifference(formatDate(currentNews.date))

        val trailTextHTML = currentNews.trailTextHtml
        holder.trailTextView.text = Html.fromHtml(Html.fromHtml(trailTextHTML).toString())

        holder.cardView.setOnClickListener {
            val newsUri = Uri.parse(currentNews.url)
            val websiteIntent = Intent(Intent.ACTION_VIEW, newsUri)
            mContext.startActivity(websiteIntent)
        }

        if (currentNews.thumbnail == null) {
            holder.thumbnailImageView.visibility = View.GONE
        } else {
            holder.thumbnailImageView.visibility = View.VISIBLE
            Glide.with(mContext.applicationContext)
                .load(currentNews.thumbnail)
                .into(holder.thumbnailImageView)
        }

        holder.shareImageView.setOnClickListener {
            shareData(currentNews)
        }
    }

    private fun setColorTheme(holder: ViewHolder) {
        val colorTheme = sharedPrefs.getString(
            mContext.getString(R.string.settings_color_key),
            mContext.getString(R.string.settings_color_default)
        )

        when (colorTheme) {
            mContext.getString(R.string.settings_color_white_value) -> {
                holder.titleTextView.setBackgroundResource(R.color.white)
                holder.titleTextView.setTextColor(Color.BLACK)
            }

            mContext.getString(R.string.settings_color_sky_blue_value),
            mContext.getString(R.string.settings_color_dark_blue_value),
            mContext.getString(R.string.settings_color_violet_value),
            mContext.getString(R.string.settings_color_light_green_value),
            mContext.getString(R.string.settings_color_green_value) -> {
                holder.titleTextView.setBackgroundResource(R.color.nav_bar_start)
                holder.titleTextView.setTextColor(Color.WHITE)
            }
        }
    }

    private fun setTextSize(holder: ViewHolder) {
        val textSize = sharedPrefs.getString(
            mContext.getString(R.string.settings_text_size_key),
            mContext.getString(R.string.settings_text_size_default)
        )

        when (textSize) {
            mContext.getString(R.string.settings_text_size_medium_value) -> {
                holder.titleTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp22)
                )
                holder.sectionTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp14)
                )
                holder.trailTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp16)
                )
                holder.authorTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp14)
                )
                holder.dateTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp14)
                )
            }

            mContext.getString(R.string.settings_text_size_small_value) -> {
                holder.titleTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp20)
                )
                holder.sectionTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp12)
                )
                holder.trailTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp14)
                )
                holder.authorTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp12)
                )
                holder.dateTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp12)
                )
            }

            mContext.getString(R.string.settings_text_size_large_value) -> {
                holder.titleTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp24)
                )
                holder.sectionTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp16)
                )
                holder.trailTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp18)
                )
                holder.authorTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp16)
                )
                holder.dateTextView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mContext.resources.getDimension(R.dimen.sp16)
                )
            }
        }
    }

    private fun shareData(news: News) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "${news.title} : ${news.url}")
        mContext.startActivity(
            Intent.createChooser(
                sharingIntent,
                mContext.getString(R.string.share_article)
            )
        )
    }

    private fun formatDate(dateStringUTC: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'", Locale.ENGLISH)
        val dateObject: Date?
        dateObject = try {
            simpleDateFormat.parse(dateStringUTC)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
        val df = SimpleDateFormat("MMM d, yyyy  h:mm a", Locale.ENGLISH)
        val formattedDateUTC = df.format(dateObject)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date?
        date = try {
            df.parse(formattedDateUTC)
            df.timeZone = TimeZone.getDefault()
            df.parse(formattedDateUTC)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
        return df.format(date)
    }

    private fun getDateInMillis(formattedDate: String): Long {
        val simpleDateFormat =
            SimpleDateFormat("MMM d, yyyy  h:mm a")
        val dateInMillis: Long
        val dateObject: Date
        try {
            dateObject = simpleDateFormat.parse(formattedDate)
            dateInMillis = dateObject.time
            return dateInMillis
        } catch (e: ParseException) {
            Log.e("Problem parsing date", e.message!!)
            e.printStackTrace()
        }
        return 0
    }

    private fun getTimeDifference(formattedDate: String): CharSequence {
        val currentTime = System.currentTimeMillis()
        val publicationTime: Long = getDateInMillis(formattedDate)
        return DateUtils.getRelativeTimeSpanString(
            publicationTime, currentTime,
            DateUtils.SECOND_IN_MILLIS
        )
    }

    fun clearAll() {
        mNewsList.clear()
        notifyDataSetChanged()
    }

    /**
     * Add  a list of [News]
     * @param newsList is the list of news, which is the data source of the adapter
     */
    fun addAll(newsList: MutableList<News>) {
        mNewsList.clear()
        mNewsList.addAll(newsList)
        notifyDataSetChanged()
    }

}