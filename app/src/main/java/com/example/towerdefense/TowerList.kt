import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.gameObjects.tower.Tower
import org.joml.Vector2f
import java.util.concurrent.CopyOnWriteArrayList

class TowerList(private val towers: CopyOnWriteArrayList<Tower> = CopyOnWriteArrayList()) :
    MutableList<Tower> by towers {

    private var isPaused = false

    fun pause() {
        isPaused = true
    }

    fun resume() {
        isPaused = false
    }

    fun update(): ArrayList<Enemy> {
        val enemies = ArrayList<Enemy>()
        if (!isPaused) {
            towers.forEach {
                it.update()
                if (it.getDestroyed()) towers.remove(it)
            }
        }
        return enemies
    }

    override fun add(element: Tower): Boolean {
        return towers.add(element)
    }

    override fun remove(element: Tower): Boolean {
        return towers.remove(element)
    }

    fun draw(canvas: Canvas) {
        towers.forEach { it.draw(canvas) }
    }

    fun getClicked(position: Vector2f?): TowerList {
        val clickedTowers = TowerList()
        towers.forEach {
            if (it.isClicked(position)) {
                clickedTowers.add(it)
                return clickedTowers
            }
        }
        return clickedTowers
    }

    fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return towers.any { it.onTouchEvent(event, position) }
    }

    fun getMovable(): TowerList {
        val movableTowers = TowerList()
        towers.forEach { if (it.movable.get()) movableTowers.add(it) }
        return movableTowers
    }

    fun getTowers(): List<Tower> {
        return towers
    }

    override fun toString(): String {
        var string = "TowerList: {"
        towers.forEach { string += "$it " }
        string += "}"
        return string
    }
}
