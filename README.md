# ShadowEngine-Java

[Trello](https://trello.com/b/GKKsVSnY)

## How to use
- Download the ShadowEngine.jar file from [releases](https://github.com/TireaDev/ShadowEngine-Java/releases)
- Import it as a jar library to your project
  - *(this may vary on your IDE/editor/os)*
- Create a main class from the example
  - You will run the application from this class
  - You can create more classes, but only one will extend the *ShadowEngine* class


## Example Class
```java

import com.tireadev.shadowengine.ShadowEngine;

public class Example extends ShadowEngine {

  @Override
  public void onAwake() {
  // Called on construct()

  }

  @Override
  public void onStart() {
  // Called on start()

  }

  @Override
  public void onUpdate(float deltaTime) {
  // Called every frame

    // close application when ESC is pressed
    if (keyReleased(256)) close();

    // clear screen to BLACK
    clear(BLACK);

    // draw circle on mouse position
    // filled circle is drawn when right mouse button is held down
    if (mouseDown(0))
      fillCircle(getMousePos(), 4, WHITE);
    else
      drawCircle(getMousePos(), 4, WHITE);
  }

  @Override
  public void onClose() {
  // Called on Application Close

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
