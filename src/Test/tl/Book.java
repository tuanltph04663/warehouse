/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.tl;

/**
 *
 * @author Admin
 */
public class Book {
    private int id;
    private String name;
    private int keptnumber;

    public Book(int id, String name,int keptnumber) {
        this.id = id;
        this.name = name;
        this.keptnumber = keptnumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKeptnumber() {
        return keptnumber;
    }

    public void setKeptnumber(int keptnumber) {
        this.keptnumber = keptnumber;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", name=" + name + ", keptnumber=" + keptnumber + '}';
    }


    
    
}
