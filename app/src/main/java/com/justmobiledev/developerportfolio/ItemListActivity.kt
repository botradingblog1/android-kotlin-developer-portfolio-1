package com.justmobiledev.developerportfolio

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.justmobiledev.developerportfolio.about.AboutFragment

import com.justmobiledev.developerportfolio.portfolio.PortfolioContent
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*

// Main Activity
class ItemListActivity : AppCompatActivity() {

    companion object {
        val MENU_OBJECTIVE_ID = "1"
        val MENU_EDUCATION_ID = "2"
        val MENU_EXPERIENCE_ID = "3"
        val MENU_CERTIFICATES_ID = "4"
        val MENU_ANDROID_APPS_ID = "5"
        val MENU_IOS_APPS_ID = "6"
    }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/layout-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)

        // Load Detail fragment
        // Tablet Layout
        if (twoPane) {
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ItemDetailFragment.ARG_ITEM_ID, "0")
                }
            }
            this.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()

        } else {
            // Phone Layout
            //val intent = Intent(this, ItemListActivity::class.java)
            //this.startActivity(intent)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val menuItems: MutableList<PortfolioMenuItem> = ArrayList()

        // Create menu items
        menuItems.add(0, PortfolioMenuItem(MENU_IOS_APPS_ID, "iOS Apps", ""))
        menuItems.add(0, PortfolioMenuItem(MENU_ANDROID_APPS_ID, "Android Apps", ""))
        menuItems.add(0, PortfolioMenuItem(MENU_CERTIFICATES_ID, "Certificates", ""))
        menuItems.add(0, PortfolioMenuItem(MENU_EXPERIENCE_ID, "Professional Experience", ""))
        menuItems.add(0, PortfolioMenuItem(MENU_EDUCATION_ID, "Education", ""))
        menuItems.add(0, PortfolioMenuItem(MENU_OBJECTIVE_ID, "Objective", ""))

        // Set Menu items
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, menuItems, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                        private val values: List<PortfolioMenuItem>,
                                        private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as PortfolioMenuItem

                // Tablet Layout
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                            putString(ItemDetailFragment.ARG_ITEM_NAME, item.title)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()

                } else {
                    // Phone Layout
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        putExtra(ItemDetailFragment.ARG_ITEM_NAME, item.title)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.id.toString()
            holder.contentView.text = item.title

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
}
