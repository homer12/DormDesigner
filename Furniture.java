public class Furniture {
    private PApplet processing;
    private PImage image;
    private float[] position;
    private boolean isDragging;
    private float deltaX;
    private float deltaY;
    private int rotations;
    private String type;

    // initializes the fields of a new bed object positioned in the center of the display
    public Furniture(String type,PApplet processing) {
        this.processing = processing;
        image = processing.loadImage("images/"+type+".png");
        position = new float[2];
        position[0] = processing.width/2;
        position[1] = processing.height/2;
        isDragging = false;

        deltaX = 0;
        deltaY = 0;

        rotations = 0;

        this.type = type;
    }

    // draws this bed at its current position
    public void update() {
        if( isDragging ){
            position[0] = processing.mouseX + deltaX;
            position[1] = processing.mouseY + deltaY;
        }
        processing.image(image, position[0], position[1], rotations*PApplet.PI/2);
    }

    // used to start dragging the bed, when the mouse is over this bed when it is pressed
    public void mouseDown() {
        isDragging = true;
        deltaX = position[0] - processing.mouseX;
        deltaY = position[1] - processing.mouseY;
    }

    // used to indicate that the bed is no longer being dragged
    public void mouseUp() {
        isDragging = false;
    }

    // helper method to determine whether the mouse is currently over this bed
    public boolean isMouseOver() {
        float halfWidth,halfHeight;

        if( rotations % 2 == 0 ){
            halfWidth = image.width/2;
            halfHeight = image.height/2;
        }else{
            halfWidth = image.height/2;
            halfHeight = image.width/2;
        }

        float judgeX = processing.mouseX - position[0];
        if(judgeX<0)
            judgeX = -judgeX;
        float judgeY = processing.mouseY - position[1];
        if(judgeY<0)
            judgeY = -judgeY;
        if(judgeX <= halfWidth && judgeY <= halfHeight)
            return true;
        else
            return false;
    }

    public void rotate(){
        rotations = ( rotations+1 ) % 4;
    }
}

