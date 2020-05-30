package org.nhc;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.avaje.agentloader.AgentLoader;

/**
 * JavaFX App
 *
 * @author lewis
 */
@Slf4j
public class App extends Application {

    static {
        // Load the agent into the running JVM process
        if (!AgentLoader.loadAgentFromClasspath("ebean-agent", "debug=1")) {
            log.info("ebean-agent not found in classpath - not dynamically loaded");
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Show-Bible");
        ViewTuple<PrimaryView, ShowViewModel> viewTuple = FluentViewLoader.fxmlView(PrimaryView.class).load();

        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}