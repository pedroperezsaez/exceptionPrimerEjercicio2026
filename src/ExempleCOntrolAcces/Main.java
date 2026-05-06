package ExempleCOntrolAcces;

public class Main {
        Person p=Person.getInstance();
        Person p2= Person.getInstance();

}


class Vehicle {
    protected void avanca(){

    }
}
//sensse modificador: accesible nomes dins el mateix paquet.
//public: accesible per tothom, dins i fora del paquet.
//protected: accesible dins el mateix paquet, pero tambie per classes que herten tambe foradel pqeut
// private: accessible nomes dins la mateixa clase. prohibeix acces desde matiex paquet fora del paquet i tambe
//les classes que hereten dins como fora del paquet
class Car extends  Vehicle{
    @Override
    protected void avanca(){

    }
}

//patron de disseny SINGLETON
class  Person{
    private static Person instance;
    private Person(){
    }

    static Person getInstance(){
        if (instance== null){
            instance=new Person();
        }
        return instance;
    }


}
