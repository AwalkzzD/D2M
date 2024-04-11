package com.example.d2m.screens.utils.base_classes

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.d2m.R

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int, private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    private lateinit var binding: VB
    private lateinit var viewModel: VM

    private var toolbar: Toolbar? = null

    /**
     * protected variable to use them in subclasses inheriting BaseActivity
     * */
    protected val activityBinding: VB get() = binding
    protected val activityViewModel: VM get() = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)

        viewModel = getViewModel()

        val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleSmall)
        progressBar.isIndeterminate = true
        progressBar.indeterminateDrawable.setColorFilter(
            resources.getColor(R.color.black, null),
            PorterDuff.Mode.SRC_IN
        )

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        progressBar.layoutParams = layoutParams
        addContentView(progressBar, layoutParams)

        viewModel.isLoading.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun getViewModel(): VM {
        if (!::viewModel.isInitialized) {
            viewModel = ViewModelProvider(this)[viewModelClass]
        }
        return viewModel
    }

    fun setupToolbar(toolbar: Toolbar, title: String, backButtonEnabled: Boolean = true) {
        this.toolbar = toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(backButtonEnabled)
            setTitle(title)
        }
    }

    fun customiseToolbar(title: String, backButtonEnabled: Boolean = true) {
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(backButtonEnabled)
            setTitle(title)
        }
    }

    fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

}