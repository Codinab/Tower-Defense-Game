import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.towerdefense.Physics2d.primitives.Collider2D
import org.joml.Vector2f

class GameObjectView(context: Context, viewGroup: ViewGroup, var collider2D: Collider2D) : View(context) {

    init {
        setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
        }
        layoutParams = ViewGroup.LayoutParams(200, 200)
    }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.RED
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), (width / 2).toFloat(), paint)
    }

    fun setPosition(position: Vector2f) {
        x = position.x
        y = position.y
        collider2D.body.position = position
    }

    fun getPosition(): Vector2f {
        return collider2D.body.position
    }
}
