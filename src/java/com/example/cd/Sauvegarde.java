package com.example.cd;

import com.example.cd.modele.Listpaquets;
import com.example.cd.modele.PaquetDeCartes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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

    public static void sauvegardeTousPaquets(ArrayList<PaquetDeCartes> paquets) throws Exception {
        sauvegarderMediaZip(paquets);
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
    public static void sauvegarderMediaZip(ArrayList<PaquetDeCartes> paquets) throws Exception{
        File zipFile= new File("src/ressources/medias.zip");
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos= new ZipOutputStream(fos);
        ArrayList<String> fichiersDejaAdd= new ArrayList<>();
        for(int i=0; i<paquets.size();i++){
            for(int j=0; j<paquets.get(i).taillePaquet();j++){
                if(!paquets.get(i).getCarte(j).getImageQuestion().equals("")){
                    File imageActuelle = new File("src/ressources/medias/"+paquets.get(i).getCarte(j).getImageQuestion());
                    if(!fichiersDejaAdd.contains(paquets.get(i).getCarte(j).getImageQuestion())){
                        fichiersDejaAdd.add(paquets.get(i).getCarte(j).getImageQuestion());
                        ZipEntry zipCarte = new ZipEntry(imageActuelle.getName());
                        zos.putNextEntry(zipCarte);
                        byte[] buffer= new byte[1024];
                        //int len;
                        while ((imageActuelle.canRead())){
                            zos.write(buffer);
                        }
                        zos.closeEntry();
                    }

                } else if (!paquets.get(i).getCarte(j).getAudioQuestion().equals("")) {
                    File audioActuel = new File("src/ressources/medias/"+paquets.get(i).getCarte(j).getAudioQuestion());
                    if(!fichiersDejaAdd.contains(paquets.get(i).getCarte(j).getAudioQuestion())){
                        fichiersDejaAdd.add(paquets.get(i).getCarte(j).getAudioQuestion());
                        ZipEntry zipCarte = new ZipEntry(audioActuel.getName());
                        zos.putNextEntry(zipCarte);
                        byte[] buffer= new byte[1024];
                        //int len;
                        while ((audioActuel.canRead())){
                            zos.write(buffer);
                        }
                        zos.closeEntry();
                    }

                }
            }
        }
        zos.close();
    }
    public static void deZipMediaZip() throws Exception{
        ZipFile zipFile = new ZipFile("src/ressources/medias.zip");
        File tempDir = new File("src/ressources/medias/");
        if(!Files.exists(tempDir.toPath())) {
            tempDir.mkdirs();
        }
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File entryDestination = new File(tempDir, entry.getName());
            if (entry.isDirectory()) {
                entryDestination.mkdirs();
            } else {
                entryDestination.getParentFile().mkdirs();
                InputStream in = zipFile.getInputStream(entry);
                FileOutputStream out = new FileOutputStream(entryDestination);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
            }
        }
        zipFile.close();
    }

    public static ArrayList<PaquetDeCartes> chargerPaquets() throws Exception {
        ArrayList<PaquetDeCartes> paquetDeCartes = new ArrayList<PaquetDeCartes>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un paquet de cartes");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Anki Collections Files ou Json Files", "*.json","*.apkg"));
        /*File dir = new File("paquets");
        if (!Files.exists(dir.toPath())) {
            Files.createDirectory(dir.toPath());
        } */
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(Main.mainStage);
        if (selectedFile != null) {
            if (selectedFile.getName().endsWith(".json")) {
                String json = Files.readAllLines(selectedFile.toPath()).get(0);
                Gson gson = new Gson();
                PaquetDeCartes paquet = new PaquetDeCartes();
                PaquetDeCartes paquetDeCartes1 = gson.fromJson(json, PaquetDeCartes.class);
                paquet.setTitre(paquetDeCartes1.getTitre());
                paquet.setDescription(paquetDeCartes1.getDescription());
                for (int i = 0; i < paquetDeCartes1.getCartes().size(); i++) {
                    paquet.getCartes().add(paquetDeCartes1.getCartes().get(i));
                }
                paquetDeCartes.add(paquet);
            } else if (selectedFile.getName().endsWith(".apkg")) {
                paquetDeCartes = Ankiloader.loadAnki(selectedFile.getAbsolutePath());
            }

        }
        return paquetDeCartes;
    }


    public static ArrayList<PaquetDeCartes> chargerTousPaquets() throws Exception {
        if(Files.exists(Path.of("src/ressources/medias.zip"))){
            deZipMediaZip();
        }
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

    public static String choisirFichierImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(Main.mainStage);
        if (selectedFile != null) {
            if(!Files.exists(Path.of("src/ressources/medias/"))){
                Files.createDirectory(Path.of("src/ressources/medias/"));
            }
            File cheminCreation = new File("src/ressources/medias/" + selectedFile.getName());
            String format = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            BufferedImage bImage = null;
            try {
                bImage = ImageIO.read(selectedFile);
                ImageIO.write(bImage, format, cheminCreation);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "file:src/ressources/medias/" + selectedFile.getName();
        }
        return "";
    }

    public static String choisirFichierAudio() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier *.wav");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav"));
        File selectedFile = fileChooser.showOpenDialog(Main.mainStage);

        if (selectedFile != null) {
            File cheminCreation = new File("src/ressources/medias/" + selectedFile.getName());
            if(!Files.exists(Path.of("src/ressources/medias/"))){
                Files.createDirectory(Path.of("src/ressources/medias/"));
            }
            InputStream input = new FileInputStream(selectedFile.getPath());
            OutputStream output = new FileOutputStream(cheminCreation);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            input.close();
            output.close();
            return "/medias/"+ selectedFile.getName();
        }
        return "";
    }

}
