package com.cmy.kotlinsimple.dialog.rating

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.*

/**
 * step1:拿到父视图的宽高和星星的数量,用来计算每一个星星的位置（原点的坐标）
 * step2:拿到原点的坐标后开始计算每个星星的path
 */
@Suppress("UNUSED_PARAMETER")
class RatingStarView : View {
    private var mContext: Context
    private lateinit var mAttrs: AttributeSet
    private var mDefStyle: Int? = null

    constructor(
        context: Context
    ) : super(context) {
        this.mContext = context
    }


    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        this.mContext = context
        this.mAttrs = attrs
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        this.mContext = context
        this.mAttrs = attrs
        this.mDefStyle = defStyle
    }

    private lateinit var mCanvas: Canvas

    /**
     * 画笔
     */
    private var paint: Paint = Paint()

    /**
     * 路径
     */
    private var path = Path()

    /**
     * 星星的数量，默认5个
     */
    private var starsNum = 5

    /**
     * 星星的默认颜色
     */
    private var starBackgroundColor = Color.GRAY

    /**
     * 星星的选中颜色
     */
    private val starForegroundColor = Color.BLACK

    /**
     * 星星圆心的x坐标
     */
    private var starsX = 0

    /**
     * 星星圆心的y坐标
     */
    private var starsY = 0

    /**
     * 星星的圆心坐标集合
     */
    private var starsLocationMap: LinkedHashMap<Int, Int> = LinkedHashMap()


    init {
        //画笔的颜色
        paint.color = starForegroundColor
        //画笔的模式
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = 10f
    }

    /**
     * 测量view的宽和高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //两个图形的x轴间距
        val oneWidth = measuredWidth / starsNum
        //Y轴的坐标
        starsY = measuredHeight / 2
        for (i in 1..starsNum) {
            //x轴的坐标
            starsX = (oneWidth / 2) + (oneWidth * (i - 1))

            starsLocationMap[starsX] = starsY
        }

    }


    /**
     * 视图的绘制工作
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mCanvas = canvas!!
        calcPath(100f, starsLocationMap, mCanvas)
//        for (en in starsLocationMap.entries){
//            canvas!!.drawCircle(en.key.toFloat(), en.value.toFloat(), 100F,paint)
//        }

    }


    /**
     * 记第一个五角星的外切圆/内切圆圆心坐标为 x = starsLocationMap.key y = starsLocationMap.value
     * 外切圆半径决定了五角星的大小
     * 计算第一个五角星的外切圆相交点的坐标,从顶点开始顺时针转动分别记录为A,B,C,D,E
     * 同样,内切圆坐标记为a,b,c,d,e
     * 如果有多个五角星,那么A-e的x轴坐标直接加上两个五角星之间的间距(val oneWidth = measuredWidth/starsNum)就行,不用重复计算
     */

    private fun calcPath(
        radius: Float,
        starsLocationMap: LinkedHashMap<Int, Int>,
        canvas: Canvas?
    ) {
        canvas!!.drawCircle(108f, 187f, 80f, paint)
        canvas.run {
            drawCircle(324f, 187f, 80f, paint)
            drawCircle(540f, 187f, 80f, paint)
            drawCircle(756f, 187f, 80f, paint)
            drawCircle(972f, 187f, 80f, paint)
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
                stuffStars(event.x)
            }
            MotionEvent.ACTION_UP -> {
                performClick()
            }
        }

        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun stuffStars(x: Float) {
        mCanvas.drawPoint(x, 187f, paint)
        Log.d("move", "stuffStars: $x")

    }

}