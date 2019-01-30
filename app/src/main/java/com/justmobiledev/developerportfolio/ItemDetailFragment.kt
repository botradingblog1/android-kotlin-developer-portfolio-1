package com.justmobiledev.developerportfolio

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justmobiledev.developerportfolio.portfolio.PortfolioContent
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: PortfolioContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = PortfolioContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
            }
            if (it.containsKey(ARG_ITEM_NAME)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                var title = it.getString(ARG_ITEM_NAME)

                activity?.toolbar_layout?.title = title
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var rootView : View?


        if (item?.id.equals(ItemListActivity.MENU_OBJECTIVE_ID))
        {
            rootView = inflater.inflate(R.layout.fragment_objective, container, false)
        }
        else if (item?.id.equals(ItemListActivity.MENU_EXPERIENCE_ID))
        {
            rootView = inflater.inflate(R.layout.fragment_experience, container, false)
        }
        else if (item?.id.equals(ItemListActivity.MENU_EDUCATION_ID))
        {
            rootView = inflater.inflate(R.layout.fragment_education, container, false)
        }
        else if (item?.id.equals(ItemListActivity.MENU_CERTIFICATES_ID))
        {
            rootView = inflater.inflate(R.layout.fragment_certificates, container, false)
        }
        else if (item?.id.equals(ItemListActivity.MENU_ANDROID_APPS_ID))
        {
            rootView = inflater.inflate(R.layout.fragment_android_apps, container, false)
        }
        else if (item?.id.equals(ItemListActivity.MENU_IOS_APPS_ID))
        {
            rootView = inflater.inflate(R.layout.fragment_ios_apps, container, false)
        }
        else {
            rootView = inflater.inflate(R.layout.fragment_evolution_steps, container, false)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
        const val ARG_ITEM_NAME = "item_name"
    }
}
