public class Main {
    private PApplet processing;
    private PImage backgroundImage;
    private Bed[] beds;
    private CreateBedButton bedButton;

    public Main(PApplet processing) {
        this.processing = processing;
        //System.out.println("Constructor of Main");
        setup();
    }

    public static void main(String[] args) {
        Utility.startApplication();
        //System.out.println("main()");
    }

    public void setup(){
        //System.out.println("setup");

        // Background color
        processing.background(100, 150, 250);

        // Background image
        backgroundImage = processing.loadImage("images/background.png");
        processing.image(backgroundImage, processing.width/2, processing.height/2);

        // Bed
        beds = new Bed[6];
        for( Bed bed : beds )
            bed = null;

        // Bed button
        bedButton = new CreateBedButton(50,24,processing);
    }

    public void update(){

        //System.out.println("update");

        // Background color
        processing.background(100, 150, 250);

        // Background image
        processing.image(backgroundImage, processing.width/2, processing.height/2);

        // Update each bed
        for( int i=0; i<beds.length; i++){
            if( beds[i] != null )
                beds[i].update();
        }

        // Update bed button
        bedButton.update();
    }


    public void mouseDown(){
        //System.out.println("mouse down");
        for( int i=beds.length-1; i>=0; i--){
            if( beds[i] != null && beds[i].isMouseOver() ){
                beds[i].mouseDown();
                return;
            }
        }
    }

    public void mouseUp() {
        //System.out.println("mouse up");
        for( int i=0; i<beds.length; i++){
            if( beds[i] != null && beds[i].isMouseOver() ){
                beds[i].mouseUp();
            }
        }
    }

    public void keyPressed(){
        //System.out.println("keyPressed");
        switch(processing.key){
            case 'b':
            case 'B':
                for( int i=0; i<beds.length; i++){
                    if( beds[i] == null ){
                        beds[i] = new Bed(processing);
                        beds[i].update();
                        return;
                    }
                }
                break;
            case 'd':
            case 'D':
                for( int i=beds.length-1; i>=0; i--){
                    if( beds[i] != null && beds[i].isMouseOver() ){
                        beds[i] = null;
                        return;
                    }
                }
                break;
            case 'r':
            case 'R':
                for( int i=beds.length-1; i>=0; i--){
                    if( beds[i] != null && beds[i].isMouseOver() ) {
                        beds[i].rotate();
                        return;
                    }
                }
                break;
        }

    }
}

class Bed{
    private PApplet processing;
    private PImage image;
    private float[] position;
    private boolean isDragging;
    private float deltaX;
    private float deltaY;
    private int rotations;

    // initializes the fields of a new bed object positioned in the center of the display
    public Bed(PApplet processing) {
        this.processing = processing;
        image = processing.loadImage("images/bed.png");
        position = new float[2];
        position[0] = processing.width/2;
        position[1] = processing.height/2;
        isDragging = false;

        deltaX = 0;
        deltaY = 0;

        rotations = 0;
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
