package org.nhc;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.avaje.agentloader.AgentLoader;

import java.io.IOException;

/**
 * JavaFX App
 */
@Slf4j
public class App extends Application {

    private static Scene scene;

    static {
        // Load the agent into the running JVM process
        if (!AgentLoader.loadAgentFromClasspath("ebean-agent", "debug=1")) {
            log.info("ebean-agent not found in classpath - not dynamically loaded");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Show-Bible");
        ViewTuple<PrimaryView, ShowViewModel> viewTuple = FluentViewLoader.fxmlView(PrimaryView.class).load();

        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();


//        scene = new Scene(loadFXML("primary"));
//        stage.setScene(scene);
//        stage.show();
    }

//    static void setRoot(String fxml) throws IOException {
//        scene.setRoot(loadFXML(fxml));
//    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}