import java.util.Arrays;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class TableViewCustom extends Application {

  private final TableView<TestClass> table = new TableView<>();
  private final ObservableList<TestClass> data = FXCollections.observableArrayList(
      new TestClass("String", 69, 42.0, true,"S"),
      new TestClass("Jack Haley", 21, 3.3, false,"J"),
      new TestClass("Harry Truman", 55, 12.33, true,"H"));
  final HBox hb = new HBox();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Scene scene = new Scene(new Group());
    primaryStage.setTitle("Table View Custom");
    primaryStage.setWidth(500);
    primaryStage.setHeight(600);

    final Label label = new Label("Test Table");
    label.setFont(new Font("Helvetica", 20));

    table.setEditable(true);


    TableColumn<TestClass, String> testStringCol = new TableColumn<>("Test String");
    testStringCol.setMinWidth(100);
    testStringCol.setCellValueFactory(new PropertyValueFactory<>("testString"));
    testStringCol.setCellFactory(TextFieldTableCell.forTableColumn());
    testStringCol.setOnEditCommit(
        (CellEditEvent<TestClass, String> t) -> {
          ((TestClass) t.getTableView().getItems().get(t.getTablePosition().getRow()))
              .setTestString(t.getNewValue());
        });

    TableColumn<TestClass, Integer> testIntCol = new TableColumn<>("Test Int");
    testIntCol.setMinWidth(100);
    testIntCol.setCellValueFactory(new PropertyValueFactory<>("testInt"));
    testIntCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    testIntCol.setOnEditCommit(
        (CellEditEvent<TestClass, Integer> t) -> {
          ((TestClass) t.getTableView().getItems().get(t.getTablePosition().getRow()))
              .setTestInt(t.getNewValue());
        });

    TableColumn<TestClass, Double> testDoubleCol = new TableColumn<>("Test Double");
    testDoubleCol.setMinWidth(100);
    testDoubleCol.setCellValueFactory(new PropertyValueFactory<>("testDouble"));
    testDoubleCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    testDoubleCol.setOnEditCommit(
        (CellEditEvent<TestClass, Double> t) -> {
          ((TestClass) t.getTableView().getItems().get(t.getTablePosition().getRow()))
              .setTestDouble(t.getNewValue());
        });

    TableColumn<TestClass, Boolean> testBooleanCol = new TableColumn<>("Test Boolean");
    testBooleanCol.setMinWidth(100);
    testBooleanCol.setCellValueFactory(new PropertyValueFactory<>("testBoolean"));
    testBooleanCol.setCellFactory(CheckBoxTableCell.forTableColumn(testBooleanCol));
    testBooleanCol.setOnEditCommit(
        (CellEditEvent<TestClass, Boolean> t) -> {
          ((TestClass) t.getTableView().getItems().get(t.getTablePosition().getRow()))
              .setTestBoolean(t.getNewValue());
        });

    TableColumn<TestClass, String> testCharacterCol= new TableColumn<>("Test character");//added by doneddy
    testCharacterCol.setMinWidth(100);
    testCharacterCol.setCellValueFactory(new PropertyValueFactory<>("testCharacter"));
    testCharacterCol.setCellFactory(TextFieldTableCell.forTableColumn());
    testCharacterCol.setOnEditCommit(
        (CellEditEvent<TestClass, String> t) -> {
          ((TestClass) t.getTableView().getItems().get(t.getTablePosition().getRow()))
              .setTestCharacter(t.getNewValue());
        });

        for (TestClass testClass : data) {//added by doneddy
          String change = testClass.getTestString();
          if (change.length() > 0) {
            testClass.setTestCharacter(change.substring(0, 1));
            testClass.setTestString(change.substring(1));
          }
        }

    table.setItems(data);
    table
        .getColumns()
        .addAll(Arrays.asList(testStringCol, testIntCol, testDoubleCol, testBooleanCol,testCharacterCol/*added by doneddy*/));

    final TextField addTestString = new TextField();
    addTestString.setPromptText("Test String");
    addTestString.setMaxWidth(testStringCol.getPrefWidth());

    final TextField addTestInt = new TextField();
    addTestInt.setMaxWidth(testIntCol.getPrefWidth());
    addTestInt.setPromptText("Test Int");

    final TextField addTestDouble = new TextField();
    addTestDouble.setMaxWidth(testDoubleCol.getPrefWidth());
    addTestDouble.setPromptText("Test Double");

    final CheckBox addTestBoolean = new CheckBox("Test Boolean");
    addTestBoolean.setMaxWidth(testBooleanCol.getPrefWidth());

    final TextField addTestCharacter = new TextField();//added by doneddy
    addTestCharacter.setMaxWidth(testCharacterCol.getPrefWidth());

    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(label, table, hb);

    final Button addButton = new Button("Add");
    addButton.setOnAction(
        (ActionEvent e) -> {
          data.add(
              new TestClass(
                  addTestString.getText(),
                  Integer.parseInt(addTestInt.getText()),
                  Double.parseDouble(addTestDouble.getText()),
                  addTestBoolean.isSelected(),addTestCharacter.getText()/*added by doneddy*/));

          addTestString.clear();
          addTestInt.clear();
          addTestDouble.clear();
          addTestBoolean.setSelected(false);
        });

    hb.getChildren().addAll(addTestString, addTestInt, addTestDouble, addTestBoolean, addButton,addTestCharacter/*added by doneddy*/);
    hb.setSpacing(3);

    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static class TestClass {

    private final StringProperty testString;
    private final IntegerProperty testInt;
    private final DoubleProperty testDouble;
    private final BooleanProperty testBoolean;
    private final StringProperty testCharacter;//added by doneddy

    public TestClass(String testString, int testInt, double testDouble, boolean testBoolean, String testCharacter/*added by doneddy*/) {
      this.testString = new SimpleStringProperty(testString);
      this.testInt = new SimpleIntegerProperty(testInt);
      this.testDouble = new SimpleDoubleProperty(testDouble);
      this.testBoolean = new SimpleBooleanProperty(testBoolean);
      this.testCharacter = new SimpleStringProperty(testCharacter);//added by doneddy
    }

    public String getTestString() {
      return testString.get();
    }

    public void setTestString(String testString) {
      this.testString.set(testString);
    }

    public StringProperty testStringProperty() {
      return testString;
    }

    public int getTestInt() {
      return testInt.get();
    }

    public void setTestInt(int testInt) {
      this.testInt.set(testInt);
    }

    public IntegerProperty testIntProperty() {
      return testInt;
    }

    public double getTestDouble() {
      return testDouble.get();
    }

    public void setTestDouble(double testDouble) {
      this.testDouble.set(testDouble);
    }

    public DoubleProperty testDoubleProperty() {
      return testDouble;
    }

    public boolean getTestBoolean() {
      return testBoolean.get();
    }

    public void setTestBoolean(boolean testBoolean) {
      this.testBoolean.set(testBoolean);
    }

    public BooleanProperty testBooleanProperty() {
      return testBoolean;
    }
    public String getTestCharacter() {//added by doneddy
      return testCharacter.get();
    }
    public void setTestCharacter(String testCharacter) {//added by doneddy
      this.testCharacter.set(testCharacter);
    }
  }
}
