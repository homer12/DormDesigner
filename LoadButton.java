public class LoadButton {
    private static final int WIDTH = 96;
    private static final int HEIGHT = 32;

    private PApplet processing;
    private float[] position;
    private String label;

    public LoadButton(float x, float y, PApplet processing) {
        position = new float[2];
        position[0] = x;    //750
        position[1] = y;    //24
        this.processing = processing;
        label = "Load Room";
    }

    public void update() {
        // Fill color for rect
        if( isMouseOver() )
            processing.fill(100);
        else
            processing.fill(200);

        // Draw rect
        processing.rect(position[0]-WIDTH/2,position[1]-HEIGHT/2,
                position[0]+WIDTH/2,position[1]+HEIGHT/2);

        // Fill color for label
        processing.fill(0);
        // Draw label
        processing.text(label,position[0],position[1]);

    }

    public Furniture mouseDown( String type, float x, float y, int rotat, PApplet processing ){
        return new Furniture(type,x,y,rotat,processing);
    }


    public boolean isMouseOver() {
        float judgeX = processing.mouseX - position[0];
        if(judgeX<0)
            judgeX = -judgeX;
        float judgeY = processing.mouseY - position[1];
        if(judgeY<0)
            judgeY = -judgeY;
        if(judgeX <= WIDTH/2 && judgeY <= HEIGHT/2)
            return true;
        else
            return false;
    }
}
