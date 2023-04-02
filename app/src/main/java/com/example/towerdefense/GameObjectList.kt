import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.gameObjects.GameObject
import org.joml.Vector2f

class GameObjectList(private val gameObjects: MutableList<GameObject> = mutableListOf()) :
    MutableList<GameObject> by gameObjects {
    fun update() : ArrayList<Enemy>{
        val enemies = ArrayList<Enemy>()
        gameObjects.forEach {
            it.update()
            if (it is Enemy && it.getDelete()) {
                enemies.add(it)
                gameObjects.remove(it)
            }
        }
        return enemies
    }

    fun draw(canvas: Canvas) {
        gameObjects.forEach { it.draw(canvas) }
    }

    fun getClicked(position: Vector2f?): GameObjectList {
        val clickedGameObjects = GameObjectList()
        gameObjects.forEach {
            if (it.isClicked(position)) {
                clickedGameObjects.add(it)
                return clickedGameObjects
            }
        }
        return clickedGameObjects
    }

    fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return gameObjects.any { it.onTouchEvent(event, position) }
    }

    fun getMovable(): GameObjectList {
        val movableGameObjects = GameObjectList()
        gameObjects.forEach { if (it.movable.get()) movableGameObjects.add(it) }
        return movableGameObjects
    }

    fun getGameObjects(): List<GameObject> {
        return gameObjects
    }

    override fun toString(): String {
        var string = ""
        gameObjects.forEach { string += it.toString() + " " }
        return string
    }


}