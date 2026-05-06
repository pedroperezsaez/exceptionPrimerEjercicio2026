package ExempleCOntrolAcces;

public class Main {
        Person p=Person.getInstance();
        Person p2= Person.getInstance();

}


class Vehicle {
    protected void avanca(){

    }
}

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
