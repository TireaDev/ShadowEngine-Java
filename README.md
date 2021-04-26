# ShadowEngine - Java version

## How to use
- Download the ShadowEngine.jar file from [releases](https://github.com/TireaDev/ShadowEngine-Java/releases)
- Import it as a jar library to your project
  - *(may vary depending on your IDE/editor/os)*
- Create a main class from the example
  - You will run the application from this class
  - You can create more classes, but only one will extend the *ShadowEngine* class

## Example Class
```java
import com.tireadev.shadowengine.ShadowEngine;

public class Example extends ShadowEngine {

    byte[] image;
    byte[] sound;

    @Override
    public void onAwake() {
    // called at the end of construct()

        // load image.png and sound.wav from disk
        // loadImage(filepath) - load full image file
        // loadImage(filepath, x, y, width, height) - load a subimage with x,y and w,h in pixels
        image = loadImage("Example/res/image.png", 5*16, 0, 16, 16);
        // loadSound(filepath)
        sound = loadSound("Example/res/sound.wav");
    }

    @Override
    public void onStart() {
    // called on start()

        // play a sound on start of the application
        playSound(sound);
    }

    @Override
    public void onUpdate(float deltaTime) {
    // called every frame

        // close application when ESC is pressed
        if (keyReleased(256)) {
            close();
        }


        // clear screen to BLACK
        clear(BLACK);

        // draw image in the center on the screen scaled up by 4
        drawImage((width - getImageWidth(image)  * 4) / 2 + 2, (height - getImageHeight(image) * 4) / 2 + 2, image, 4);

        // draw circle on mouse position
        // filled circle is drawn when left mouse button is held down
        if (mouseDown(0)) {
            fillCircle(getMousePos(), 4, WHITE);
        }
        else {
            drawCircle(getMousePos(), 4, WHITE);
        }

        // play sound on left mouse button click
        if (mousePressed(0)) {
            playSound(sound);
        }
    }

    @Override
    public void onClose() {
    // called on Application Close

    }

    

    public static void main(String[] args) {
        Example demo = new Example();
        // create Application and window with (width, height, title, vsync?, hideCursor?)
        if (demo.construct(512, 480, "demo", true, true)) {
            // start the application loop
            demo.start();
        }
    }
}
```

[Trello](https://trello.com/b/GKKsVSnY)
