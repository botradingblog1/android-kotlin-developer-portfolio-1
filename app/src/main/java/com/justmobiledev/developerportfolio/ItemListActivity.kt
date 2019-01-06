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

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    companion object {
        val MENU_OBJECTIVE_ID = "1"
        val MENU_ABOUT_ID = "2"
        val MENU_EDUCATION_ID = "3"
        val MENU_EXPERIENCE_ID = "4"
        val MENU_CERTIFICATES_ID = "5"
        val MENU_ANDROID_APPS_ID = "6"
        val MENU_IOS_APPS_ID = "7"
        val MENU_XAMARIN_APPS_ID = "8"
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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)

        // Load Detail fragment
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
            val intent = Intent(this, ItemDetailActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_ITEM_ID, "1")
            }
            this.startActivity(intent)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val menuItems: MutableList<PortfolioMenuItem> = ArrayList()

        // Create your menu items
        //val item6 = PortfolioMenuItem(MENU_XAMARIN_APPS_ID, "Xamarin Apps", "")
        //menuItems.add(0, item6)

        val item5 = PortfolioMenuItem(MENU_IOS_APPS_ID, "iOS Apps", "")
        menuItems.add(0, item5)

        val item4 = PortfolioMenuItem(MENU_ANDROID_APPS_ID, "Android Apps", "")
        menuItems.add(0, item4)

        val item3 = PortfolioMenuItem(MENU_CERTIFICATES_ID, "Certificates", "")
        menuItems.add(0, item3)


        val item1 = PortfolioMenuItem(MENU_EXPERIENCE_ID, "Professional Experience", "")
        menuItems.add(0, item1)

        val item2 = PortfolioMenuItem(MENU_EDUCATION_ID, "Education", "")
        menuItems.add(0, item2)

        val item0 = PortfolioMenuItem(MENU_OBJECTIVE_ID, "Objective", "")
        menuItems.add(0, item0)


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

                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id.toString())
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()

                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
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
