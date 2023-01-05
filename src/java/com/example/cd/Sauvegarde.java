package com.example.cd;

import com.example.cd.modele.Listpaquets;
import com.example.cd.modele.PaquetDeCartes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Sauvegarde {

    public Sauvegarde(){
    }

    public static void sauvegarde(PaquetDeCartes paquetDeCartes) throws IOException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        //Path paquetsjson = Path.of("paquets");
        DirectoryChooser dossierchoisi = new DirectoryChooser();
        dossierchoisi.setTitle("Choisi l'emplacement de sauvegarde");
        dossierchoisi.setInitialDirectory(new File(System.getProperty("user.home")));
        /*if (!Files.exists(paquetsjson)){
            Files.createDirectory(paquetsjson);

        }*/
        File dirchoosed = dossierchoisi.showDialog(Main.mainStage);
        String name= dirchoosed.getPath()+"/"+paquetDeCartes.getTitre().replace(' ','_')+".json";
        Path filePath = Paths.get(name);
        if (!Files.exists(filePath)){
            Files.createFile(filePath);
        }
        FileWriter fileWriter = new FileWriter(name);
        fileWriter.write(gson.toJson(paquetDeCartes));
        fileWriter.close();
    }

    public static void sauvegardeTousPaquets(ArrayList<PaquetDeCartes> paquets) throws IOException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String name="src/ressources/donnees.json";
        Path filePath = Paths.get(name);
        if (!Files.exists(filePath)){
            Files.createFile(filePath);
        }
        Listpaquets listPaquets = new Listpaquets(paquets,"paquets");
        FileWriter fileWriter = new FileWriter(name);
        fileWriter.write(gson.toJson(listPaquets));
        fileWriter.close();
    }

    public static PaquetDeCartes chargerPaquets() throws IOException {
        PaquetDeCartes paquetDeCartes = new PaquetDeCartes();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un paquet de cartes");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Json Files", "*.json"));
        /*File dir = new File("paquets");
        if (!Files.exists(dir.toPath())) {
            Files.createDirectory(dir.toPath());
        } */
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(Main.mainStage);
        if (selectedFile != null) {
            String json = Files.readAllLines(selectedFile.toPath()).get(0);
            Gson gson = new Gson();
            PaquetDeCartes paquetDeCartes1 = gson.fromJson(json, PaquetDeCartes.class);
            paquetDeCartes.setTitre(paquetDeCartes1.getTitre());
            paquetDeCartes.setDescription(paquetDeCartes1.getDescription());
            for (int i = 0; i < paquetDeCartes1.getCartes().size(); i++) {
                paquetDeCartes.getCartes().add(paquetDeCartes1.getCartes().get(i));
            }
        }
        return paquetDeCartes;
    }


    public static ArrayList<PaquetDeCartes> chargerTousPaquets(){
        ArrayList<PaquetDeCartes> paquets = new ArrayList<>();
        File file = new File("src/ressources/donnees.json");
        if (file.isFile()) {
            try {
                String json = Files.readAllLines(file.toPath()).get(0);
                Gson gson = new Gson();
                Listpaquets listPaquets = gson.fromJson(json, Listpaquets.class);
                paquets.addAll(listPaquets.paquets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return paquets;
    }

    public static String choisirFichierImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.svg"));
        File selectedFile = fileChooser.showOpenDialog(Main.mainStage);
        if (selectedFile != null) {
            System.out.println("Image selected: " + selectedFile.getName());
            File cheminCreation = new File("src/ressources/images/" + selectedFile.getName());
            String format = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            System.out.println("format: " + format);
            BufferedImage bImage = null;
            try {
                bImage = ImageIO.read(selectedFile);
                ImageIO.write(bImage, format, cheminCreation);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "file:src/ressources/images/" + selectedFile.getName();
        }
        return "";
    }

}
