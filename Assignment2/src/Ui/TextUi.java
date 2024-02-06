package Ui;

import Model.Main;
import Model.Unit;
import com.google.gson.Gson;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TextUi {
    final private TextMenu textMenu;
    private Unit[] units;
    public TextUi() {
        textMenu = new TextMenu();
    }
    public void start() {
        textMenu.displayIntroMessage();
        while(true) {
           textMenu.displayMenu(String.valueOf(Main.class));
           handleMenuOption(textMenu.getValueInRange(1, 8));
        }

    }

    private void handleMenuOption(int selectedValue) {
        selectedValue--;
        MenuOptions mainOption = null;
        for(MenuOptions option: MenuOptions.values()) {
            if(option.ordinal() == selectedValue) {
                mainOption = option;
                break;
            }
        }
        switch(mainOption) {
            case readJson -> {
                readJson();
            }
            case displayUnitInfo -> {
                System.out.println("DISPLAYUNITINFO");
            }
            case createUnit -> {
                System.out.println("CREATEUNIT");
            }
            case testUnit -> {
                System.out.println("TESTUNIT");
            }
            case shipUnit -> {
                System.out.println("SHIPUNIT");
            }
            case printReport -> {
                printReport();
            }
            case reportSortOrder -> {
                System.out.println("SORTORDER");
            }
            case exit -> {
                System.out.println("EXIT");
            }

            case null, default -> {}
        }
    }

    private void printReport() {
        textMenu.getReportOption();
    }

    private void readJson() {
        System.out.println("Enter the path to the input JSON file; blank to cancel.\nWARNING: " +
                "This will replace all current data with the data from the file.");
        String path = textMenu.getFilePath();
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader("data.json");
            units =  gson.fromJson(fileReader, Unit[].class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

