public class Main {
    private PApplet processing;
    private PImage backgroundImage;
    private Furniture[] furni;
    private CreateBedButton bedButton;
    private CreateSofaButton sofaButton;
    private SaveButton saveButton;
    private LoadButton loadButton;

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

        // Furniture
        furni = new Furniture[6];
        for( Furniture fur : furni)
            fur = null;

        // Bed button
        bedButton = new CreateBedButton(50,24,processing);

        // Sofa Button
        sofaButton = new CreateSofaButton(150,24,processing);

        // Save Button
        saveButton = new SaveButton(650,24,processing);

        // Load Button
        loadButton = new LoadButton(750,24,processing);
    }

    public void update(){

        //System.out.println("update");

        // Background color
        processing.background(100, 150, 250);

        // Background image
        processing.image(backgroundImage, processing.width/2, processing.height/2);

        // Update each bed
        for(int i = 0; i< furni.length; i++){
            if( furni[i] != null )
                furni[i].update();
        }

        // Update bed button
        bedButton.update();

        // Update sofa button
        sofaButton.update();

        // Update save button
        saveButton.update();

        // Update load button
        loadButton.update();
    }


    public void mouseDown(){
        //System.out.println("mouse down");
        if( bedButton.isMouseOver() ){
            for(int i = 0; i< furni.length; i++ ){
                if( furni[i] == null ){
                   Furniture bed = bedButton.mouseDown();
                   if( bed != null )    // If it's not null
                       furni[i] = bed;
                   break;
                }
            }
            return;
        }

        if( sofaButton.isMouseOver() ){
            for(int i = 0; i< furni.length; i++ ){
                if( furni[i] == null ){
                    Furniture sofa = sofaButton.mouseDown();
                    if( sofa != null )    // If it's not null
                        furni[i] = sofa;
                    break;
                }
            }
            return;
        }

        for(int i = furni.length-1; i>=0; i--){
            if( furni[i] != null && furni[i].isMouseOver() ){
                furni[i].mouseDown();
                return;
            }
        }
    }

    public void mouseUp() {
        //System.out.println("mouse up");
        for(int i = 0; i< furni.length; i++){
            if( furni[i] != null && furni[i].isMouseOver() ){
                furni[i].mouseUp();
            }
        }
    }

    public void keyPressed(){
        //System.out.println("keyPressed");
        switch(processing.key){
            case 'd':
            case 'D':
                for(int i = furni.length-1; i>=0; i--){
                    if( furni[i] != null && furni[i].isMouseOver() ){
                        furni[i] = null;
                        return;
                    }
                }
                break;
            case 'r':
            case 'R':
                for(int i = furni.length-1; i>=0; i--){
                    if( furni[i] != null && furni[i].isMouseOver() ) {
                        furni[i].rotate();
                        return;
                    }
                }
                break;
        }

    }
}


