package com.example.mini_projet;

import java.util.List;

public class Model {
    private String email;
    private String sommeDepenses;
    private String sommeRessources;
    private List <String> listDepenses;
    private  List<Integer> listDepensesValues;
    private List <String> listRessources;
    private List<Integer> listRessourcesValues;

    public Model(String email, String sommeDepenses, String sommeRessources, List<String> listDepenses, List<Integer> listDepensesValues, List<String> listRessources, List<Integer> listRessourcesValues) {
        this.email = email;
        this.sommeDepenses = sommeDepenses;
        this.sommeRessources = sommeRessources;
        this.listDepenses = listDepenses;
        this.listDepensesValues = listDepensesValues;
        this.listRessources = listRessources;
        this.listRessourcesValues = listRessourcesValues;
    }
    public Model(){
    }

    public String getEmail() {
        return email;
    }

    public String getSommeDepenses() {
        return sommeDepenses;
    }

    public String getSommeRessources() {
        return sommeRessources;
    }


    public List<String> getListDepenses() {
        return listDepenses;
    }


    public List<Integer> getListDepensesValues() {
        return listDepensesValues;
    }

    public List<String> getListRessources() {
        return listRessources;
    }


    public List<Integer> getListRessourcesValues() {
        return listRessourcesValues;
    }

}
