package com.example.lab01.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.lab01.outils.MySQLiteOpenHelper;

import java.util.Date;

public class AccesLocal {
    private String nomBase = "bdCoach.sqlite";
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;

    public AccesLocal(Context context){
        accesBD = new MySQLiteOpenHelper(context,nomBase,null,versionBase);
    }
    public void ajout(Profil profile) {
        SQLiteDatabase bd = accesBD.getWritableDatabase();
        String req = "INSERT INTO PROFILE (dateMesure, poids, taille, age, sexe) VALUES (?, ?, ?, ?, ?)";

        try (SQLiteStatement stmt = bd.compileStatement(req)) {
            stmt.bindString(1, profile.getDateMesure().toString());
            stmt.bindDouble(2, profile.getPoids());
            stmt.bindDouble(3, profile.getTaille());
            stmt.bindLong(4, profile.getAge());
            stmt.bindString(5, String.valueOf(profile.getSexe()));
            stmt.executeInsert();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bd != null && bd.isOpen()) {
                bd.close();
            }
        }
    }


    public Profil recupDernier(){
        SQLiteDatabase bd = accesBD.getReadableDatabase();
        Profil profile = null;
        String req = "select * from PROFILE;";
        Cursor curseur =  bd.rawQuery(req,null);
        curseur.moveToLast();
        if(!curseur.isAfterLast()){
            Date date = new Date();
            Integer poids = curseur.getInt(1);
            Integer taille = curseur.getInt(2);
            Integer age = curseur.getInt(3);
            Integer sexe = curseur.getInt(4);
            profile = new Profil(date,poids,taille,age,sexe);
        }
        curseur.close();

        return profile;

    }


}
