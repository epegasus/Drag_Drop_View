package com.kotlin.dragdrop

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kotlin.dragdrop.databinding.ActivityMainBinding
import com.kotlin.dragdrop.utils.ViewToolUtils

@SuppressLint("ClickableViewAccessibility")
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewToolUtils: ViewToolUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewToolUtils()

        binding.root.setOnClickListener { onRootClick(true) }
        binding.clContainerMain.setOnTouchListener(viewToolUtils.onObjectTouchListener)
        binding.sivExpandMain.setOnTouchListener(viewToolUtils.onExpandTouchListener)
        binding.sivDeleteMain.setOnClickListener { onDeleteClick() }
        binding.btnAddMain.setOnClickListener { onAddClick() }
    }

    private fun initViewToolUtils() {
        viewToolUtils = ViewToolUtils(binding.clContainerMain, object : ViewToolUtils.OnKeyDownTouchEventListener {
            override fun onKeyDown() {
                onRootClick(false)
            }
        })
    }

    private fun onRootClick(isRoot: Boolean) {
        if (isRoot) {
            binding.groupMain.visibility = GONE
            binding.flContainerMain.background = null
        } else {
            binding.groupMain.visibility = VISIBLE
            binding.flContainerMain.background = ContextCompat.getDrawable(this, R.drawable.outline)
        }
    }

    private fun onAddClick() {
        binding.clContainerMain.visibility = VISIBLE
        binding.btnAddMain.visibility = GONE
        binding.sivSignatureMain.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_logo))
    }

    private fun onDeleteClick() {
        binding.clContainerMain.visibility = GONE
        binding.btnAddMain.visibility = VISIBLE
        binding.sivSignatureMain.background = null
    }
}