# ShadowEngine-Java

[Trello](https://trello.com/b/GKKsVSnY)

## How to use
- Download the ShadowEngine.jar file
- Import it as a jar library to your project
  - *(this may vary on your IDE/editor/os)*
- Create a main class from the example
  - You will run the application from this class
  - You can create more classes, but only one will extend the *ShadowEngine* class


## Example Class
```java

import com.tireadev.shadowengine.ShadowEngine;

public class Example extends ShadowEngine {

  // Called on construct()
  @Override
  public void onAwake() {

  }

  // Called on start()
  @Override
  public void onStart() {

  }

  // Called every frame
  @Override
  public void onUpdate() {

  }

  // Called on Application Close
  @Override
  public void onClose() {

  }



  public static void main(String[] args) {
    Example demo = new Example();
    // creates application and window with width, height, title
    if (demo.construct(512, 480, "demo")) {
        // starts the application loop
        demo.start();
    }
  }
}

```
