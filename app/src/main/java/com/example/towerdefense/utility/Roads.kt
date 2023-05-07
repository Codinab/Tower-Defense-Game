import com.example.towerdefense.utility.Road
import org.joml.Vector2i
import com.example.towerdefense.utility.Direction2D

enum class Roads(val road: Road) {
    ROAD_1(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 10)
        addLine(Direction2D.DOWN, 10)
        addLine(Direction2D.RIGHT, 10)
    }),
    
    ROAD_2(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 15)
        addLine(Direction2D.DOWN, 5)
        addLine(Direction2D.UP, 5)
    }),
    
    ROAD_3(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 10)
        addLine(Direction2D.DOWN, 5)
        addLine(Direction2D.RIGHT, 5)
        addLine(Direction2D.DOWN, 5)
        addLine(Direction2D.RIGHT, 5)
    }),
    
    ROAD_4(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 5)
        addLine(Direction2D.UP, 5)
        addLine(Direction2D.RIGHT, 5)
        addLine(Direction2D.DOWN, 5)
        addLine(Direction2D.RIGHT, 5)
        addLine(Direction2D.UP, 5)
        addLine(Direction2D.RIGHT, 5)
    }),
    
    ROAD_5(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 2)
        addLine(Direction2D.DOWN, 2)
        addLine(Direction2D.UP, 2)
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.DOWN, 2)
        addLine(Direction2D.RIGHT, 2)
        addLine(Direction2D.UP, 2)
        addLine(Direction2D.RIGHT, 4)
    }),
    ROAD_6(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 6)
        addLine(Direction2D.UP, 2)
        addLine(Direction2D.RIGHT, 6)
        addLine(Direction2D.DOWN, 2)
        addLine(Direction2D.RIGHT, 6)
    }),
    ROAD_7(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 8)
        addLine(Direction2D.DOWN, 2)
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.UP, 4)
        addLine(Direction2D.RIGHT, 4)
    }),
    
    ROAD_8(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.DOWN, 2)
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.UP, 2)
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.DOWN, 2)
        addLine(Direction2D.RIGHT, 4)
    }),
    
    ROAD_9(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 3)
        addLine(Direction2D.UP, 3)
        addLine(Direction2D.RIGHT, 5)
        addLine(Direction2D.DOWN, 3)
        addLine(Direction2D.RIGHT, 5)
    }),
    ROAD_10(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 5)
        addLine(Direction2D.UP, 3)
        addLine(Direction2D.RIGHT, 5)
        addLine(Direction2D.DOWN, 3)
        addLine(Direction2D.RIGHT, 5)
    }),
    
    ROAD_11(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 10)
        addLine(Direction2D.UP, 2)
        addLine(Direction2D.RIGHT, 5)
        addLine(Direction2D.DOWN, 2)
        addLine(Direction2D.RIGHT, 10)
    }),
    
    ROAD_12(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 3)
        addLine(Direction2D.UP, 3)
        addLine(Direction2D.RIGHT, 3)
        addLine(Direction2D.DOWN, 3)
        addLine(Direction2D.RIGHT, 3)
        addLine(Direction2D.UP, 3)
        addLine(Direction2D.RIGHT, 3)
    }),
    
    ROAD_13(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.DOWN, 4)
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.UP, 4)
        addLine(Direction2D.RIGHT, 4)
    }),
    
    ROAD_14(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 6)
        addLine(Direction2D.DOWN, 3)
        addLine(Direction2D.RIGHT, 3)
        addLine(Direction2D.UP, 3)
        addLine(Direction2D.RIGHT, 6)
    }),
    
    ROAD_15(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 2)
        addLine(Direction2D.UP, 4)
        addLine(Direction2D.RIGHT, 6)
        addLine(Direction2D.DOWN, 4)
        addLine(Direction2D.RIGHT, 2)
    }),
    
    ROAD_16(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 3)
        addLine(Direction2D.UP, 4)
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.DOWN, 4)
        addLine(Direction2D.RIGHT, 3)
    }),
    
    ROAD_17(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.DOWN, 2)
        addLine(Direction2D.RIGHT, 4)
        addLine(Direction2D.UP, 4)
        addLine(Direction2D.RIGHT, 4)
    }),
    
    ROAD_18(Road(Vector2i(0, 0)).apply {
        addLine(Direction2D.RIGHT, 8)
        addLine(Direction2D.DOWN, 3)
        addLine(Direction2D.RIGHT, 3)
        addLine(Direction2D.UP, 3)
        addLine(Direction2D.RIGHT, 8)
    }),
}
