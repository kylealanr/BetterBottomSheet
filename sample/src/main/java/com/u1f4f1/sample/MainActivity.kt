package com.u1f4f1.sample

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.u1f4f1.powerbottomsheet.bottomsheet.BottomSheet
import com.u1f4f1.powerbottomsheet.bottomsheet.BottomSheetState
import com.u1f4f1.powerbottomsheet.coordinatorlayoutbehaviors.AnchorPointBottomSheetBehavior
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import android.support.v7.app.AppCompatDelegate



class MainActivity : AppCompatActivity() {

    internal var bottomSheetBehavior: AnchorPointBottomSheetBehavior<*>? = null
    internal var sampleAdapter: SampleAdapter? = null

    private var bottomSheet: BottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activeColor = ContextCompat.getColor(this, R.color.colorAccent)
        val inactiveColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        bottomSheet = findViewById(R.id.bottom_sheet)

        bottomSheetBehavior = AnchorPointBottomSheetBehavior.from<View>(bottomSheet!!)

        bottomSheet!!.setActivatedListener(object : AnchorPointBottomSheetBehavior.OnSheetActivatedListener {
            override fun isActivated(isActive: Boolean) {
                if (isActive) {
                    bottomSheet!!.setBackgroundColor(activeColor)
                } else {
                    bottomSheet!!.setBackgroundColor(inactiveColor)
                }
            }
        })

        button.setOnClickListener({ _ -> onClick() })
    }

    internal fun onClick() {
        if (sampleAdapter == null) {
            sampleAdapter = SampleAdapter(bottomSheetBehavior)

            bottomSheet!!.setupRecyclerview(sampleAdapter!!)
            sampleAdapter!!.addFakeViews()
        }

        bottomSheet!!.setState(BottomSheetState.STATE_COLLAPSED)

        bottomSheet!!.postDelayed({ sampleAdapter!!.dataLoaded() }, 400)
    }

    override fun onBackPressed() {
        if (bottomSheetBehavior?.state === BottomSheetState.STATE_EXPANDED || bottomSheetBehavior?.state === BottomSheetState.STATE_ANCHOR_POINT) {
            bottomSheetBehavior?.state = BottomSheetState.STATE_COLLAPSED
            return
        }

        if (bottomSheetBehavior?.state === BottomSheetState.STATE_COLLAPSED) {
            bottomSheetBehavior?.state = BottomSheetState.STATE_HIDDEN
            return
        }

        if (bottomSheetBehavior?.state === BottomSheetState.STATE_HIDDEN) {
            bottomSheetBehavior?.state = BottomSheetState.STATE_COLLAPSED
            return
        }

        super.onBackPressed()
    }
}
