package com.hectordelgado.animationstation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * A simple application that lets users animate various properties of an ImageView.
 *
 * <p>Gathers data from various SeekBar widgets to animate common properties of the ImageView.
 * The data is used in an ObjectAnimator which is then added to an AnimatorSet to play all
 * animations simultaneously.
 *
 * Created on August 19, 2019.
 * Copyright © $2019 Hector Delgado. All rights reserved.
 *
 * @author Hector Delgado
 *
 */
class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private var oldAlpha = 0f
    private var newAlpha = 0f
    private var oldRotation = 0f
    private var newRotation = 0f
    private var oldScale = 0f
    private var newScale = 0f
    private var oldXPosition = 0f
    private var newXPosition = 0f
    private var oldYPosition = 0f
    private var newYPosition = 0f
    private var alphaChanged = false
    private var rotationChanged = false
    private var scaleChanged = false
    private var xPositionChanged = false
    private var yPositionChanged = false

    private lateinit var mConstraintLayout: ConstraintLayout
    private lateinit var imageViewLayout: ConstraintLayout
    private lateinit var mImageView: ImageView
    private lateinit var alphaTextView: TextView
    private lateinit var alphaSeekBar: SeekBar
    private lateinit var rotationTextView: TextView
    private lateinit var rotationSeekBar: SeekBar
    private lateinit var scaleTextView: TextView
    private lateinit var scaleSeekBar: SeekBar
    private lateinit var xPositionTextView: TextView
    private lateinit var xPositionSeekBar: SeekBar
    private lateinit var yPositionTextView: TextView
    private lateinit var yPositionSeekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mConstraintLayout = findViewById(R.id.mConstraintLayout)
        imageViewLayout = findViewById(R.id.imageViewLayout)
        mImageView = findViewById(R.id.mImageView)
        alphaTextView = findViewById(R.id.alphaTextView)
        alphaSeekBar = findViewById(R.id.alphaSeekBar)
        rotationTextView = findViewById(R.id.rotationTextView)
        rotationSeekBar = findViewById(R.id.rotationSeekBar)
        scaleTextView = findViewById(R.id.scaleTextView)
        scaleSeekBar = findViewById(R.id.scaleSeekBar)
        xPositionTextView = findViewById(R.id.xPositionTextView)
        xPositionSeekBar = findViewById(R.id.xPositionSeekBar)
        yPositionTextView = findViewById(R.id.yPositionTextView)
        yPositionSeekBar = findViewById(R.id.yPositionSeekBar)

        alphaSeekBar.setOnSeekBarChangeListener(this)
        rotationSeekBar.setOnSeekBarChangeListener(this)
        scaleSeekBar.setOnSeekBarChangeListener(this)
        xPositionSeekBar.setOnSeekBarChangeListener(this)
        yPositionSeekBar.setOnSeekBarChangeListener(this)

        rotationSeekBar.max = 360
        scaleSeekBar.max = 150
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        xPositionSeekBar.max = mConstraintLayout.measuredWidth - mImageView.measuredWidth
        yPositionSeekBar.max = imageViewLayout.measuredHeight - mImageView.measuredHeight
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            when (seekBar?.id) {
                R.id.alphaSeekBar -> {
                    alphaChanged = true
                    oldAlpha = mImageView.alpha
                    newAlpha = progress.toFloat() / 100f
                    val newText = "${resources.getString(R.string.textView_text_alpha)} $newAlpha"
                    alphaTextView.text = newText
                }
                R.id.rotationSeekBar -> {
                    rotationChanged = true
                    oldRotation = mImageView.rotation
                    newRotation = progress.toFloat()
                    val newText = "${resources.getString(R.string.textView_text_rotation)} $newRotation"
                    rotationTextView.text = newText
                }
                R.id.scaleSeekBar -> {
                    scaleChanged = true
                    oldScale = mImageView.scaleX
                    newScale = progress.toFloat()/ 100f
                    val newText = "${resources.getString(R.string.textView_text_scale)} $newScale"
                    scaleTextView.text = newText
                }
                R.id.xPositionSeekBar -> {
                    xPositionChanged = true
                    oldXPosition = mImageView.x
                    newXPosition = progress.toFloat()
                    val newText = "${resources.getString(R.string.textView_text_xPosition)} $newXPosition"
                    xPositionTextView.text = newText

                }
                R.id.yPositionSeekBar -> {
                    yPositionChanged = true
                    oldYPosition = mImageView.y
                    newYPosition = progress.toFloat()
                    val newText = "${resources.getString(R.string.textView_text_yPosition)} $newYPosition"
                    yPositionTextView.text = newText
                }
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) { }

    override fun onStopTrackingTouch(seekBar: SeekBar?) { }

    fun startButtonClicked(view: View) {
        when (view.id) {
            R.id.startButton -> {
                val animatorSet = AnimatorSet()
                animatorSet.interpolator = BounceInterpolator()

                if (alphaChanged) {
                    val alphaObjectAnimator = ObjectAnimator.ofFloat(mImageView, "alpha", oldAlpha, newAlpha)
                    animatorSet.play(alphaObjectAnimator)
                    alphaChanged = false
                }

                if (rotationChanged) {
                    val rotationObjectAnimator = ObjectAnimator.ofFloat(mImageView, "rotation", oldRotation, newRotation)
                    animatorSet.play(rotationObjectAnimator)
                    rotationChanged = false
                }

                if (scaleChanged) {
                    val scaleXObjectAnimator = ObjectAnimator.ofFloat(mImageView, "scaleX", oldScale, newScale)
                    val scaleYObjectAnimator = ObjectAnimator.ofFloat(mImageView, "scaleY", oldScale, newScale)
                    animatorSet.playTogether(scaleXObjectAnimator, scaleYObjectAnimator)
                    scaleChanged = false
                }

                if (xPositionChanged) {
                    val xPositionObjectAnimator = ObjectAnimator.ofFloat(mImageView, "x", oldXPosition, newXPosition)
                    animatorSet.play(xPositionObjectAnimator)
                    xPositionChanged = false
                }

                if (yPositionChanged) {
                    val yPositionObjectAnimator = ObjectAnimator.ofFloat(mImageView, "y", oldYPosition, newYPosition)
                    animatorSet.play(yPositionObjectAnimator)
                    yPositionChanged = false
                }

                animatorSet.start()
            }
        }
    }


}
