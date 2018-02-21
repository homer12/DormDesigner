import sun.reflect.annotation.ExceptionProxy;

import java.io.*;
import java.util.regex.PatternSyntaxException;

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

    public static void main(String[] args){
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
        }else if( sofaButton.isMouseOver() ){
            for(int i = 0; i< furni.length; i++ ){
                if( furni[i] == null ){
                    Furniture sofa = sofaButton.mouseDown();
                    if( sofa != null )    // If it's not null
                        furni[i] = sofa;
                    break;
                }
            }
            return;
        }else if( saveButton.isMouseOver() ){
            System.out.println("savebutton.");
        }else if( loadButton.isMouseOver() ){

            // clear all the furnitures
            for( Furniture f: furni)
                f = null;

            // specify the project directory and
            // construct the file path string
            String rootProjDir = System.getProperty("user.dir");
            String filePath = rootProjDir + File.separatorChar + "src" +
                    File.separatorChar + "RoomData.ddd";

            // check if file RoomData.ddd exists
            // EXCEPTIONAL CIRCUMSTANCE (2)
            BufferedReader br;
            try{
                br = new BufferedReader(new FileReader(filePath));
            }catch( FileNotFoundException exc ){
                System.out.println("WARNING: Could not load room contents from file RoomData.ddd.");
                return;
            }

            int furniCount = 0;


            while( true ){

                // because readLine() method will throw an Excpetion
                // we have to catch it
                String str;
                try{
                    str = br.readLine();
                }catch( Exception e ){
                    continue;
                }

                if( str == null ) {
                    // it has reached the end of file, so break out
                    break;
                }

                // check if the number of objects is more than 6
                // EXCEPTIONAL CIRCUMSTANCES (5)
                furniCount++;
                if( furniCount > 6 ){
                    System.out.println("WARNING: Unable to load more furniture.");
                    return;
                }

                str = str.trim();
                if( str.equals("") ){
                    // skip the blank
                    furniCount--;
                    continue;
                }

                // check if the string contains ":"
                // EXCEPTIONAL CIRCUMSTANCES (4).1
                String[] strList;
                try{
                    strList = str.split(":", str.length());
                }catch( ArrayIndexOutOfBoundsException e ){
                    System.out.println("WARNING: Found incorrectly formatted line in file: "+str);
                    furniCount--;
                    continue;
                }

                // check if the string contains ","
                // EXCEPTIONAL CIRCUMSTANCES (4).2
                String[] speci;
                try{
                    speci = strList[1].split(",");
                }catch( ArrayIndexOutOfBoundsException e ){
                    System.out.println("WARNING: Found incorrectly formatted line in file: "+str);
                    furniCount--;
                    continue;
                }

                // check if the number of the specification is 3
                // EXCEPTIONAL CIRCUMSTANCES (4).3
                if( speci.length != 3 ){
                    System.out.println("WARNING: Found incorrectly formatted line in file: "+str);
                    furniCount--;
                    continue;
                }

                String furniType = strList[0].trim();
                String imgPath = rootProjDir + File.separatorChar +
                        "src" + File.separatorChar + "images" +
                        File.separatorChar + furniType + ".png";
                // check if furniture type exists
                // EXCEPTIONAL CIRCUMSTANCES (3)
                File furniImgFile = new File(imgPath);
                if( !furniImgFile.exists() ){
                    System.out.println("WARNING: Could not find an image for a furniture object of type: "+furniType);
                    furniCount--;
                    continue;
                }

                float x = Float.parseFloat(speci[0].trim());
                float y = Float.parseFloat(speci[1].trim());
                int rotat = Integer.parseInt(speci[2].trim());

                for(int i = 0; i< furni.length; i++ ){
                    if( furni[i] == null ){
                        Furniture f = saveButton.mouseDown(furniType,x,y,rotat,processing);
                        if( f != null )    // If it's not null
                            furni[i] = f;
                        break;
                    }
                }

            }
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


