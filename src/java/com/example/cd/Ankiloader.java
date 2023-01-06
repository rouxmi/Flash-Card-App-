package com.example.cd;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.google.gson.Gson;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Ankiloader {
    public static ArrayList<PaquetDeCartes> loadAnki(String filename) throws Exception {
        ArrayList<PaquetDeCartes> paquetDeCartes = new ArrayList<>();
        ZipFile zipFile = new ZipFile(filename);
        File tempDir = new File("temp");
        tempDir.mkdir();
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


        File collectionFile = new File(tempDir, "collection.anki2");


        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + collectionFile.getAbsolutePath());
        Statement statement = connection.createStatement();



        ResultSet deck = statement.executeQuery("SELECT decks FROM col");
        String deckjson = deck.getString(1);
        int Start = deckjson.indexOf("{");
        int End = deckjson.indexOf("}");
        int deuxpoints = deckjson.indexOf(":");
        int nameindex = deckjson.indexOf("name");

        ArrayList<String> deckname = new ArrayList<String>();
        ArrayList<String> deckid = new ArrayList<String>();
        while (Start != -1) {
            String id = deckjson.substring(Start+1, deuxpoints-1).trim();
            String name = deckjson.substring(nameindex + 8, nameindex+9+deckjson.substring(nameindex+9).indexOf('\"') );
            if (name.contains("\"")) {
                name = name.substring(0, name.indexOf("\""));
            }
            deckname.add(name);
            deckid.add(id);
            deckjson = deckjson.substring(End +1);
            Start = deckjson.indexOf("\"");
            End = deckjson.indexOf("}");
            deuxpoints = deckjson.indexOf(":");
            nameindex = deckjson.indexOf("name\":");
        }

        ResultSet resultSet = statement.executeQuery("SELECT flds,did FROM cards c" +
                "  INNER JOIN notes n ON c.nid = n.id");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        ArrayList<String> question = new ArrayList<String>();
        ArrayList<String> reponse = new ArrayList<String>();
        ArrayList<String> decks = new ArrayList<String>();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                if (i == 1) {
                    question.add(columnValue.substring(0, columnValue.indexOf('\u001F')));
                    reponse.add(columnValue.substring(columnValue.indexOf('\u001F')+1));
                }
                if (i == 2) {
                    decks.add(deckname.get(deckid.indexOf(columnValue)));
                }
            }
        }




        resultSet.close();
        statement.close();
        connection.close();
        for (String s : deckname) {
            ArrayList<Carte> cartes = new ArrayList<Carte>();
            for (int j = 0; j < question.size(); j++) {
                if (decks.get(j).equals(s)) {
                    cartes.add(new Carte(question.get(j), reponse.get(j)));
                }
            }
            PaquetDeCartes paquet = new PaquetDeCartes(cartes, s, s);
            if (paquet.getCartes().size() > 0) {
                paquetDeCartes.add(paquet);
            }
        }


        deleteDirectory(tempDir);
        return paquetDeCartes;
    }


    private static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}





